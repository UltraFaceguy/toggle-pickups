package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
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
    Player p = (Player)e.getEntity();
    String lootFilterItems = plugin.getMap().get(p.getUniqueId().toString());
    String mapToArray = String.valueOf(p.getUniqueId().toString()) + " " + lootFilterItems;

    if (plugin.getDropsToggledList().contains(mapToArray)) {
      e.setCancelled(true);
      ItemStack item = e.getItem().getItemStack();
      if (item.hasItemMeta()) {
        ItemMeta meta = item.getItemMeta();
        List<String> lore = meta.getLore();

        ArrayList<String> LootFilterList = new ArrayList<>();
        for(String word : lootFilterItems.split(" ")) {
          LootFilterList.add(word);
        }
        for (String LootFilter : LootFilterList) {
          if (lore.toString().contains("REWARDS!") || lore.toString().contains(LootFilter)) {
            e.setCancelled(false);
          }
        }
        LootFilterList.clear();

      }
      else {
        return;
      }
    }
  }
}
