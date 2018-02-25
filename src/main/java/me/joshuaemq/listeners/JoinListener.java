package me.joshuaemq.listeners;

import java.io.File;
import java.io.IOException;
import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

  private TogglePickupsPlugin plugin;

  public JoinListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (!plugin.getConfig().contains(p.getUniqueId().toString())) {
      String defaultFilter = "REWARDS!";
      plugin.getConfig().set(String.valueOf(p.getUniqueId().toString()) + ".LootFilter", defaultFilter);
      plugin.saveConfig();
      String key = p.getUniqueId().toString();
      String lootFilterItems = plugin.getConfig().getString(String.valueOf(p.getUniqueId().toString()) + ".LootFilter");
      plugin.getMap().put(key, lootFilterItems);
    }
  }

}
