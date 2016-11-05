package org.devathon.contest2016.listener.world;

import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class WorldLoadListener implements Listener {
    @EventHandler
    public void onWorldLoad( WorldLoadEvent event ) {
        World world = event.getWorld();
        world.setDifficulty( Difficulty.EASY );
        world.setTime( 1000L );
        world.setGameRuleValue( "doDaylightCycle", "false" );
        world.setStorm( false );
        world.setThunderDuration( 0 );
        world.setThundering( false );
    }
}
