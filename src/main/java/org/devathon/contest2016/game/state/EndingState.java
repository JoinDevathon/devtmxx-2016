package org.devathon.contest2016.game.state;

import org.bukkit.Bukkit;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.game.Game;

/**
 * @author tmxx
 * @version 1.0
 */
public class EndingState extends GameState {
    public EndingState( DevathonPlugin devathonPlugin, Game game ) {
        super( devathonPlugin, game, "Ending", devathonPlugin.getMainConfig().getEndingTime() );
    }

    @Override
    public void run() {
        if ( this.getTime() == 0 ) {
            Bukkit.broadcastMessage( this.getDevathonPlugin().getPrefix() + "§cServer is restarting now!" );
            Bukkit.shutdown();
            return;
        }
        if ( this.getTime() % 10 == 0 || this.getTime() <= 5 ) {
            Bukkit.broadcastMessage( this.getDevathonPlugin().getPrefix() + "§cServer is restarting in §e" + this.getTime() +
                    " second" + ( this.getTime() == 1 ? "" : "s" ) );
        }
        this.setTime( this.getTime() - 1 );
    }

    @Override
    public void start() {
        Bukkit.broadcastMessage( this.getDevathonPlugin().getPrefix() + "§cServer is restarting in §e" + this.getTime() +
                " second" + ( this.getTime() == 1 ? "" : "s" ) );
    }

    @Override
    public void end() {
    }
}
