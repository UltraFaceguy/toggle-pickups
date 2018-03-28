package me.joshuaemq.listeners;

import java.io.File;
import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

  private TogglePickupsPlugin plugin;

  public PlayerJoinListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player player = e.getPlayer();
    if (plugin.getPlayerFilterManager().getPlayerFilterMap().containsKey(player.getUniqueId())) {
      return;
    }
    File fileName = new File(plugin.getDataFolder() + "/data", player.getUniqueId().toString() + ".yml");
    if (!fileName.exists()) {
      return;
    }
    plugin.loadPlayerFile(fileName);
  }
}
