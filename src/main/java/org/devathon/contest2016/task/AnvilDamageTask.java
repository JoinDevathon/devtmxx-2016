package org.devathon.contest2016.task;

import org.bukkit.Location;
import org.bukkit.Material;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class AnvilDamageTask implements Runnable {
    @Override
    public void run() {
        for ( GameUser gameUser : GameUser.getPlayingUsers() ) {
            if ( gameUser.isOnline() ) {
                Location location = gameUser.getPlayer().getLocation();
                for ( double x = -0.5; x <= 0.5; x += 0.1 ) {
                    for ( double y = -0.3; y <= 0.3; y += 0.1 ) {
                        for ( double z = -0.5; z <= 0.5; z += 0.1 ) {
                            if ( location.clone().add( x, y, z ).getBlock().getType().equals( Material.ANVIL ) ) {
                                gameUser.getPlayer().damage( 15.0D );
                            }
                        }
                    }
                }
                location = gameUser.getPlayer().getEyeLocation();
                for ( double x = -0.5; x <= 0.5; x += 0.1 ) {
                    for ( double y = -0.3; y <= 0.3; y += 0.1 ) {
                        for ( double z = -0.5; z <= 0.5; z += 0.1 ) {
                            if ( location.clone().add( x, y, z ).getBlock().getType().equals( Material.ANVIL ) ) {
                                gameUser.getPlayer().damage( 15.0D );
                            }
                        }
                    }
                }
            }
        }
    }
}
