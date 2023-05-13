package me.joshuaemq.data;

public enum FilterSetting {

  TOME("Enchantment Tomes", "Enchantment Tome", null, null, null),
  SCROLL("Upgrade Scrolls", "Upgrade Scroll", null, null, null),
  GEM_I("Socket Gems (I)", "Socket Gem", " I", null, null),
  GEM_II("Socket Gems (II)", "Socket Gem", " II", null, null),
  GEM_III("Socket Gems (III)", "Socket Gem", " III", null, null),
  GEM_IV("Socket Gems (IV)", "Socket Gem", " IV", null, null),
  JUNK("Junk Items", null, null, null, null),
  COMMON("Common Equipment", null, null, "\uD86D\uDFE6", null),
  UNCOMMON("Uncommon Equipment", null, null, "\uD86D\uDFE7", null),
  RARE("Rare Equipment", null, null, "\uD86D\uDFE8", null),
  EPIC("Epic Equipment", null, null, "\uD86D\uDFE9", null);

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

  public static final FilterSetting[] VALUES = FilterSetting.values();

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
