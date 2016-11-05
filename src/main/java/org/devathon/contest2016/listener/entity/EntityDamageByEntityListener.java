package org.devathon.contest2016.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity( EntityDamageByEntityEvent event ) {
        event.setCancelled( true );
    }
}
