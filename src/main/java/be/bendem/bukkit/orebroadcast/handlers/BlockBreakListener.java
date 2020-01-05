package be.bendem.bukkit.orebroadcast.handlers;

import be.bendem.bukkit.orebroadcast.OreBroadcast;
import be.bendem.bukkit.orebroadcast.OreDetectionEvent;
import com.github.siroshun09.sirolibrary.bukkitutils.BukkitUtil;
import com.github.siroshun09.sirolibrary.message.BukkitMessage;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class BlockBreakListener implements Listener {
    private static BlockBreakListener instance;

    private BlockBreakListener() {
        instance = this;
    }

    public static BlockBreakListener get() {
        if (instance == null) {
            new BlockBreakListener();
        }
        return instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockBreak(@NotNull BlockBreakEvent event) {
        Block block = event.getBlock();
        if (!OreBroadcast.get().isOre(block.getType()) || OreBroadcast.get().isWorldDisabled(block.getWorld())) {
            return;
        }

        if (OreBroadcast.get().isBlackListed(block)) {
            OreBroadcast.get().unBlackList(block);
            return;
        }

        if (!event.getPlayer().getGameMode().equals(GameMode.SURVIVAL)) {
            return;
        }

        Set<Block> vein = getVein(block);
        if (vein.isEmpty()) {
            return;
        }

        OreDetectionEvent e = new OreDetectionEvent(event.getPlayer(), block, vein);
        BukkitUtil.callEvent(e);

        OreBroadcast.get().blackList(e.getVein());
        OreBroadcast.get().unBlackList(e.getBlockMined());

        if (e.getVein().isEmpty() || OreBroadcast.get().isDisabledOre(block.getType())) {
            return;
        }

        broadcast(OreBroadcast.get().getMessage(), e.getPlayer(),
                OreBroadcast.get().getOreName(e.getBlockMined().getType()), e.getVein().size());
    }

    @NotNull
    private Set<Block> getVein(Block block) {
        Set<Block> vein = new HashSet<>();
        vein.add(block);
        getVein(block, vein);
        return vein;
    }

    private void getVein(Block block, Set<Block> vein) {
        int x, y, z;
        for (x = -1; x < 2; x++) {
            for (y = -1; y < 2; y++) {
                for (z = -1; z < 2; z++) {
                    if (OreBroadcast.get().getMaxVein() <= vein.size()) {
                        break;
                    }
                    Block relative = block.getRelative(x, y, z);
                    if (!vein.contains(relative) && block.getType().equals(relative.getType())
                            && !OreBroadcast.get().isBlackListed(relative) && (x != 0 || y != 0 || z != 0)) {
                        vein.add(relative);
                        getVein(relative, vein);
                    }
                }
            }
        }
    }

    @NotNull
    private String format(@NotNull String msg, @NotNull Player player, @NotNull String ore, int count) {
        return msg
                .replace("{player_name}", player.getDisplayName())
                .replace("{real_player_name}", player.getName())
                .replace("{world}", player.getWorld().getName())
                .replace("{ore}", ore)
                .replace("{count}", String.valueOf(count));
    }

    private void broadcast(@NotNull String msg, @NotNull Player player, @NotNull String ore, int count) {
        BukkitMessage.broadcastWithColor(format(msg, player, ore, count));
    }
}
