package me.joshuaemq.data;

import java.util.List;

public class PlayerFilterData {

  private boolean filterEnabled;
  private List<String> lootFilterEntries;

  public List<String> getLootFilterEntries() {
    return lootFilterEntries;
  }

  public void setLootFilterEntries(List<String> lootFilterEntries) {
    this.lootFilterEntries = lootFilterEntries;
  }

  public boolean isFilterEnabled() {
    return filterEnabled;
  }

  public void setFilterEnabled(boolean filterEnabled) {
    this.filterEnabled = filterEnabled;
  }
}
