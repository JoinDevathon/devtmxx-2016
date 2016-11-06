package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerLoginListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerLoginListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onPlayerLogin( PlayerLoginEvent event ) {
        GameUser gameUser = new GameUser( event.getPlayer().getUniqueId(), event.getPlayer().getName() );
        gameUser.setState(
                this.devathonPlugin.getGame().getGameState().equals( this.devathonPlugin.getGame().getLobbyState() ) ?
                        GameUser.State.PLAYING : GameUser.State.SPECTATING
        );
        GameUser.addGameUser( gameUser );
    }
}
