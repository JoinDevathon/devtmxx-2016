package org.devathon.contest2016.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityChangeBlockEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class EntityChangeBlockListener implements Listener {
    @EventHandler
    public void onEntityBlockChange( EntityChangeBlockEvent event ) {
        event.setCancelled( true );
    }
}
