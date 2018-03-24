package me.joshuaemq.listeners;

import static sun.audio.AudioPlayer.player;

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

  private TogglePickupsPlugin plugin;

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
    if (event.getItem().hasMetadata("TD-" + player.getName())) {
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
    List<String> lore = meta.getLore();
    if (lore == null || lore.size() == 0) {
      if (data.getLootFilterEntries().contains(FilterSetting.JUNK)) {
        cancelItemPickupAndSetItemMeta(event);
      }
      return;
    }
    String itemName = item.getItemMeta().getDisplayName();
    String itemNameNoColor = ChatColor.stripColor(itemName);
    if ("REWARD!".equals(itemNameNoColor) || "(Faceguy Crest)".equals(itemNameNoColor)) {
      return;
    }
    String itemLoreNoColor = ChatColor.stripColor(lore.toString());
    for (FilterSetting setting : data.getLootFilterEntries()) {
      if (setting.getLoreFilter() != null && itemLoreNoColor.contains(setting.getLoreFilter())) {
        cancelItemPickupAndSetItemMeta(event);
        return;
      }
      if (setting.getNameFilter() != null && itemNameNoColor.contains(setting.getNameFilter())) {
        if (setting.getSecondaryNameFilter() == null) {
          cancelItemPickupAndSetItemMeta(event);
          return;
        }
        if (itemNameNoColor.endsWith(setting.getSecondaryNameFilter())) {
          cancelItemPickupAndSetItemMeta(event);
          return;
        }
      }
    }
  }

  private void cancelItemPickupAndSetItemMeta(EntityPickupItemEvent event) {
    Player player = (Player) event.getEntity();
    event.getItem().setMetadata("TD-" + player.getName(), new FixedMetadataValue(plugin, true));
    Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
      @Override
      public void run() {
        if (event.getItem().isValid()) {
          event.getItem().removeMetadata("TD-" + player.getName(), plugin);
        }
      }
    }, 60L);
    event.setCancelled(true);
  }
}
