package me.joshuaemq.tasks;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTask extends BukkitRunnable {

  private final TogglePickupsPlugin plugin;

  public SaveTask(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  public void run() {

    // This right here pulls from the global list of saved playerdata;
    plugin.getPlayerFilterManager().getPlayerFilterMap();

    // You can do things like this
    PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(UUID.fromString("PLAYERS-UUID-HERE"));
    System.out.println("Is player's filter on?: " + data.isFilterEnabled());
    System.out.println("All of the player's added filters: " + data.getLootFilterEntries());

    // To edit those values, we'll need to update them in the manager...
    PlayerFilterData data2 = plugin.getPlayerFilterManager().getPlayerFilterMap().get(UUID.fromString("PLAYERS-UUID-HERE"));
    List<String> filterEntries = new ArrayList<>();
    filterEntries.add("dank");
    filterEntries.add("memes");
    data2.setFilterEnabled(false);
    data2.setLootFilterEntries(filterEntries);
    plugin.getPlayerFilterManager().getPlayerFilterMap().put(UUID.fromString("PLAYERS-UUID-HERE"), data2);

    // Ideally, you'd use this save task to loop through all the things in that manager's filter map
    // and save them all to config, then remove all non-online players from the filter map.
    // Also it should be noted that there was a bug before, that made the plugin only work for
    // one player! Use the maps to prevent this :O

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
