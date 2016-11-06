package org.devathon.contest2016.listener.entity;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class EntitySpawnListener implements Listener {
    @EventHandler
    public void onEntitySpawn( EntitySpawnEvent event ) {
        if ( event.getEntityType().equals( EntityType.PLAYER ) || event.getEntityType().equals( EntityType.SKELETON ) ) {
            event.setCancelled( false );
        } else {
            event.setCancelled( true );
        }
    }
}
