package be.bendem.bukkit.orebroadcast;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

/**
 * Broadcast event for OreBroadcast messages.
 *
 * @author cnaude
 */
public class OreBroadcastEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final String format;
    private final Block blockMined;
    private final Player source;
    private final Set<Block> vein;
    private boolean cancelled = false;

    public OreBroadcastEvent(String format, Player source, Block blockMined, Set<Block> vein) {
        this.format = format;
        this.source = source;
        this.blockMined = blockMined;
        this.vein = vein;
    }

    public String getFormat() {
        return format;
    }

    public Player getSource() {
        return this.source;
    }

    public Set<Block> getVein() {
        return vein;
    }

    public Block getBlockMined() {
        return blockMined;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
