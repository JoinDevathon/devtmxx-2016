package org.devathon.contest2016.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak( BlockBreakEvent event ) {
        event.setCancelled( true );
    }
}
