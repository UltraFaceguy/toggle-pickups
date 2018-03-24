package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
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
    Player playerInEvent = (Player) e.getEntity();
    if (playerInEvent.isSneaking()) {
      return;
    }
    if (!(playerInEvent.hasPermission("toggledrops.use"))) {
      return;
    }
    PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(e.getEntity().getUniqueId());
    //if (data == null || !data.isFilterEnabled()) {
    //  return;
    //}
    if (data == null) {
      return;
    }
    ItemStack item = e.getItem().getItemStack();
    if (!item.hasItemMeta()) {
      if (data.getLootFilterEntries().contains(FilterSetting.JUNK)) {
        e.setCancelled(true);
        return;
      }
    }
    ItemMeta meta = item.getItemMeta();
    List<String> lore = meta.getLore();
    if (lore == null || lore.size() == 0) {
      if (data.getLootFilterEntries().contains(FilterSetting.JUNK)) {
        e.setCancelled(true);
        return;
      }
    }
    String itemName = item.getItemMeta().getDisplayName();
    String itemNameNoColor = ChatColor.stripColor(itemName);
    if ("REWARD!".equals(itemNameNoColor) || "(Faceguy Crest)".equals(itemNameNoColor)) {
      return;
    }
    String itemLoreNoColor = ChatColor.stripColor(lore.toString());
    for (FilterSetting setting : data.getLootFilterEntries()) {
      if (setting.getLoreFilter() != null && itemLoreNoColor.contains(setting.getLoreFilter())) {
        e.setCancelled(true);
        return;
      }
      if (setting.getNameFilter() != null && itemNameNoColor.contains(setting.getNameFilter())) {
        if (setting.getSecondaryNameFilter() == null) {
          e.setCancelled(true);
          return;
        }
        if (itemNameNoColor.endsWith(setting.getSecondaryNameFilter())) {
          e.setCancelled(true);
          return;
        }
      }
    }
  }
}
