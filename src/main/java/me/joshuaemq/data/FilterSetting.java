package me.joshuaemq.data;

public enum FilterSetting {

  JUNK("Junk Items", null, null, null, null),
  COMMON("Common Equipment", null, null, "Common", null),
  UNCOMMON("Uncommon Equipment", null, null, "Uncommon", null),
  RARE("Rare Equipment", null, null, "Rare", null),
  EPIC("Epic Equipment", null, null, "Epic", null),
  TOME("Enchantment Tomes", "Enchantment Tome", null, null, null),
  SCROLL("Upgrade Scrolls", "Upgrade Scroll", null, null, null),
  GEM_I("Socket Gems (I)", "Socket Gem", " I", null, null),
  GEM_II("Socket Gems (II)", "Socket Gem", " II", null, null),
  GEM_III("Socket Gems (III)", "Socket Gem", " III", null, null),
  GEM_IV("Socket Gems (IV)", "Socket Gem", " IV", null, null);

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
