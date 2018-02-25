package me.joshuaemq.listeners;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
  private File fileName;
  private FileConfiguration config;

    public FileConfiguration getPlayerConfig() {
        return this.config;
    }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    fileName = new File(plugin.getDataFolder(), p.getUniqueId().toString() + ".yml");

    if (!fileName.exists()) {
      try {
        fileName.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      config = YamlConfiguration.loadConfiguration(fileName);

      try {
        config.save(fileName);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      p.sendMessage("Your file was created!");
    }
    else {
      p.sendMessage("Your File Exists!");
    }

    if (!plugin.getPlayerFilterManager().getPlayerFilterMap().containsKey(p.getUniqueId())) {
      PlayerFilterData data = new PlayerFilterData();
      data.setFilterEnabled(this.getPlayerConfig().getBoolean("filterEnabled"));
      data.setLootFilterEntries(new ArrayList<>());
      plugin.getPlayerFilterManager().getPlayerFilterMap().put(p.getUniqueId(), data);
    }
  }
}