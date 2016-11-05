package org.devathon.contest2016.listener.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class CreatureSpawnListener implements Listener {
    @EventHandler
    public void onEntitySpawn( CreatureSpawnEvent event ) {
        event.setCancelled( !event.getSpawnReason().equals( CreatureSpawnEvent.SpawnReason.CUSTOM ) );
    }
}
