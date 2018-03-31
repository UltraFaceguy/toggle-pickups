package me.joshuaemq;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import me.joshuaemq.commands.FilterCommand;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import me.joshuaemq.listeners.InventoryClickListener;
import me.joshuaemq.listeners.ItemPickupListener;
import me.joshuaemq.listeners.PlayerJoinListener;
import me.joshuaemq.managers.FilterGuiManager;
import me.joshuaemq.managers.PlayerFilterManager;
import me.joshuaemq.tasks.SaveTask;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePickupsPlugin extends JavaPlugin {

    private PlayerFilterManager playerFilterManager;
    private FilterGuiManager filterGuiManager;

    private SaveTask saveTask;

    public void createPlayerFile(Player player) {
        createPlayerFile(player.getUniqueId());
    }

    public void createPlayerFile(UUID uuid) {

        File fileName = new File(getDataFolder() + "/data", uuid + ".yml");

        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(fileName);

            try {
                config.save(fileName);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        if (!(getPlayerFilterManager().getPlayerFilterMap().containsKey(uuid))) {

            File playersFile = new File(getDataFolder() + "/data", uuid + ".yml");
            FileConfiguration playerFile = YamlConfiguration.loadConfiguration(playersFile);

            if (playerFile.get(uuid.toString()) == null) {
                PlayerFilterData data = new PlayerFilterData();
                //data.setFilterEnabled(playerFile.getBoolean(uuid + ".drops-enabled"));
                data.setLootFilterEntries(new ArrayList<>());
                getPlayerFilterManager().getPlayerFilterMap().put(uuid, data);
            } else {
                PlayerFilterData data = new PlayerFilterData();
                //data.setFilterEnabled(playerFile.getBoolean(uuid + ".drops-enabled"));
                List<String> playersLootEntries = playerFile.getStringList(uuid + ".loot-filter-entries");
                List<FilterSetting> playerSettings = new ArrayList<>();
                for (String str : playersLootEntries) {
                    FilterSetting setting = FilterSetting.valueOf(str);
                    playerSettings.add(setting);
                }
                data.setLootFilterEntries(playerSettings);
                getPlayerFilterManager().getPlayerFilterMap().put(uuid, data);
            }
        }
    }

    public void onEnable() {
        playerFilterManager = new PlayerFilterManager();
        filterGuiManager = new FilterGuiManager(this);
        saveTask = new SaveTask(this);

        saveTask.runTaskTimer(this,
                250 * 20L, // 4m10s delay
                300 * 20L // 5m repeat period
        );

        Bukkit.getPluginManager().registerEvents(new ItemPickupListener(this), this);
        Bukkit.getPluginManager().registerEvents(new InventoryClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(this), this);

        this.getCommand("toggledrops").setExecutor(new FilterCommand(this));

        getConfig().options().copyDefaults(true);
        saveConfig();
        reloadConfig();
        loadOnlinePlayers();

        Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Enabled!");
    }

    public void onDisable() {
        saveTask.run();
        saveTask.cancel();
        HandlerList.unregisterAll(this);
        playerFilterManager = null;
        filterGuiManager.closeAllMenus();
        filterGuiManager = null;
        saveTask = null;

        Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Disabled!");
    }

    private void loadOnlinePlayers() {
        System.out.println("Loading data files for all online players...");
        if (getDataFolder() == null) {
            System.out.println("ERROR! NO DATA FOLDER FOUND! UH OH!");
            return;
        }
        int loadedPlayers = 0;
        for (Player player : Bukkit.getOnlinePlayers()) {
            File dataFile = new File(getDataFolder() + "/data", player.getUniqueId() + ".yml");
            if (!dataFile.exists()) {
                continue;
            }
            loadPlayerFile(dataFile);
            loadedPlayers++;
        }
        System.out.println("Loaded data for all online players! (" + loadedPlayers + " entries)");
    }

    public void loadPlayerFile(File file) {
        FileConfiguration playerFile2 = YamlConfiguration.loadConfiguration(file);

        String fileName = file.getName();
        int index = fileName.indexOf(".");
        String uuidString = fileName.substring(0, index);

        List<String> playerFilterEntries = playerFile2.getStringList(uuidString + ".loot-filter-entries");

        List<FilterSetting> playerSettings = new ArrayList<>();
        for (String s : playerFilterEntries) {
            FilterSetting setting = FilterSetting.valueOf(s);
            playerSettings.add(setting);
        }

        PlayerFilterData playerFilterData = new PlayerFilterData();
        //playerFilterData.setFilterEnabled(dropsEnabled);
        playerFilterData.setLootFilterEntries(playerSettings);

        UUID uuid = UUID.fromString(uuidString);
        playerFilterManager.getPlayerFilterMap().put(uuid, playerFilterData);
    }

    public void savePlayerData(UUID uuid) {
        if (getDataFolder() == null) {
            System.out.println("ERROR! NO DATA FOLDER FOUND! UH OH!");
            return;
        }
        PlayerFilterData playerData = getPlayerFilterManager().getPlayerFilterMap().get(uuid);
        File playerFile = new File(getDataFolder() + "/data", uuid.toString() + ".yml");
        if (!playerFile.exists()) {
            createPlayerFile(uuid);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(playerFile);

        List<String> entries = new ArrayList<>();
        for (FilterSetting setting : playerData.getLootFilterEntries()) {
            entries.add(setting.toString());
        }
        config.set(uuid + ".loot-filter-entries", entries);

        try {
            config.save(playerFile);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public PlayerFilterManager getPlayerFilterManager () {
        return playerFilterManager;
    }

    public FilterGuiManager getFilterGuiManager () {
        return filterGuiManager;
    }
}