package be.bendem.bukkit.orebroadcast;

import org.bukkit.block.Block;

import java.util.UUID;

/**
 * SafeBlock contains information about a block but doesn't prevents world
 * unloading (it doesn't contain information about the server's worlds)
 *
 * @author bendem
 */
class SafeBlock {

    private final int x;
    private final int y;
    private final int z;
    private final UUID world;

    SafeBlock(Block block) {
        this(block.getX(), block.getY(), block.getZ(), block.getWorld().getUID());
    }

    private SafeBlock(int x, int y, int z, UUID world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SafeBlock)) {
            return false;
        }
        SafeBlock safeBlock = (SafeBlock) o;
        return x == safeBlock.x
                && y == safeBlock.y
                && z == safeBlock.z
                && world.equals(safeBlock.world);
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        result = 31 * result + world.hashCode();
        return result;
    }

}
