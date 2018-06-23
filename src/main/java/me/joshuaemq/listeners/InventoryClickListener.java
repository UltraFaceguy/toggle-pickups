package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import me.joshuaemq.menus.FilterGuiMenu;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
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
  public void onInvyClick(InventoryClickEvent e) {
    if (e.isCancelled()) {
      return;
    }
    if (e.getWhoClicked().getOpenInventory() == null
        || e.getWhoClicked().getOpenInventory().getTopInventory() == null) {
      return;
    }
    if (!plugin.getFilterGuiManager().getOpenMenus()
        .contains(e.getWhoClicked().getOpenInventory().getTopInventory())) {
      return;
    }
    e.setCancelled(true);
    if (e.getCurrentItem().getType() != Material.EMERALD_BLOCK
        && e.getCurrentItem().getType() != Material.REDSTONE_BLOCK) {
      return;
    }
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
      if (stackName.equals(setting.getName())) {
        PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap()
            .get(owner.getUniqueId());
        if (data.getLootFilterEntries().contains(setting)) {
          data.getLootFilterEntries().remove(setting);
          owner.playSound(owner.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.5f);
        } else {
          data.getLootFilterEntries().add(setting);
          owner.playSound(owner.getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 0.75f);
        }
        FilterGuiMenu.setToggleButton(stack, setting.getName(),
            data.getLootFilterEntries().contains(setting));
        plugin.getPlayerFilterManager().getPlayerFilterMap().put(owner.getUniqueId(), data);
        return;
      }
    }
  }
}
