package me.joshuaemq.menus;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class FilterGuiMenu {

  private TogglePickupsPlugin plugin;
  private Inventory inventory;
  private static ItemStack borderItem = generateBorderItem();

  public FilterGuiMenu(TogglePickupsPlugin plugin, Player owner) {
    this.plugin = plugin;
    this.inventory = Bukkit.createInventory(owner, 36, ChatColor.GREEN + "LootFilter");
    fillInventory(owner);
    openInventory(owner);
  }

  private void fillInventory(Player owner) {
    for (int i = 0; i <= inventory.getSize(); i++) {
      // Fills in the border.
      if (i < 9 || i % 8 == 0 || i % 9 == 0 || i > 27) {
        inventory.setItem(i, borderItem.clone());
        continue;
      }
      // Starts at slot 10 for settings icons
      int settingSlot = 10;
      PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(owner.getUniqueId());
      for (FilterSetting setting : FilterSetting.values()) {
        ItemStack settingIcon = new ItemStack(Material.WOOL);
        MaterialData materialData = settingIcon.getData();
        ItemMeta meta = settingIcon.getItemMeta();
        if (data.getLootFilterEntries().contains(setting)) {
          meta.setDisplayName(ChatColor.RED + setting.getName());
          materialData.setData(DyeColor.RED.getDyeData());
        } else {
          meta.setDisplayName(ChatColor.GREEN + setting.getName());
          materialData.setData(DyeColor.GREEN.getDyeData());
        }
        settingIcon.setData(materialData);
        inventory.setItem(settingSlot, settingIcon);
        settingSlot++;
        // If we reach the border, roll over ot next line. Yeah, its ugly.
        if (settingSlot == 17) {
          settingSlot = 19;
        }
      }
    }
  }

  public void openInventory(Player player) {
    player.openInventory(inventory);
  }

  public Inventory getInventory() {
    return inventory;
  }

  private static ItemStack generateBorderItem() {
    ItemStack itemStack = new ItemStack(Material.BEDROCK);
    ItemMeta meta = itemStack.getItemMeta();
    meta.setDisplayName(ChatColor.BLACK + "");
    itemStack.setItemMeta(meta);
    return itemStack;
  }
}
