package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.devathon.contest2016.DevathonPlugin;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerSpawnLocationListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerSpawnLocationListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onSpawnPosition( PlayerSpawnLocationEvent event ) {
        event.setSpawnLocation( this.devathonPlugin.getMainConfig().getLobbySpawn().getLocation() );
    }
}
