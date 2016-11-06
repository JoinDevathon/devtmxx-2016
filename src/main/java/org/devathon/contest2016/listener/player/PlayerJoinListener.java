package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerJoinListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerJoinListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        event.setJoinMessage( null );
    }
}
