package me.joshuaemq;

import java.io.File;
import java.util.List;
import java.util.UUID;

import me.joshuaemq.commands.FilterCommand;
import me.joshuaemq.data.PlayerFilterData;
import me.joshuaemq.listeners.ItemPickupListener;
import me.joshuaemq.listeners.JoinListener;
import me.joshuaemq.managers.PlayerFilterManager;
import me.joshuaemq.tasks.SaveTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePickupsPlugin extends JavaPlugin {

    private PlayerFilterManager playerFilterManager;

    private SaveTask saveTask;

    public void onEnable() {
        playerFilterManager = new PlayerFilterManager();
        saveTask = new SaveTask(this);

        saveTask.runTaskTimer(this,
                4 * 60 * 20L,
                5 * 60 * 20L
        );

        Bukkit.getPluginManager().registerEvents(new ItemPickupListener(this), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        this.getCommand("toggledrops").setExecutor(new FilterCommand(this));

        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        loadPlayerData();

        Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Enabled!");
    }

    public void onDisable() {
        saveTask.saveAll();
        saveTask.cancel();
        HandlerList.unregisterAll(this);
        playerFilterManager = null;
        saveTask = null;

        Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Disabled!");
    }
    private FileConfiguration playerFile;

    public void loadPlayerData() {
        if (getDataFolder() != null) {

            for (File file : getDataFolder().listFiles()) {
                playerFile = YamlConfiguration.loadConfiguration(file);

                String fileName = file.getName();
                int index = fileName.indexOf(".");
                String str = fileName.substring(0, index);

                if (!(fileName.equals("config.yml"))) {
                    Boolean str1 = playerFile.getBoolean(str + ".drops-enabled");
                    List str2 = playerFile.getList(str + ".loot-filter-entries");

                    PlayerFilterData playerFilterData = new PlayerFilterData();
                    playerFilterData.setFilterEnabled(str1);
                    playerFilterData.setLootFilterEntries(str2);

                    UUID uuid = UUID.fromString(str);
                    playerFilterManager.getPlayerFilterMap().put(uuid, playerFilterData);
                }
            }
        }
    }

    public PlayerFilterManager getPlayerFilterManager () {
        return playerFilterManager;
    }
}