package me.joshuaemq.commands;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class FilterCommand implements CommandExecutor {
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

    if (cmd.getLabel().equalsIgnoreCase("toggledrops")) {
      plugin.createPlayerFile(p);
      if (args.length == 0) {
        if (!data.isFilterEnabled()) {
          data.setFilterEnabled(true);
          p.sendMessage(ChatColor.RED + "You enabled your loot filter!");
        }
        else if (data.isFilterEnabled()) {
          data.setFilterEnabled(false);
          p.sendMessage(ChatColor.GREEN + "You disabled your loot filter!");
        }

      } else if (args[0].equalsIgnoreCase("add")) {
        if (!(data.isFilterEnabled())) {
          if (args.length == 1) {
            p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + "Common" + ChatColor.BLUE + " Uncommon" + ChatColor.DARK_PURPLE + " Rare" + ChatColor.RED + " Epic" + ChatColor.GOLD + " Unique" + ChatColor.BLUE + " Enchantment" + ChatColor.GOLD + " Gem" + ChatColor.DARK_GREEN + " Scroll");
            return false;
          }

          String addition = "";
          int i = 1;
          while (i < args.length) {
            addition = String.valueOf(addition) + args[i];
            ++i;
          }
          if (!((addition = addition.trim()).equalsIgnoreCase("Common") || addition.equalsIgnoreCase("Uncommon") || addition.equalsIgnoreCase("Rare") || addition.equalsIgnoreCase("Epic") || addition.equalsIgnoreCase("Unique" ) || addition.equalsIgnoreCase("Gem" ) || addition.equalsIgnoreCase("Enchantment" ) || addition.equalsIgnoreCase("Scroll" ))) {
            p.sendMessage(ChatColor.RED + "Invalid Argument");
            return false;
          }
          String Addition = String.valueOf(addition.substring(0, 1).toUpperCase()) + addition.substring(1);

          if (!(data.getLootFilterEntries().contains(Addition))) {
            List<String> lootFilter = data.getLootFilterEntries();
            lootFilter.add(Addition);
            p.sendMessage(ChatColor.GREEN + Addition + " was added to your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + Addition + " is already in your loot filter!");
          }
        } else {
          p.sendMessage(ChatColor.RED + "You must disable ToggleDrops to edit your loot filter!");
          return false;
        }

        //REMOVE ITEM FILTER SECTION

      } else if (args[0].equalsIgnoreCase("remove")) {
        if (!(data.isFilterEnabled())) {
          if (args.length == 1) {
              p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + "Common" + ChatColor.BLUE + " Uncommon" + ChatColor.DARK_PURPLE + " Rare" + ChatColor.RED + " Epic" + ChatColor.GOLD + " Unique" + ChatColor.BLUE + " Enchantment" + ChatColor.GOLD + " Gem" + ChatColor.DARK_GREEN + " Scroll");
            return false;
          }
          String removal = "";
          int i = 1;
          while (i < args.length) {
            removal = String.valueOf(removal) + args[i];
            ++i;
          }
            if (!((removal = removal.trim()).equalsIgnoreCase("Common") || removal.equalsIgnoreCase("Uncommon") || removal.equalsIgnoreCase("Rare") || removal.equalsIgnoreCase("Epic") || removal.equalsIgnoreCase("Unique" ) || removal.equalsIgnoreCase("Gem" ) || removal.equalsIgnoreCase("Enchantment" ) || removal.equalsIgnoreCase("Scroll" ))) {
            p.sendMessage(ChatColor.RED + "Invalid Argument");
            return false;
          }

          String Removal = String.valueOf(removal.substring(0, 1).toUpperCase()) + removal.substring(1);
          if (data.getLootFilterEntries().contains(Removal)) {
            List<String> lootFilter = data.getLootFilterEntries();
            lootFilter.remove(Removal);
            p.sendMessage(ChatColor.RED + Removal + " was removed from your loot filter!");
          } else {
            p.sendMessage(ChatColor.RED + Removal + " is not in your loot filter!");
          }

        } else {
          p.sendMessage(ChatColor.RED + "You must disable ToggleDrops to edit your loot filter!");
          return false;
        }

      } else if (args[0].equalsIgnoreCase("list")) {
        p.sendMessage(ChatColor.GREEN + "Filtered Items: " + ChatColor.RED + data.getLootFilterEntries().toString().trim());

      } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
        p.sendMessage(ChatColor.GREEN + "-=+=-" + ChatColor.GOLD + " Toggle Drops " + ChatColor.GREEN + "-=+=-");
        p.sendMessage(ChatColor.GRAY + "Toggle Drops allows you to configure a whitelist of items you are able to pickup!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops " + ChatColor.GRAY + "- Enable/Disable loot filter!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops add " + ChatColor.GRAY + "- To add an item type to your loot filter!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops remove " + ChatColor.GRAY + "- To remove an item type from your loot filter!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops list " + ChatColor.GRAY + "- To display your loot filter!");
        p.sendMessage(ChatColor.GRAY + "Sneaking while your loot filter is active allows you to pick up a non-filtered item!");
        p.sendMessage(ChatColor.GREEN + "=========================");
      } else {
        p.sendMessage(ChatColor.RED + "Invalid Argument!");
      }
    }
    return true;
  }
}
