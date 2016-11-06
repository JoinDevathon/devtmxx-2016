package org.devathon.contest2016.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class EntityRegainHealthListener implements Listener {
    @EventHandler
    public void onEntityRegainHealth( EntityRegainHealthEvent event ) {
        event.setCancelled( true );
    }
}
