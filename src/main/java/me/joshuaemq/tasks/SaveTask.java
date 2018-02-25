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

    for (UUID player : plugin.getPlayerFilterManager().getPlayerFilterMap().keySet()) {
      PlayerFilterData playerData = plugin.getPlayerFilterManager().getPlayerFilterMap().get(player);
      String pathToFile = player.toString() + ".yml";
      plugin.getConfig().set(pathToFile, playerData);
      plugin.saveConfig();
    }

    //remove offline players drop filter map

    Collection<? extends Player> getOnlinePlayers = Bukkit.getOnlinePlayers();
    List<String> onlinePlayers = new ArrayList<String>();

    for (Player p : getOnlinePlayers) {
      onlinePlayers.add(p.getUniqueId().toString());
    }

    List<String> playerToRemove = new ArrayList<String>();

    for (UUID listEntry : plugin.getPlayerFilterManager().getPlayerFilterMap().keySet()) {

      if (!(onlinePlayers.contains(listEntry))) {
        playerToRemove.add(listEntry.toString());
      }
    }

    for (String PlayerToRemove : playerToRemove) {
      UUID playerToRemoveUUID = UUID.fromString(PlayerToRemove);
      plugin.getPlayerFilterManager().getPlayerFilterMap().remove(PlayerToRemove);

    }
    playerToRemove.clear();
  }
}
