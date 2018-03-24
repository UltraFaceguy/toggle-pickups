package me.joshuaemq.managers;

import java.util.ArrayList;
import java.util.List;
import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.menus.FilterGuiMenu;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FilterGuiManager {

  private TogglePickupsPlugin plugin;
  private List<Inventory> openMenus;

  public FilterGuiManager(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
    this.openMenus = new ArrayList<>();
  }

  public List<Inventory> getOpenMenus() {
    return openMenus;
  }

  public void open(Player player) {
    FilterGuiMenu menu = new FilterGuiMenu(plugin, player);
    openMenus.add(menu.getInventory());
  }

  public void closeAllMenus() {
    for (Inventory inventory : openMenus) {
      ((Player)inventory.getHolder()).closeInventory();
    }
    openMenus = new ArrayList<>();
  }
}
