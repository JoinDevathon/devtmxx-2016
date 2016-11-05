package org.devathon.contest2016.game.state;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.game.Game;

/**
 * @author tmxx
 * @version 1.0
 */
public class LobbyState extends GameState {
    private BukkitRunnable bukkitRunnable = null;

    public LobbyState( DevathonPlugin devathonPlugin, Game game ) {
        super( devathonPlugin, game, "Lobby", devathonPlugin.getMainConfig().getLobbyTime() );
    }

    @Override
    public void run() {
        if ( Bukkit.getOnlinePlayers().size() < this.getDevathonPlugin().getMainConfig().getNecessaryPlayers() ) {
            this.setTime( this.getStartTime() );
            this.setRunning( false );
            this.bukkitRunnable = new BukkitRunnable() {
                int run = 0;
                @Override
                public void run() {
                    if ( run == 20 ) {
                        int difference = getDevathonPlugin().getMainConfig().getNecessaryPlayers() - Bukkit.getOnlinePlayers().size();
                        Bukkit.broadcastMessage( getDevathonPlugin().getPrefix() + "§cWaiting for " +
                                difference + " more player" + ( difference == 1 ? "" : "s" ) );
                        run = 0;
                    }
                    run++;
                    if ( Bukkit.getOnlinePlayers().size() >= getDevathonPlugin().getMainConfig().getNecessaryPlayers() ) {
                        setRunning( true );
                        cancel();
                    }
                }
            };
        } else {
            if ( this.getTime() == 0 ) {
                return;
            }
            if ( this.getTime() % 10 == 0 || this.getTime() <= 5 ) {
                Bukkit.broadcastMessage( this.getDevathonPlugin().getPrefix() + "§eStarting in §c" + this.getTime() +
                        " second" + ( this.getTime() == 1 ? "" : "s") );
            }
            this.setTime( this.getTime() - 1 );
        }
    }

    @Override
    public void start() {
        Bukkit.broadcastMessage( this.getDevathonPlugin().getPrefix() + "§eLobby time started!" );
    }

    @Override
    public void end() {
        if ( this.bukkitRunnable != null ) {
            this.bukkitRunnable.cancel();
        }
    }
}
