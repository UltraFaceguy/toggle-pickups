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

    public void run() {

        // This right here pulls from the global list of saved playerdata;
        // plugin.getPlayerFilterManager().getPlayerFilterMap();

        // You can do things like this
        //PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(UUID.fromString("PLAYERS-UUID-HERE"));
        //System.out.println("Is player's filter on?: " + data.isFilterEnabled());
        //System.out.println("All of the player's added filters: " + data.getLootFilterEntries());

        // To edit those values, we'll need to update them in the manager...
        //PlayerFilterData data2 = plugin.getPlayerFilterManager().getPlayerFilterMap().get(UUID.fromString("PLAYERS-UUID-HERE"));
        //List<String> filterEntries = new ArrayList<>();
        //filterEntries.add("dank");
        //filterEntries.add("memes");
        //data2.setFilterEnabled(false);
        //data2.setLootFilterEntries(filterEntries);
        //plugin.getPlayerFilterManager().getPlayerFilterMap().put(UUID.fromString("PLAYERS-UUID-HERE"), data2);

        // Ideally, you'd use this save task to loop through all the things in that manager's filter map
        // and save them all to config, then remove all non-online players from the filter map.
        // Also it should be noted that there was a bug before, that made the plugin only work for
        // one player! Use the maps to prevent this :O

        //Saves player data to config

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
                System.out.println("Player data saved for: " + key.toString());

                //remove offline players from map.

                Player onlinePlayer = Bukkit.getPlayer(key);
                if (!(onlinePlayer.isOnline())) {
                    Map offlinePlayer = plugin.getPlayerFilterManager().getPlayerFilterMap();
                    offlinePlayer.remove(key);
                    System.out.println("Offline Player Removed From Map: " + key.toString());
                }
            }

        } else {
            System.out.println("Folder is empty/non-existing");
        }
    }
}
