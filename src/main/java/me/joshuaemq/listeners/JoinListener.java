package me.joshuaemq.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import me.joshuaemq.data.PlayerFilterData;

public class JoinListener implements Listener {

  private TogglePickupsPlugin plugin;

  public JoinListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
      Player p = e.getPlayer();
      plugin.createPlayerFile(p);
  }
}