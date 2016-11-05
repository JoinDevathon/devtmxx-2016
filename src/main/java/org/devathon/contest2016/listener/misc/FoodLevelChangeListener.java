package org.devathon.contest2016.listener.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class FoodLevelChangeListener implements Listener {
    @EventHandler
    public void onFoodLevelChange( FoodLevelChangeEvent event ) {
        event.setFoodLevel( 20 );
    }
}
