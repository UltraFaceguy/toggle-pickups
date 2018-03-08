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
      Player playerInEvent = (Player) e.getEntity();
      if (e.isCancelled() || !(e.getEntity() instanceof Player) || playerInEvent.isSneaking()) {
          return;
      }
      if (!(playerInEvent.hasPermission("toggledrops.use"))) {
        return;
      }
      PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(e.getEntity().getUniqueId());
      if (data == null) {
          return;
      }
      if (data.isFilterEnabled()) {
          e.setCancelled(true);
          ItemStack item = e.getItem().getItemStack();
          if (item.hasItemMeta()) {
              ItemMeta meta = item.getItemMeta();
              List<String> lore = meta.getLore();
              String itemName = item.getItemMeta().getDisplayName();
              String itemNameNoColor = ChatColor.stripColor(itemName);
              if ("REWARD!".equals(ChatColor.stripColor(meta.getDisplayName())) || "(Faceguy Crest)".equals(ChatColor.stripColor(meta.getDisplayName()))) {
                  e.setCancelled(false);
                  return;
              }

              for (String str : data.getLootFilterEntries()) {
                  if (ChatColor.stripColor(lore.toString()).contains(str) || itemNameNoColor.contains(str)) {
                      e.setCancelled(false);
                  }
              }
          }
    }
  }
}
