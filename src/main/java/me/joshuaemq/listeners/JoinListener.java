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
    File userFile = new File(getDataFolder()+File.separator+"playerPreferences"+File.separator+p.getUniqueId().toString()+".yml");
    if (!userFile.exists()) {
      try {
        userFile.createNewFile();
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      plugin.setUserConfig(YamlConfiguration.loadConfiguration(userFile));
      try {
        plugin.getUserConfig().save(userFile);
      } catch (IOException e1) {
        e1.printStackTrace();
      }
      p.sendMessage("Your file was created!");
      //add user to the list
    } else {
      p.sendMessage("Your File Exists!");
      //add user to the list
      //add user to the array
    }
  }

}
