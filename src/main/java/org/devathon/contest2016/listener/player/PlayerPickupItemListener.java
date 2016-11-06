package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerPickupItemListener implements Listener {
    @EventHandler
    public void onPlayerPickupItem( PlayerPickupItemEvent event ) {
        event.setCancelled( true );
    }
}
