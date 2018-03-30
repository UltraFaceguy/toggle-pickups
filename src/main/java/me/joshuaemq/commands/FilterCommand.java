package me.joshuaemq.commands;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
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
      //if (args.length == 0) {
      //  if (!data.isFilterEnabled()) {
      //    data.setFilterEnabled(true);
      //    p.sendMessage(ChatColor.RED + "You enabled your loot filter!");
      //  }
      //  else if (data.isFilterEnabled()) {
      //    data.setFilterEnabled(false);
      //    p.sendMessage(ChatColor.GREEN + "You disabled your loot filter!");
      //  }
      //} else
      if (args.length == 0 || args[0].equalsIgnoreCase("menu")) {
        plugin.getFilterGuiManager().open(p);
      } else if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("?")) {
        p.sendMessage(ChatColor.GREEN + "-=+=-" + ChatColor.GOLD + " Toggle Drops " + ChatColor.GREEN + "-=+=-");
        p.sendMessage(ChatColor.GRAY + "Toggle Drops allows you to configure a whitelist of items you are able to pickup!");
        //p.sendMessage(ChatColor.WHITE + "/ToggleDrops " + ChatColor.GRAY + "- Enable/Disable loot filter!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops " + ChatColor.GRAY + "- Opens your loot filter menu!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops add " + ChatColor.GRAY + "- Manually add an item type to your loot filter!");
        p.sendMessage(ChatColor.WHITE + "/ToggleDrops remove " + ChatColor.GRAY + "- Manually remove an item type from your loot filter!");
        //p.sendMessage(ChatColor.WHITE + "/ToggleDrops list " + ChatColor.GRAY + "- To display your loot filter!");
        p.sendMessage(ChatColor.GRAY + "The loot filter is disabled while sneaking!");
        p.sendMessage(ChatColor.GREEN + "=========================");
      } else if (args[0].equalsIgnoreCase("add")) {
        if (args.length == 1) {
          p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + "Common" + ChatColor.BLUE + " Uncommon" + ChatColor.DARK_PURPLE + " Rare" + ChatColor.RED + " Epic" + ChatColor.GOLD + " Unique" + ChatColor.BLUE + " Enchantment" + ChatColor.GOLD + " Gem" + ChatColor.DARK_GREEN + " Scroll");
          return false;
        }

        FilterSetting setting;
        try {
          setting = FilterSetting.valueOf("" + args[1]);
        } catch (Exception e) {
          p.sendMessage(ChatColor.RED + "" + args[1] + " is not a valid filter option!");
          return false;
        }

        if (!(data.getLootFilterEntries().contains(setting))) {
          List<FilterSetting> lootFilter = data.getLootFilterEntries();
          lootFilter.add(setting);
          p.sendMessage(ChatColor.GREEN + setting.getName() + " was added to your loot filter!");
        } else {
          p.sendMessage(ChatColor.RED + setting.getName() + " is already in your loot filter!");
        }
        //REMOVE ITEM FILTER SECTION
      } else if (args[0].equalsIgnoreCase("remove")) {
        if (args.length == 1) {
          p.sendMessage(ChatColor.YELLOW + "Options: " + ChatColor.WHITE + "Common" + ChatColor.BLUE + " Uncommon" + ChatColor.DARK_PURPLE + " Rare" + ChatColor.RED + " Epic" + ChatColor.GOLD + " Unique" + ChatColor.BLUE + " Enchantment" + ChatColor.GOLD + " Gem" + ChatColor.DARK_GREEN + " Scroll");
          return false;
        }
        FilterSetting setting;
        try {
          setting = FilterSetting.valueOf("" + args[1]);
        } catch (Exception e) {
          p.sendMessage(ChatColor.RED + "" + args[1] + " is not a valid filter option!");
          return false;
        }

        if (data.getLootFilterEntries().contains(setting)) {
          List<FilterSetting> lootFilter = data.getLootFilterEntries();
          lootFilter.remove(setting);
          p.sendMessage(ChatColor.RED + setting.getName() + " was removed from your loot filter!");
        } else {
          p.sendMessage(ChatColor.RED + setting.getName() + " is not in your loot filter!");
        }
      } else if (args[0].equalsIgnoreCase("list")) {
        p.sendMessage(
            ChatColor.GREEN + "Filtered Items: " + ChatColor.RED + data.getLootFilterEntries().toString().trim());
      } else {
        p.sendMessage(ChatColor.RED + "Invalid Argument!");
      }
    }
    return true;
  }
}
