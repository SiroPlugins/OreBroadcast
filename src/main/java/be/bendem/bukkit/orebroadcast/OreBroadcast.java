package be.bendem.bukkit.orebroadcast;

import be.bendem.bukkit.orebroadcast.handlers.BlockBreakListener;
import be.bendem.bukkit.orebroadcast.handlers.BlockPlaceListener;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collection;

/**
 * OreBroadcast for Bukkit
 *
 * @author bendem
 */
public class OreBroadcast extends JavaPlugin {

    private Config config;

    @Override
    public void onEnable() {
        config = new Config(this);
        config.loadConfig();

        getServer().getPluginManager().registerEvents(new BlockBreakListener(this), this);
        getServer().getPluginManager().registerEvents(new BlockPlaceListener(this), this);
    }


    /**
     * Blacklists a block. Blocks blacklisted won't get broadcasted when
     * broken.
     *
     * @param block the block to blacklist
     */
    public void blackList(Block block) {
        config.getBroadcastBlacklist().add(new SafeBlock(block));
    }

    /**
     * Blacklists multiple blocks. Blocks blacklisted won't get broadcasted
     * when broken.
     *
     * @param blocks the blocks to blacklist
     */
    public void blackList(Collection<Block> blocks) {
        for(Block block : blocks) {
            blackList(block);
        }
    }

    /**
     * Unblacklist a block.
     *
     * @param block the block to unblacklist
     */
    public void unBlackList(Block block) {
        config.getBroadcastBlacklist().remove(new SafeBlock(block));
    }

    /**
     * Checks wether a block is blacklisted or not.
     *
     * @param block the block to check
     * @return true if the block is blacklisted
     */
    public boolean isBlackListed(Block block) {
        return config.getBroadcastBlacklist().contains(new SafeBlock(block));
    }

    /**
     * Check if OreBroadcast is active in a world
     *
     * @param world the name of the world
     * @return true if OreBroadcast is active in the world
     */
    public boolean isWorldWhitelisted(String world) {
        return !config.isWorldWhitelistActive() || config.getWorldWhitelist().contains(world);
    }

}
