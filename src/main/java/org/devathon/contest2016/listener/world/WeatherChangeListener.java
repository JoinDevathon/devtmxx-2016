package org.devathon.contest2016.listener.world;

import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class WeatherChangeListener implements Listener {
    @EventHandler
    public void onWeatherChange( WeatherChangeEvent event ) {
        event.setCancelled( true );
        World world = event.getWorld();
        world.setStorm( false );
        world.setThunderDuration( 0 );
        world.setThundering( false );
    }
}
