/**
 * The MIT License Copyright (c) 2015 Teal Cube Games
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute,
 * sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
 * NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package me.joshuaemq.menus;

import com.tealcube.minecraft.bukkit.facecore.utilities.PaletteUtil;
import com.tealcube.minecraft.bukkit.facecore.utilities.TextUtils;
import io.pixeloutlaw.minecraft.spigot.hilt.ItemStackExtensionsKt;
import java.util.List;
import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import ninja.amp.ampmenus.events.ItemClickEvent;
import ninja.amp.ampmenus.items.MenuItem;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FilterButton extends MenuItem {

  private final TogglePickupsPlugin plugin;
  private final FilterSetting filterSetting;

  private final ItemStack enabledIcon;
  private final ItemStack disabledIcon;

  FilterButton(TogglePickupsPlugin plugin, FilterSetting filterSetting) {
    super("FILTERSETTING", new ItemStack(Material.PAPER));
    this.plugin = plugin;
    this.filterSetting = filterSetting;

    ItemStack enabled = new ItemStack(Material.PAPER);
    ItemStackExtensionsKt.setCustomModelData(enabled, 999);
    ItemStackExtensionsKt.setDisplayName(enabled,
        PaletteUtil.color("|lgreen||b|" + filterSetting.getName()));
    TextUtils.setLore(enabled, PaletteUtil.color(List.of(
        "",
        "|lgray|Picking up this item type is |lgreen||b|ENABLED",
        "",
        "|white||i|Click to |red||i|DISABLE"
    )));
    enabledIcon = enabled;

    ItemStack disabled = new ItemStack(Material.PAPER);
    ItemStackExtensionsKt.setCustomModelData(disabled, 992);
    ItemStackExtensionsKt.setDisplayName(disabled,
        PaletteUtil.color("|red||b|" + filterSetting.getName()));
    TextUtils.setLore(disabled, PaletteUtil.color(List.of(
        "",
        "|lgray|Picking up this item type is |red||b|DISABLED",
        "|gray||i|TIP: Hit sneak to bypass this at any time!",
        "",
        "|white||i|Click to |lgreen||i|ENABLE"
    )));
    disabledIcon = disabled;
  }

  @Override
  public ItemStack getFinalIcon(Player commandSender) {
    if (plugin.getPlayerFilterManager().getPlayerFilterMap().get(commandSender.getUniqueId())
        .getLootFilterEntries().contains(filterSetting)) {
      return disabledIcon;
    } else {
      return enabledIcon;
    }
  }

  @Override
  public void onItemClick(ItemClickEvent event) {
    super.onItemClick(event);
    if (plugin.getPlayerFilterManager().getPlayerFilterMap().get(event.getPlayer().getUniqueId())
        .getLootFilterEntries().contains(filterSetting)) {
      plugin.getPlayerFilterManager().getPlayerFilterMap().get(event.getPlayer().getUniqueId())
          .getLootFilterEntries().remove(filterSetting);
      event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 1.5f);
    } else {
      plugin.getPlayerFilterManager().getPlayerFilterMap().get(event.getPlayer().getUniqueId())
          .getLootFilterEntries().add(filterSetting);
      event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1.0f, 0.75f);
    }
    event.setWillUpdate(true);
    event.setWillClose(false);
  }
}
