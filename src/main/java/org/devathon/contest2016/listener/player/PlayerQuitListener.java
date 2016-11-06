package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerQuitListener implements Listener {
    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        event.setQuitMessage( null );
    }
}
