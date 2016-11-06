package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerDropItemListener implements Listener {
    @EventHandler
    public void onPlayerDropItem( PlayerDropItemEvent event ) {
        event.setCancelled( true );
    }
}
