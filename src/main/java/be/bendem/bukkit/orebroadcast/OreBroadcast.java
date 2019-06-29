package be.bendem.bukkit.orebroadcast;

import be.bendem.bukkit.orebroadcast.handlers.BlockBreakListener;
import be.bendem.bukkit.orebroadcast.handlers.BlockPlaceListener;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

public class OreBroadcast extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        config = new Config(this);
        config.loadConfig();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }

    public void blackList(Block block) {
        config.getBroadcastBlacklist().add(new SafeBlock(block));
    }

    public void blackList(Collection<Block> blocks) {
        for(Block block : blocks) {
            blackList(block);
        }
    }

    public void unBlackList(Block block) {
        config.getBroadcastBlacklist().remove(new SafeBlock(block));
    }

    public boolean isBlackListed(Block block) {
        return config.getBroadcastBlacklist().contains(new SafeBlock(block));
    }

    public boolean isWorldDisabled(String world) {
        return config.getDisableWorlds().contains(world);
    }

}
