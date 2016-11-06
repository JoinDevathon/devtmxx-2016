package org.devathon.contest2016.listener.misc;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.devathon.contest2016.DevathonPlugin;

/**
 * @author tmxx
 * @version 1.0
 */
public class ServerListPingListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public ServerListPingListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onServerListPing( ServerListPingEvent event ) {
        event.setMaxPlayers( this.devathonPlugin.getMainConfig().getMaxPlayers() );
        event.setMotd( "§c§lDroidFactory: §e§l" + this.devathonPlugin.getMapConfig().getName() + "\n" +
                "§c§lCurrent State: §e§l" + ( this.devathonPlugin.getGame().getGameState() == null ?
                    "Unknown" : this.devathonPlugin.getGame().getGameState().getName() ) );
    }
}
