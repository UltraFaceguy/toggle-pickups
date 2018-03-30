package me.joshuaemq.menus;

import java.util.ArrayList;
import java.util.List;
import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class FilterGuiMenu {

  private TogglePickupsPlugin plugin;
  private Inventory inventory;
  private final static ItemStack BORDER_ITEM = generateBorderItem();
  private final static String MENU_NAME = ChatColor.DARK_GREEN + "LootFilter";

  public FilterGuiMenu(TogglePickupsPlugin plugin, Player owner) {
    this.plugin = plugin;
    this.inventory = Bukkit.createInventory(owner, 36, MENU_NAME);
    fillInventory(owner);
    openInventory(owner);
  }

  private void fillInventory(Player owner) {
    for (int i = 0; i <= inventory.getSize() - 1; i++) {
      // Fills in the border.
      if (i < 10 || i == 17 || i == 18 || i > 25) {
        inventory.setItem(i, BORDER_ITEM.clone());
        continue;
      }
      // Starts at slot 10 for settings icons
      int settingSlot = 10;
      PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(owner.getUniqueId());
      for (FilterSetting setting : FilterSetting.values()) {
        ItemStack settingIcon = new ItemStack(Material.EMERALD_BLOCK);
        setToggleButton(settingIcon, setting.getName(), data.getLootFilterEntries().contains(setting));
        inventory.setItem(settingSlot, settingIcon);
        settingSlot++;
        // If we reach the border, roll over ot next line. Yeah, its ugly.
        if (settingSlot == 17) {
          settingSlot = 19;
        }
      }
    }
  }

  private void openInventory(Player player) {
    player.playSound(player.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1.0f, 0.7f);
    player.openInventory(inventory);
  }

  public Inventory getInventory() {
    return inventory;
  }

  public static void setToggleButton(ItemStack itemStack, String settingName, boolean enabled) {
    ItemMeta meta = itemStack.getItemMeta();
    List<String> lore = new ArrayList<>();
    lore.add(ChatColor.WHITE + "" + ChatColor.BOLD + "Click to toggle!");
    if (enabled) {
      meta.setDisplayName(ChatColor.RED + "" + ChatColor.BOLD + settingName);
      itemStack.setType(Material.REDSTONE_BLOCK);
      lore.add(ChatColor.GRAY + "Item pickup " + ChatColor.RED + "" + ChatColor.BOLD + "DISABLED");
    } else {
      meta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + settingName);
      itemStack.setType(Material.EMERALD_BLOCK);
      lore.add(ChatColor.GRAY + "Item pickup " + ChatColor.GREEN + "" + ChatColor.BOLD + "ENABLED");
    }
    meta.setLore(lore);
    itemStack.setItemMeta(meta);
  }

  private static ItemStack generateBorderItem() {
    ItemStack itemStack = new ItemStack(Material.BEDROCK);
    ItemMeta meta = itemStack.getItemMeta();
    meta.setDisplayName(ChatColor.BLACK + "");
    itemStack.setItemMeta(meta);
    return itemStack;
  }
}
