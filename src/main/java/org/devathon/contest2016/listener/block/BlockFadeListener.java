package org.devathon.contest2016.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class BlockFadeListener implements Listener {
    @EventHandler
    public void onBlockFade( BlockFadeEvent event ) {
        event.setCancelled( true );
    }
}
