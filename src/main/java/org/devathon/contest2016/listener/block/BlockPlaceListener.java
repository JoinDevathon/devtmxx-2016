package org.devathon.contest2016.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class BlockPlaceListener implements Listener {
    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event ) {
        event.setCancelled( true );
    }
}
