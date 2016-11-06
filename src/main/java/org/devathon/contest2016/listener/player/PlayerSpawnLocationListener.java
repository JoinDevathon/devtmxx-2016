package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;
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
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        if ( gameUser.getState().equals( GameUser.State.PLAYING ) ) {
            event.setSpawnLocation( this.devathonPlugin.getMapConfig().getLobbySpawn().getLocation() );
        } else {
            event.setSpawnLocation( this.devathonPlugin.getMapConfig().getSpectatorSpawn().getLocation() );
        }
    }
}
