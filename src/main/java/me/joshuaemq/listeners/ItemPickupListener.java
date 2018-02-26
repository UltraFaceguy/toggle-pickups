package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemPickupListener implements Listener {

  private TogglePickupsPlugin plugin;

  public ItemPickupListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority= EventPriority.HIGH)
  public void onEntityPickup(EntityPickupItemEvent e) {
      if (e.isCancelled() || !(e.getEntity() instanceof Player)) {
          return;
      }
      PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(e.getEntity().getUniqueId());

      if (data.isFilterEnabled()) {
          e.setCancelled(true);
          ItemStack item = e.getItem().getItemStack();
          if (item.hasItemMeta()) {
              ItemMeta meta = item.getItemMeta();
              List<String> lore = meta.getLore();

              if ("REWARD!".equals(ChatColor.stripColor(meta.getDisplayName()))) {
                  e.setCancelled(false);
                  return;
              }
              for (String str : lore) {
                  if ((data.getLootFilterEntries().equals(ChatColor.stripColor(str)))) {
                      e.setCancelled(false);
                  }
              }
          }
    }
  }
}
