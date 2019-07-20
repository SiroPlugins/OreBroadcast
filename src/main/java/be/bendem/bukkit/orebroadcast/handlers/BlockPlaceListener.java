package be.bendem.bukkit.orebroadcast.handlers;

import be.bendem.bukkit.orebroadcast.OreBroadcast;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.bukkit.Material.*;

public class BlockPlaceListener implements Listener {

    private final OreBroadcast plugin;
    private final FileConfiguration config;
    private final List<Material> oreList = new ArrayList<>();

    public BlockPlaceListener(OreBroadcast plugin) {
        this.plugin = plugin;
        config = plugin.getConfig();
        Collections.addAll(oreList, COAL_ORE, IRON_ORE, GOLD_ORE, REDSTONE_ORE, LAPIS_ORE, EMERALD_ORE, DIAMOND_ORE);
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Block block = e.getBlock();
        if (oreList.contains(e.getBlockPlaced().getType())
                && !plugin.isWorldDisabled(block.getWorld().getName())
                && !config.getStringList("disableOres").contains(block.getType().toString())
                && !plugin.isBlackListed(block)
                && (e.getPlayer().getGameMode() != GameMode.CREATIVE
                || !plugin.getConfig().getBoolean("broadcast-creative-placed-blocks", true))) {

            plugin.blackList(block);
        }
    }
}
