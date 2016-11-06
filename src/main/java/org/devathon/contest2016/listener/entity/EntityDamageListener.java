package org.devathon.contest2016.listener.entity;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.devathon.contest2016.DevathonPlugin;

/**
 * @author tmxx
 * @version 1.0
 */
public class EntityDamageListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public EntityDamageListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onEntityDamage( EntityDamageEvent event ) {
        if ( this.devathonPlugin.getGame().getGameState().equals( this.devathonPlugin.getGame().getIngameState() ) ) {
            switch ( event.getCause() ) {
                case CUSTOM:
                case SUFFOCATION:
                case FIRE:
                case FIRE_TICK:
                case HOT_FLOOR:
                case LAVA:
                    event.setDamage( 7.5D );
                    event.setCancelled( false );
                    break;
                default:
                    event.setCancelled( true );
            }
        } else {
            event.setCancelled( true );
        }
    }
}
