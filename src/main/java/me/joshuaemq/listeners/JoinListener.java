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
  private File fileName;
  private FileConfiguration config;

    public FileConfiguration getPlayerConfig() {
        return this.config;
    }
  private FileConfiguration playerFile;

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
    }

    if (!(plugin.getPlayerFilterManager().getPlayerFilterMap().containsKey(p.getUniqueId()))) {

        File playersFile = new File(plugin.getDataFolder(), p.getUniqueId().toString() + ".yml");
        playerFile = YamlConfiguration.loadConfiguration(playersFile);

        if (playerFile.get(p.getUniqueId().toString()) == null) {

            PlayerFilterData data = new PlayerFilterData();
            data.setFilterEnabled(playerFile.getBoolean(p.getUniqueId().toString() + ".drops-enabled"));
            data.setLootFilterEntries(new ArrayList<>());
            plugin.getPlayerFilterManager().getPlayerFilterMap().put(p.getUniqueId(), data);
        } else {
            PlayerFilterData data = new PlayerFilterData();
            data.setFilterEnabled(playerFile.getBoolean(p.getUniqueId().toString() + ".drops-enabled"));
            List playersLootEntries = playerFile.getList(p.getUniqueId().toString() + ".loot-filter-entries");
            data.setLootFilterEntries(playersLootEntries);
            plugin.getPlayerFilterManager().getPlayerFilterMap().put(p.getUniqueId(), data);
        }

    }
  }
}