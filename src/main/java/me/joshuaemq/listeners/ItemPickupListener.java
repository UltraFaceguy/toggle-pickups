package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemPickupListener implements Listener {

  private TogglePickupsPlugin plugin;

  public ItemPickupListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler
  public void onEntityPickup(EntityPickupItemEvent event) {
    if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
      return;
    }
    if (plugin.getPlayerFilterManager().getPlayerFilterMap().containsKey(event.getEntity().getUniqueId())) {
      ItemStack item = event.getItem().getItemStack();
      ItemMeta meta = item.getItemMeta();
      if (item.hasItemMeta() && "REWARD!".equalsIgnoreCase(ChatColor.stripColor(meta.getDisplayName()))) {
          return;
      }
      event.setCancelled(true);
    }
  }
}
