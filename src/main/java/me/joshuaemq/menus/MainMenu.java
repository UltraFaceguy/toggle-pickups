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

import io.pixeloutlaw.minecraft.spigot.garbage.StringExtensionsKt;
import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.FilterSetting;
import ninja.amp.ampmenus.menus.ItemMenu;

public class MainMenu extends ItemMenu {

  /*
  00 01 02 03 04 05 06 07 08
  09 10 11 12 13 14 15 16 17
  18 19 20 21 22 23 24 25 26
  27 28 29 30 31 32 33 34 35
  36 37 38 39 40 41 42 43 44
  45 46 47 48 49 50 51 52 53
  */
  public MainMenu(TogglePickupsPlugin plugin) {
    super(StringExtensionsKt.chatColorize("&f砩&0&lFILTER YER DROOPS"), Size.fit(36), plugin);

    setItem(10, new FilterButton(plugin, FilterSetting.COMMON));
    setItem(11, new FilterButton(plugin, FilterSetting.UNCOMMON));
    setItem(12, new FilterButton(plugin, FilterSetting.RARE));
    setItem(13, new FilterButton(plugin, FilterSetting.EPIC));
    setItem(14, new FilterButton(plugin, FilterSetting.SCROLL));
    setItem(15, new FilterButton(plugin, FilterSetting.TOME));
    setItem(16, new FilterButton(plugin, FilterSetting.JUNK));

    setItem(19, new FilterButton(plugin, FilterSetting.GEM_I));
    setItem(20, new FilterButton(plugin, FilterSetting.GEM_II));
    setItem(21, new FilterButton(plugin, FilterSetting.GEM_III));
    setItem(22, new FilterButton(plugin, FilterSetting.GEM_IV));
    //setItem(23, new FilterButton(this, FilterSetting.GEM_I));
    //setItem(24, new FilterButton(this, FilterSetting.GEM_I));
    //setItem(25, new FilterButton(this, FilterSetting.GEM_I));
  }
}
