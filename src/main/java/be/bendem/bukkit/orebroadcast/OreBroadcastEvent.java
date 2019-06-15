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

    private       String format;
    private final Block blockMined;
    private final Player source;
    private final Set<Block> vein;
    private boolean cancelled = false;

    /**
     * Broadcast event for OreBroadcast messages.
     *
     * @param format the format of the message
     * @param source the player that mined the block
     * @param blockMined the block which was mined by the player
     * @param vein the complete vein containing the block mined
     */
    public OreBroadcastEvent(String format, Player source, Block blockMined, Set<Block> vein) {
        this.format = format;
        this.source = source;
        this.blockMined = blockMined;
        this.vein = vein;
    }

    /**
     * Gets the format of the message.
     * This format can contain:
     * <ul>
     * <li>{player_name}</li>
     * <li>{real_player_name}</li>
     * <li>{world}</li>
     * <li>{count}</li>
     * <li>{ore}</li>
     * <li>{ore_color}</li>
     * <li>{plural}</li>
     * </ul>
     *
     * @return the format of the message
     */
    public String getFormat() {
        return format;
    }

    /**
     * Gets the player who mined the block.
     *
     * @return the player who mined the block
     */
    public Player getSource() {
        return this.source;
    }

    /**
     * Gets the vein of blocks corresponding to that message (you can
     * modify that Set to reduce the size. Note that removed block
     * won't get blacklisted and will then be broadcasted when broken).
     *
     * @return the vein
     */
    public Set<Block> getVein() {
        return vein;
    }

    /**
     * Gets the block which was mined by the player, triggering the event
     *
     * @return the block mined
     */
    public Block getBlockMined() {
        return blockMined;
    }

    /**
     * Gets the cancellation state of the event.
     *
     * @return true if the event is cancelled
     */

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the cancellation state of the event
     * (If you cancel the event, the blocks of the vein will be broadcasted
     * next time a block of the vein is broken)
     * See {@link OreBroadcast#blackList(Block)} to blacklist blocks
     * independently.
     *
     * @param cancel the new cancellation state
     */
    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
