package me.joshuaemq.listeners;

import me.joshuaemq.TogglePickupsPlugin;
import me.joshuaemq.data.PlayerFilterData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class PlayerSneakListener implements Listener {


    private TogglePickupsPlugin plugin;

    public PlayerSneakListener(TogglePickupsPlugin plugin) {
        this.plugin = plugin;
    }
    private boolean filterChange = false;

    @EventHandler
    public void onPlayerSneak(PlayerToggleSneakEvent e) {
        PlayerFilterData data = plugin.getPlayerFilterManager().getPlayerFilterMap().get(e.getPlayer().getUniqueId());
        boolean filterEnabled = data.isFilterEnabled();

        if (e.isSneaking() && data.isFilterEnabled()) {
            data.setFilterEnabled(false);
            filterChange = true;
        } else {
            data.setFilterEnabled(filterChange);
        }
    }
}
