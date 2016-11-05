package org.devathon.contest2016.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class BlockPhysicsListener implements Listener {
    @EventHandler
    public void onBlockPhysics( BlockPhysicsEvent event ) {
        event.setCancelled( true );
    }
}
