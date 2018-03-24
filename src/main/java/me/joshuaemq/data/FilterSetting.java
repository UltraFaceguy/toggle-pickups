package me.joshuaemq.data;

public enum FilterSetting {

  JUNK("Junk", null, null, null, null),
  COMMON("Common", null, null, "Common", null),
  UNCOMMON("Uncommon", null, null, "Uncommon", null),
  RARE("Rare", null, null, "Rare", null),
  EPIC("Epic", null, null, "Epic", null),
  TOME("Enchantment Tome", "Enchantment Tome", null, null, null),
  SCROLL("Upgrade Scroll", "Upgrade Scroll", null, null, null),
  GEM_I("Socket Gem (I)", "Socket Gem", " I", null, null),
  GEM_II("Socket Gem (II)", "Socket Gem", " II", null, null),
  GEM_III("Socket Gem (III)", "Socket Gem", " III", null, null),
  GEM_IV("Socket Gem (IV)", "Socket Gem", " IV", null, null);

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
