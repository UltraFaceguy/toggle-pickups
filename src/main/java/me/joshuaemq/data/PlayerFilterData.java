package me.joshuaemq.data;

import java.util.List;

public class PlayerFilterData {

  private List<FilterSetting> lootFilterEntries;

  public List<FilterSetting> getLootFilterEntries() {
    return lootFilterEntries;
  }

  public void setLootFilterEntries(List<FilterSetting> lootFilterEntries) {
    this.lootFilterEntries = lootFilterEntries;
  }
}
