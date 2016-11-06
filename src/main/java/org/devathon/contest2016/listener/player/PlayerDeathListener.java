package org.devathon.contest2016.listener.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerDeathListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerDeathListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onPlayerDeath( PlayerDeathEvent event ) {
        event.getDrops().clear();
        event.setDroppedExp( 0 );
        GameUser gameUser = GameUser.getGameUser( event.getEntity().getUniqueId() );
        if ( gameUser != null ) {
            event.setDeathMessage( this.devathonPlugin.getPrefix() + "§a" + event.getEntity().getName() + " §c died" );
        } else {
            event.setDeathMessage( null );
        }
    }
}
