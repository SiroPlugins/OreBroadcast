package be.bendem.bukkit.orebroadcast;

import be.bendem.bukkit.orebroadcast.handlers.BlockBreakListener;
import be.bendem.bukkit.orebroadcast.handlers.BlockPlaceListener;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OreBroadcast extends JavaPlugin {
    private static OreBroadcast instance;
    private final Set<Block> broadcastBlacklist = new HashSet<>();
    private final List<Material> oreList =
            List.of(Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE, Material.REDSTONE_ORE,
                    Material.LAPIS_ORE, Material.EMERALD_ORE, Material.DIAMOND_ORE, Material.NETHER_QUARTZ_ORE);

    public OreBroadcast() {
        instance = this;
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();
        getServer().getPluginManager().registerEvents(BlockBreakListener.get(), this);
        getServer().getPluginManager().registerEvents(BlockPlaceListener.get(), this);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
        broadcastBlacklist.clear();
    }

    public String getMessage() {
        return getConfig().getString("message", "&7* &l{player_name}&r &7が &b{count}個&r &7の &b{ore}&7 を発見した!");
    }

    public String getOreName(@NotNull Material material) {
        return getConfig().getString("Ores." + material.toString(), material.toString());
    }

    public int getMaxVein() {
        return getConfig().getInt("max-vein-size", 100);
    }

    public boolean isOre(Material material) {
        return oreList.contains(material);
    }

    public boolean isDisabledOre(@NotNull Material material) {
        return getConfig().getStringList("disableOres").contains(material.toString());
    }

    public boolean isIgnoreCreative() {
        return getConfig().getBoolean("broadcast-creative-placed-blocks", true);
    }

    public boolean isWorldDisabled(@NotNull World world) {
        return getConfig().getStringList("disableWorlds").contains(world.getName());
    }

    public void blackList(Block block) {
        broadcastBlacklist.add(block);
    }

    public void blackList(Collection<Block> blocks) {
        broadcastBlacklist.addAll(blocks);
    }

    public void unBlackList(Block block) {
        broadcastBlacklist.remove(block);
    }

    public boolean isBlackListed(Block block) {
        return broadcastBlacklist.contains(block);
    }

    public static OreBroadcast get() {
        return instance;
    }
}
