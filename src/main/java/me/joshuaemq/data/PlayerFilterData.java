package me.joshuaemq.data;

import java.util.List;

public class PlayerFilterData {

  private boolean filterEnabled;
  private List<FilterSetting> lootFilterEntries;

  public List<FilterSetting> getLootFilterEntries() {
    return lootFilterEntries;
  }

  public void setLootFilterEntries(List<FilterSetting> lootFilterEntries) {
    this.lootFilterEntries = lootFilterEntries;
  }

  public boolean isFilterEnabled() {
    return filterEnabled;
  }

  public void setFilterEnabled(boolean filterEnabled) {
    this.filterEnabled = filterEnabled;
  }
}
