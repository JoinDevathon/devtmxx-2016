package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerMoveListener implements Listener {
    @EventHandler
    public void onPlayerMove( PlayerMoveEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        if ( gameUser != null && !gameUser.isMovable() ) {
            if ( event.getFrom().getX() != event.getTo().getX() ||
                    event.getFrom().getY() != event.getTo().getY() ||
                    event.getFrom().getZ() != event.getTo().getX() ) {
                gameUser.getPlayer().teleport( event.getFrom() );
            }
        }
    }
}
