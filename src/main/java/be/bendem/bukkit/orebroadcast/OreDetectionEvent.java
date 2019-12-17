package be.bendem.bukkit.orebroadcast;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class OreDetectionEvent extends Event {
    private static final HandlerList handlers = new HandlerList();

    private final Block blockMined;
    private final Player player;
    private final Set<Block> vein;

    public OreDetectionEvent(@NotNull Player player, @NotNull Block blockMined, @NotNull Set<Block> vein) {
        this.player = player;
        this.blockMined = blockMined;
        this.vein = vein;
    }

    @NotNull
    public Player getPlayer() {
        return player;
    }

    @NotNull
    public Set<Block> getVein() {
        return vein;
    }

    @NotNull
    public Block getBlockMined() {
        return blockMined;
    }

    @NotNull
    public static HandlerList getHandlerList() {
        return handlers;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
