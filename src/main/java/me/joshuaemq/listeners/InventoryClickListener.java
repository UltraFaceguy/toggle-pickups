package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class InventoryClickListener implements Listener {

  private TogglePickupsPlugin plugin;

  public InventoryClickListener(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityPickup(InventoryClickEvent e) {
    if (!plugin.getFilterGuiManager().getOpenMenus().contains(e.getClickedInventory())) {
      return;
    }
    e.setCancelled(true);
    toggleSetting(e.getCurrentItem(), (Player) e.getWhoClicked());
  }

  private void toggleSetting(ItemStack stack, Player owner) {
    if (stack == null || stack.getType() == Material.AIR) {
      return;
    }
    if (stack.getItemMeta() == null) {
      return;
    }
    String stackName = ChatColor.stripColor(stack.getItemMeta().getDisplayName());
    if (stackName == null) {
      return;
    }
    for (FilterSetting setting : FilterSetting.values()) {
      if (stackName.startsWith(setting.getName())) {
        PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(owner.getUniqueId());
        if (data.getLootFilterEntries().contains(setting)) {
          data.getLootFilterEntries().remove(setting);
        } else {
          data.getLootFilterEntries().add(setting);
        }
        plugin.getPlayerFilterManager().getPlayerFilterMap().put(owner.getUniqueId(), data);
        return;
      }
    }
  }

}
