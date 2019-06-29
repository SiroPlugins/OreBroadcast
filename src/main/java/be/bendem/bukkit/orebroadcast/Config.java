package be.bendem.bukkit.orebroadcast;

import java.util.HashSet;
import java.util.Set;

class Config {

    private final OreBroadcast plugin;
    private final Set<SafeBlock> broadcastBlacklist = new HashSet<>();
    private final Set<String> disableWorlds = new HashSet<>();

    Config(OreBroadcast plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
    }

    void loadConfig() {

        plugin.reloadConfig();

        disableWorlds.addAll(plugin.getConfig().getStringList("disableWorlds"));

    }

    Set<SafeBlock> getBroadcastBlacklist() {
        return broadcastBlacklist;
    }

    Set<String> getDisableWorlds() {
        return disableWorlds;
    }

}