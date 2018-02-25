package me.joshuaemq.commands;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FilterCommand extends TogglePickupsPlugin {
  private TogglePickupsPlugin plugin;

  public FilterCommand(TogglePickupsPlugin plugin) {
    this.plugin = plugin;
  }

  public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
    Player p = (Player) sender;
    PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(p.getUniqueId());

    if (!(sender instanceof Player)) {
      Bukkit.getServer().getLogger().info("Console can not run this command!");
      return false;
    }

    if (cmd.getName().equalsIgnoreCase("toggledrops")) {

      if (args.length == 0) {
        if (!data.isFilterEnabled()) {
          data.setFilterEnabled(true);
          p.sendMessage(ChatColor.RED + "You can no longer pickup items!");
        }
        else if (data.isFilterEnabled()) {
          data.setFilterEnabled(false);
          p.sendMessage(ChatColor.GREEN + "You can now pickup items!");
        }

      } else if (args[0].equalsIgnoreCase("add")) {
        if (!(data.isFilterEnabled())) {
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
          String Addition = String.valueOf(addition.substring(0, 1).toUpperCase()) + addition.substring(1);

          if (!(data.getLootFilterEntries().contains(Addition))) {
            List<String> lootFilter = data.getLootFilterEntries();
            lootFilter.add(Addition);
            p.sendMessage(ChatColor.GREEN + Addition + " was added to your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + addition.substring(0, 1).toUpperCase() + " is already in your loot filter!");
          }
        } else {
          p.sendMessage(ChatColor.RED + "You must disable ToggleDrops to edit your loot filter!");
          return false;
        }

        //REMOVE ITEM FILTER SECTION

      } else if (args[0].equalsIgnoreCase("remove")) {
        if (!(data.isFilterEnabled())) {
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
          if (data.getLootFilterEntries().contains(Removal)) {
            List<String> lootFilter = data.getLootFilterEntries();
            lootFilter.add(Removal);
            p.sendMessage(ChatColor.RED + Removal + " was removed from your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + removal.substring(0, 1).toUpperCase() + removal.substring(1) + " is not in your loot filter!");
          }

        } else {
          p.sendMessage(ChatColor.RED + "You must disable ToggleDrops to edit your loot filter!");
          return false;
        }




      } else if (args[0].equalsIgnoreCase("list")) {
        p.sendMessage(ChatColor.GREEN + "Filtered Items: " + ChatColor.RED + data.getLootFilterEntries().toString().trim());

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
