package me.joshuaemq.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
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
    if (plugin.get != null) {

      Collection<? extends Player> getOnlinePlayers = Bukkit.getOnlinePlayers();
      List<String> onlinePlayers = new ArrayList<>();

      for (Player p : getOnlinePlayers) {
        onlinePlayers.add(p.getUniqueId().toString());
      }

      List<String> playerToRemove = new ArrayList<>();

      for (String listEntry : playersDropToggled) {

        String arr[] = listEntry.split(" ", 2);
        String playerUUIDString = arr[0];

        if (!(onlinePlayers.contains(playerUUIDString))) {
          playerToRemove.add(listEntry);
        }
      }

      for (String PlayerToRemove : playerToRemove) {
        playersDropToggled.remove(PlayerToRemove);

      }
      playerToRemove.clear();

    }

    for (String player : map.keySet()) {
      String lootFilter = map.get(player);
      getConfig().set(String.valueOf(player) + ".LootFilter", lootFilter);
      saveConfig();
    }
  }
}
