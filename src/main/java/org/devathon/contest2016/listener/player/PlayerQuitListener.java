package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerQuitListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerQuitListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onPlayerQuit( PlayerQuitEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        if ( !this.devathonPlugin.getGame().getGameState().equals( this.devathonPlugin.getGame().getIngameState() ) ) {
            if ( gameUser.getState().equals( GameUser.State.PLAYING ) ) {
                event.setQuitMessage( this.devathonPlugin.getPrefix() + "§a" + event.getPlayer().getName() + " §cwas eliminated" );
            }
        } else {
            event.setQuitMessage( this.devathonPlugin.getPrefix() + "§a" + event.getPlayer().getName() + " §eleft the game" );
        }
        if ( gameUser != null ) {
            GameUser.removeGameUser( gameUser );
        }
        this.devathonPlugin.getGame().check();
    }
}
