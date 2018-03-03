package me.joshuaemq;

import java.util.UUID;

import me.joshuaemq.commands.FilterCommand;
import me.joshuaemq.data.PlayerFilterData;
import me.joshuaemq.listeners.ItemPickupListener;
import me.joshuaemq.listeners.JoinListener;
import me.joshuaemq.managers.PlayerFilterManager;
import me.joshuaemq.tasks.SaveTask;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePickupsPlugin extends JavaPlugin {

	private PlayerFilterManager playerFilterManager;

	private SaveTask saveTask;

	public void onEnable() {
		playerFilterManager = new PlayerFilterManager();
		saveTask = new SaveTask(this);

		saveTask.runTaskTimer(this,
				0L,
				600L
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
		saveTask.cancel();
		HandlerList.unregisterAll(this);
		playerFilterManager = null;
		saveTask = null;

		Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Disabled!");
	}

	public void loadPlayerData() {
		if (getConfig().getKeys(false) != null) {
			for (String key : getConfig().getKeys(false)) {
				PlayerFilterData playerFilterData = new PlayerFilterData();
				playerFilterData.setFilterEnabled(getConfig().getBoolean(key + ".drops-enabled"));
				playerFilterData.setLootFilterEntries(getConfig().getStringList(key + ".loot-filter-entries"));
				UUID uuid = UUID.fromString(key);
				playerFilterManager.getPlayerFilterMap().put(uuid, playerFilterData);
			}
		} else {
			Bukkit.getServer().getLogger().info("toggleDrops: Config is currently NULL");
		}
	}

	public PlayerFilterManager getPlayerFilterManager() {
		return playerFilterManager;
	}

}