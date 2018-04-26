package me.joshuaemq.tasks;

import java.util.*;

import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {

    private final TogglePickupsPlugin plugin;

    public SaveTask(TogglePickupsPlugin plugin) {
        this.plugin = plugin;
    }

    public void run() {
        System.out.println("Saving ToggleDrops data...");
        List<UUID> playersToRemove = new ArrayList<UUID>();

        for (UUID key : plugin.getPlayerFilterManager().getPlayerFilterMap().keySet()) {
            plugin.savePlayerData(key);
            playersToRemove.add(key);
        }

        for (UUID uuid : playersToRemove) {
            if (Bukkit.getOfflinePlayer(uuid).equals(true)) {
                plugin.getPlayerFilterManager().getPlayerFilterMap().remove(uuid);
            }
        }
        System.out.println("ToggleDrops data saved!");
    }
}
