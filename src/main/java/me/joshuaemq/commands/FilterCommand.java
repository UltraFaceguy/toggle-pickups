package me.joshuaemq.commands;

import me.joshuaemq.TogglePickupsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FilterCommand extends TogglePickupsPlugin {

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    Player p = (Player)sender;
    if (!(sender instanceof Player)) {
      Bukkit.getServer().getLogger().info("Console can not run this command!");
      return false;
    }
    if (cmd.getName().equalsIgnoreCase("toggledrops")) {
      String playersLootFilter = map.get(p.getUniqueId().toString());
      String playerInList = String.valueOf(p.getUniqueId().toString()) + " " + playersLootFilter;

      if (args.length == 0) {
        if (!playersDropToggled.contains(playerInList)) {
          String lootFilterItems = map.get(p.getUniqueId().toString());
          String mapToArray = String.valueOf(p.getUniqueId().toString()) + " " + lootFilterItems;
          playersDropToggled.add(mapToArray);
          p.sendMessage(ChatColor.RED + "You can no longer pickup items!");
        }
        else if (playersDropToggled.contains(playerInList)) {
          String lootFilterItems = map.get(p.getUniqueId().toString());
          String mapToArray = String.valueOf(p.getUniqueId().toString()) + " " + lootFilterItems;
          playersDropToggled.remove(mapToArray);
          p.sendMessage(ChatColor.GREEN + "You can now pickup items!");
        }

      } else if (args[0].equalsIgnoreCase("add")) {
        if (!(playersDropToggled.contains(playerInList))) {
          if (args.length == 1) {
            p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + " Common," + ChatColor.BLUE + " Uncommon," + ChatColor.DARK_PURPLE + " Rare," + ChatColor.RED + " Epic," + ChatColor.GOLD + " Unique");
            return false;
          }

          String addition = "";
          int i = 1;
          while (i < args.length) {
            addition = String.valueOf(addition) + args[i];
            ++i;
          }
          if (!((addition = addition.trim()).equalsIgnoreCase("Common") || addition.equalsIgnoreCase("Uncommon") || addition.equalsIgnoreCase("Rare") || addition.equalsIgnoreCase("Epic") || addition.equalsIgnoreCase("Unique"))) {
            p.sendMessage(ChatColor.RED + "Invalid Argument");
            return false;
          }

          if (!(playersLootFilter.contains(addition))) {
            String Addition = String.valueOf(addition.substring(0, 1).toUpperCase()) + addition.substring(1);
            String newFilter = String.valueOf(playersLootFilter) + " " + Addition;
            map.put(p.getUniqueId().toString(), newFilter.trim());
            p.sendMessage(ChatColor.GREEN + Addition + " was added to your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + addition.substring(0, 1).toUpperCase() + " is already in your loot filter!");
          }
        } else {
          p.sendMessage(ChatColor.RED + "You must disable toggleDrops to edit your loot filter!");
          return false;
        }



        //REMOVE ITEM FILTER SECTION

      } else if (args[0].equalsIgnoreCase("remove")) {
        if (!(playersDropToggled.contains(playerInList))) {
          if (args.length == 1) {
            p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + " Common," + ChatColor.BLUE + " Uncommon," + ChatColor.DARK_PURPLE + " Rare," + ChatColor.RED + " Epic," + ChatColor.GOLD + " Unique");
            return false;
          }
          String removal = "";
          int i = 1;
          while (i < args.length) {
            removal = String.valueOf(removal) + args[i];
            ++i;
          }
          if (!((removal = removal.trim()).equalsIgnoreCase("Common") || removal.equalsIgnoreCase("Uncommon") || removal.equalsIgnoreCase("Rare") || removal.equalsIgnoreCase("Epic") || removal.equalsIgnoreCase("Unique"))) {
            p.sendMessage(ChatColor.RED + "Invalid Argument");
            return false;
          }

          String Removal = String.valueOf(removal.substring(0, 1).toUpperCase()) + removal.substring(1);
          if (playersLootFilter.contains(Removal)) {
            String newFilter = playersLootFilter.replace(Removal, "");
            map.put(p.getUniqueId().toString(), newFilter.trim());
            p.sendMessage(ChatColor.RED + Removal + " was removed from your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + removal.substring(0, 1).toUpperCase() + removal.substring(1) + " is not in your loot filter!");
          }

        } else {
          p.sendMessage(ChatColor.RED + "You must disable toggleDrops to edit your loot filter!");
          return false;
        }




      } else if (args[0].equalsIgnoreCase("list")) {
        String lootFilterDisplay = playersLootFilter.replace("REWARDS!", "");
        p.sendMessage(ChatColor.GREEN + "Filtered Items: " + ChatColor.RED + lootFilterDisplay.trim());

      } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
        p.sendMessage(ChatColor.GREEN + "-=+=- Toggleable Drops -=+=-");
        p.sendMessage(ChatColor.WHITE + "/toggleDrops <arg> " + ChatColor.GRAY + "- Enable/Disable loot filter!");
        p.sendMessage(ChatColor.WHITE + "<Add> " + ChatColor.GRAY + "- To add an item type to your item filter!");
        p.sendMessage(ChatColor.WHITE + "<Remove> " + ChatColor.GRAY + "- To remove an item type from your item filter!");
        p.sendMessage(ChatColor.WHITE + "<List> " + ChatColor.GRAY + "- To display your item filter!");
        p.sendMessage(ChatColor.GREEN + "=========================");
      } else {
        p.sendMessage(ChatColor.RED + "Invalid Argument!");
      }
    }
    return true;
  }
}
