package me.joshuaemq;

import java.util.ArrayList;

import java.util.UUID;
import me.joshuaemq.listeners.ItemPickupListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public class TogglePickupsPlugin extends JavaPlugin {

	private ArrayList<UUID> playersDropToggled = new ArrayList<>();
	private FileConfiguration userConfig = null;
	
	public void onEnable() {
		Bukkit.getPluginManager().registerEvents(new ItemPickupListener(this), this);
		Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Enabled!");
		new PlayerDataConfig(this);
		System.out.println("This is a message from the main class!");
	}
	
	public void onDisable() {
		HandlerList.unregisterAll(this);
		Bukkit.getServer().getLogger().info("Toggleable Drops By Joshuaemq Disabled!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args ) {
		Player player = (Player) sender;
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.BLUE + "Console can not run this command!");
			return false;
		}
		if (cmd.getName().equalsIgnoreCase("toggledrops")) {
			if (!(playersDropToggled.contains(player.getUniqueId()))) {
				player.sendMessage(ChatColor.RED + "You can no longer pickup items!");
				playersDropToggled.add(player.getUniqueId());
			} else if (playersDropToggled.contains(player.getUniqueId())) {
				playersDropToggled.remove(player.getUniqueId());
				player.sendMessage(ChatColor.GREEN + "You can now pickup items!");
			}
		}
	return true;	
	}

	public ArrayList<UUID> getDropsToggledList() {
		return playersDropToggled;
	}

	public FileConfiguration getUserConfig() {
		return userConfig;
	}

	public void setUserConfig(FileConfiguration fileConfiguration) {
		this.userConfig = fileConfiguration;
	}
}
