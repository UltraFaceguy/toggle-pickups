package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import org.bukkit.metadata.FixedMetadataValue;

public class ItemPickupListener implements Listener {

  private final TogglePickupsPlugin plugin;

  public ItemPickupListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority= EventPriority.HIGH)
  public void onEntityPickup(EntityPickupItemEvent event) {
    if (event.isCancelled() || !(event.getEntity() instanceof Player)) {
      return;
    }
    Player playerInEvent = (Player) event.getEntity();
    if (playerInEvent.isSneaking()) {
      return;
    }
    if (event.getItem().hasMetadata("TD-" + playerInEvent.getName())) {
      event.setCancelled(true);
      return;
    }
    if (!(playerInEvent.hasPermission("toggledrops.use"))) {
      return;
    }
    PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(event.getEntity().getUniqueId());
    //if (data == null || !data.isFilterEnabled()) {
    //  return;
    //}
    if (data == null) {
      return;
    }
    ItemStack item = event.getItem().getItemStack();
    if (!item.hasItemMeta()) {
      if (data.getLootFilterEntries().contains(FilterSetting.JUNK)) {
        cancelItemPickupAndSetItemMeta(event);
      }
      return;
    }
    ItemMeta meta = item.getItemMeta();
    String itemName = item.getItemMeta().getDisplayName();
    String itemNameNoColor = ChatColor.stripColor(itemName);
    if ("REWARD!".equals(itemNameNoColor) || "(Faceguy Crest)".equals(itemNameNoColor)) {
      return;
    }
    List<String> lore = meta.getLore();
    if (lore == null || lore.size() == 0) {
      if (data.getLootFilterEntries().contains(FilterSetting.JUNK)) {
        cancelItemPickupAndSetItemMeta(event);
      }
      return;
    }
    String itemLoreNoColor = ChatColor.stripColor(lore.toString());

    for (FilterSetting setting : FilterSetting.VALUES) {
      if (!data.getLootFilterEntries().contains(setting)) {
        if (itemMatches(itemNameNoColor, itemLoreNoColor, setting)) {
          return;
        }
      }
      if (itemMatches(itemNameNoColor, itemLoreNoColor, setting)) {
        cancelItemPickupAndSetItemMeta(event);
        return;
      }
    }
  }

  private boolean itemMatches(String name, String lore, FilterSetting setting) {
    if (setting.getNameFilter() != null && name.contains(setting.getNameFilter())) {
      if (setting.getSecondaryNameFilter() == null) {
        return true;
      }
      return name.endsWith(setting.getSecondaryNameFilter());
    }
    if (setting.getLoreFilter() != null && lore.contains(setting.getLoreFilter())) {
      if (setting.getSecondaryLoreFilter() == null) {
        return true;
      }
      return lore.contains(setting.getSecondaryLoreFilter());
    }
    return false;
  }

  private void cancelItemPickupAndSetItemMeta(EntityPickupItemEvent event) {
    Player eventPlayer = (Player) event.getEntity();
    event.getItem().setMetadata("TD-" + eventPlayer.getName(), new FixedMetadataValue(plugin, true));
    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
      @Override
      public void run() {
        if (event.getItem().isValid() && event.getItem().hasMetadata("TD-" + eventPlayer.getName())) {
          event.getItem().removeMetadata("TD-" + eventPlayer.getName(), plugin);
        }
      }
    }, 60L);
    event.setCancelled(true);
  }
}
