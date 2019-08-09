package be.bendem.bukkit.orebroadcast.handlers;

import be.bendem.bukkit.orebroadcast.OreBroadcast;
import be.bendem.bukkit.orebroadcast.OreBroadcastEvent;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.*;

import static org.bukkit.Material.*;

public class BlockBreakListener implements Listener {

    private final OreBroadcast plugin;
    private final List<Material> oreList = new ArrayList<>();
    private final List<String> disableOres;
    private final Map<Material, String> oreName = new HashMap<>();
    private final int maxVein;
    private final String msg;

    public BlockBreakListener(OreBroadcast plugin) {
        this.plugin = plugin;
        maxVein = plugin.getConfig().getInt("max-vein-size", 100);
        msg = plugin.getConfig().getString("message", "{player} just found {count} block{plural} of {ore}");
        disableOres = plugin.getConfig().getStringList("disableOres");

        Collections.addAll(oreList, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, LAPIS_ORE, EMERALD_ORE, DIAMOND_ORE);

        oreList.forEach(m -> oreName.put(m, plugin.getConfig().getString("Ores." + m.toString(), m.toString())));

    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (oreList.contains(block.getType()) && !disableOres.contains(block.getType().toString())) {

            Player player = event.getPlayer();

            if (player.getGameMode() != GameMode.SURVIVAL
                    || plugin.isWorldDisabled(player.getWorld().getName())) {
                return;
            }

            if (plugin.isBlackListed(block)) {
                plugin.unBlackList(block);
                return;
            }

            Set<Block> vein = getVein(block);

            if (vein.size() < 1) {
                plugin.getLogger().fine("Vein ignored");
                return;
            }

            OreBroadcastEvent e = new OreBroadcastEvent(
                    msg,
                    player,
                    block,
                    vein
            );

            plugin.getServer().getPluginManager().callEvent(e);

            if (e.isCancelled() || e.getVein().isEmpty()) {
                return;
            }

            String blockName = oreName.get(block.getType());
            plugin.blackList(e.getVein());
            plugin.unBlackList(e.getBlockMined());

            String formattedMessage = format(
                    e.getFormat(),
                    e.getSource(),
                    e.getVein().size(),
                    blockName
            );

            Bukkit.broadcastMessage(formattedMessage);
        }
    }

    private Set<Block> getVein(Block block) {
        Set<Block> vein = new HashSet<>();
        vein.add(block);
        getVein(block, vein);
        return vein;
    }

    private void getVein(Block block, Set<Block> vein) {
        if (vein.size() > maxVein) {
            return;
        }

        int i, j, k;
        for (i = -1; i < 2; ++i) {
            for (j = -1; j < 2; ++j) {
                for (k = -1; k < 2; ++k) {
                    Block relative = block.getRelative(i, j, k);
                    if (!vein.contains(relative)                  // block already found
                            && block.getType().equals(relative.getType())           // block has not the same type
                            && ((i != 0 || j != 0 || k != 0))) {   // comparing block to itself)
                        vein.add(relative);
                        getVein(relative, vein);
                    }
                }
            }
        }
    }

    private String format(String msg, Player player, int count, String ore) {
        return msg
                .replace("&", "§")
                .replace("{player_name}", player.getDisplayName())
                .replace("{real_player_name}", player.getName())
                .replace("{world}", player.getWorld().getName())
                .replace("{count}", String.valueOf(count))
                .replace("{ore}", ore);
    }

}
