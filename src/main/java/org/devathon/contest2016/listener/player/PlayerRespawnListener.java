package org.devathon.contest2016.listener.player;

import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerRespawnListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerRespawnListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @SuppressWarnings( "deprecation" )
    @EventHandler
    public void onPlayerRespawn( PlayerRespawnEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        if ( gameUser.getModule() == null ) {
            event.setRespawnLocation( this.devathonPlugin.getMapConfig().getIngameSpawn().getLocation() );
        } else {
            event.setRespawnLocation( gameUser.getModule().getModuleConfig().getStartPosition().getLocation() );
        }
        gameUser.setMovable( false );
        gameUser.getPlayer().sendTitle( "", "§cReady?" );
        Bukkit.getScheduler().runTaskLater( this.devathonPlugin, new Runnable() {
            @Override
            public void run() {
                if ( gameUser.isOnline() ) {
                    gameUser.getPlayer().sendTitle( "§aGo!", "§cReady?" );
                    gameUser.setMovable( true );
                }
            }
        }, 40L );
    }
}
