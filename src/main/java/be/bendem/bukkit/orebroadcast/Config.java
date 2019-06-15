package be.bendem.bukkit.orebroadcast;

import java.util.HashSet;
import java.util.Set;

/* package */ class Config {

    private final OreBroadcast plugin;
    private final Set<SafeBlock> broadcastBlacklist   = new HashSet<>();
    private final Set<String>    worldWhitelist       = new HashSet<>();
    private       boolean        worldWhitelistActive = false;

    /* package */ Config(OreBroadcast plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    /* package */ void loadConfig() {
        plugin.reloadConfig();

        // Load world whitelist
        worldWhitelist.clear();
        worldWhitelistActive = plugin.getConfig().getBoolean("active-per-worlds", true);
        if(worldWhitelistActive) {
            worldWhitelist.addAll(plugin.getConfig().getStringList("active-worlds"));
        }
    }

    /* package */ Set<SafeBlock> getBroadcastBlacklist() {
        return broadcastBlacklist;
    }

    /* package */ Set<String> getWorldWhitelist() {
        return worldWhitelist;
    }

    /* package */ boolean isWorldWhitelistActive() {
        return worldWhitelistActive;
    }


}
