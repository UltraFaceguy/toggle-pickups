package me.joshuaemq.data;

public enum FilterSetting {

  COMMON("Common", null, null, "common", null),
  UNCOMMON("Uncommon", null, null, "uncommon", null),
  RARE("Rare", null, null, "rare", null),
  EPIC("Epic", null, null, "epic", null),
  TOME("Enchantment Tome", "enchantment tome", null, null, null),
  SCROLL("Upgrade Scroll", "upgrade scroll", null, null, null),
  GEM_I("Socket Gem (I)", "socket gem", "I", null, null),
  GEM_II("Socket Gem (II)", "socket gem", "II", null, null),
  GEM_III("Socket Gem (III)", "socket gem", "III", null, null),
  GEM_IV("Socket Gem (IV)", "socket gem", "IV", null, null);

  private final String name;
  private final String nameFilter;
  private final String secondaryNameFilter;
  private final String loreFilter;
  private final String secondaryLoreFilter;

  FilterSetting(String name, String nameFilter, String secondaryNameFilter, String loreFilter, String secondaryLoreFilter) {
    this.name = name;
    this.nameFilter = nameFilter;
    this.secondaryNameFilter = secondaryNameFilter;
    this.loreFilter = loreFilter;
    this.secondaryLoreFilter = secondaryLoreFilter;
  }

  public String getName() {
    return name;
  }
  public String getNameFilter() {
    return nameFilter;
  }
  public String getSecondaryNameFilter() {
    return secondaryNameFilter;
  }
  public String getLoreFilter() {
    return loreFilter;
  }
  public String getSecondaryLoreFilter() {
    return secondaryLoreFilter;
  }
}
