package be.bendem.bukkit.orebroadcast.handlers;

import be.bendem.bukkit.orebroadcast.OreBroadcast;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.jetbrains.annotations.NotNull;

public class BlockPlaceListener implements Listener {
    private static BlockPlaceListener instance;

    private BlockPlaceListener() {
        instance = this;
    }

    public static BlockPlaceListener get() {
        if (instance == null) {
            new BlockPlaceListener();
        }
        return instance;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBlockPlace(@NotNull BlockPlaceEvent e) {
        if (e.isCancelled()) {
            return;
        }

        if ((!e.getPlayer().getGameMode().equals(GameMode.CREATIVE) || !OreBroadcast.get().isIgnoreCreative())
                && OreBroadcast.get().isOre(e.getBlockPlaced().getType())
                && !OreBroadcast.get().isBlackListed(e.getBlock())
                && !OreBroadcast.get().isWorldDisabled(e.getBlock().getWorld())) {
            OreBroadcast.get().addBlackList(e.getBlock());
        }
    }
}
