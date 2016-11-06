package org.devathon.contest2016.listener.player;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class AsyncPlayerChatListener implements Listener {
    @EventHandler
    public void onAsyncPlayerChat( AsyncPlayerChatEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        event.setCancelled( true );
        if ( gameUser != null ) {
            String message = ( gameUser.getState().equals( GameUser.State.PLAYING ) ? "§a" : "§7" ) +
                    gameUser.getName() + "§7: §f" + event.getMessage();
            Bukkit.broadcastMessage( message );
        }
    }
}
