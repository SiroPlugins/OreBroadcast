package be.bendem.bukkit.orebroadcast;

import be.bendem.bukkit.orebroadcast.handlers.BlockBreakListener;
import be.bendem.bukkit.orebroadcast.handlers.BlockPlaceListener;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OreBroadcast extends JavaPlugin {

    private final Set<SafeBlock> broadcastBlacklist = new HashSet<>();
    private final Set<String> disableWorlds = new HashSet<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadConfig();

        disableWorlds.addAll(getConfig().getStringList("disableWorlds"));

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }

    public void blackList(Block block) {
        broadcastBlacklist.add(new SafeBlock(block));
    }

    public void blackList(Collection<Block> blocks) {
        for (Block block : blocks) blackList(block);
    }

    public void unBlackList(Block block) {
        broadcastBlacklist.remove(new SafeBlock(block));
    }

    public boolean isBlackListed(Block block) {
        return broadcastBlacklist.contains(new SafeBlock(block));
    }

    public boolean isWorldDisabled(String world) {
        return disableWorlds.contains(world);
    }
}
