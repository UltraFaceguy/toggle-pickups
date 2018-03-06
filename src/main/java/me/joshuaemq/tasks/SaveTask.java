package me.joshuaemq.tasks;

import java.io.File;
import java.io.IOException;
import java.util.*;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {

    private final TogglePickupsPlugin plugin;
    private File playerFile;
    private FileConfiguration config;

    public SaveTask(TogglePickupsPlugin plugin) {
        this.plugin = plugin;
    }

    public void saveAll() {
        if (plugin.getDataFolder() != null) {
            for (UUID key : plugin.getPlayerFilterManager().getPlayerFilterMap().keySet()) {

                PlayerFilterData playerData = plugin.getPlayerFilterManager().getPlayerFilterMap().get(key);

                playerFile = new File(plugin.getDataFolder(), key.toString() + ".yml");
                config = YamlConfiguration.loadConfiguration(playerFile);

                config.set(key + ".loot-filter-entries", playerData.getLootFilterEntries());
                config.set(key + ".drops-enabled", playerData.isFilterEnabled());

                try {
                    config.save(playerFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    List<String> playersToRemove = new ArrayList<>();
    public void run() {

        if (plugin.getDataFolder() != null) {
            for (UUID key : plugin.getPlayerFilterManager().getPlayerFilterMap().keySet()) {

                PlayerFilterData playerData = plugin.getPlayerFilterManager().getPlayerFilterMap().get(key);

                playerFile = new File(plugin.getDataFolder(), key.toString() + ".yml");
                config = YamlConfiguration.loadConfiguration(playerFile);

                config.set(key + ".drops-enabled", playerData.isFilterEnabled());
                config.set(key + ".loot-filter-entries", playerData.getLootFilterEntries());

                try {
                    config.save(playerFile);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                //remove offline players from map. //find keys that are offline - put them into a list for name in list remove from map. clear list
                if (Bukkit.getPlayer(key) == null) {
                    playersToRemove.add(key.toString());
                }
            }
            for (String playerInRemoveList : playersToRemove) {
                UUID offlinePlayerUUID = UUID.fromString(playerInRemoveList);
                plugin.getPlayerFilterManager().getPlayerFilterMap().remove(offlinePlayerUUID);
            }
            playersToRemove.clear();
        }
    }
}
