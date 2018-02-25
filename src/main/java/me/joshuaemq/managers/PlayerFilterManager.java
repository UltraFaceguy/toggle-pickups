package me.joshuaemq.managers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import me.joshuaemq.data.PlayerFilterData;

public class PlayerFilterManager {

  private Map<UUID, PlayerFilterData> playerFilterMap;

  public PlayerFilterManager() {
    this.playerFilterMap = new HashMap<>();
  }

  public Map<UUID, PlayerFilterData> getPlayerFilterMap() {
    return playerFilterMap;
  }
}
