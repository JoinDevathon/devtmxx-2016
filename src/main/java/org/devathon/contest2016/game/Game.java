package org.devathon.contest2016.game;

import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.game.state.EndingState;
import org.devathon.contest2016.game.state.GameState;
import org.devathon.contest2016.game.state.IngameState;
import org.devathon.contest2016.game.state.LobbyState;

/**
 * @author tmxx
 * @version 1.0
 */
public class Game {
    private final DevathonPlugin devathonPlugin;
    private final BukkitRunnable bukkitRunnable;
    private final LobbyState lobbyState;
    private final IngameState ingameState;
    private final EndingState endingState;

    private GameState gameState = null;

    public Game( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
        this.bukkitRunnable = new BukkitRunnable() {
            @Override
            public void run() {
                if ( gameState != null ) {
                    if ( gameState.isRunning() ) {
                        gameState.run();
                    }
                }
            }
        };

        this.lobbyState = new LobbyState( this.devathonPlugin, this );
        this.ingameState = new IngameState( this.devathonPlugin, this );
        this.endingState = new EndingState( this.devathonPlugin, this );
    }

    public void start() {
        this.bukkitRunnable.runTaskTimer( this.devathonPlugin, 0L, 20L );
    }

    public void stop() {
        this.bukkitRunnable.cancel();
    }

    public void setGameState( GameState gameState ) {
        if ( this.gameState != null ) {
            this.gameState.end();
        }
        this.gameState = gameState;
        this.gameState.start();
    }

    public GameState getGameState() {
        return this.gameState;
    }

    public LobbyState getLobbyState() {
        return this.lobbyState;
    }

    public IngameState getIngameState() {
        return this.ingameState;
    }

    public EndingState getEndingState() {
        return this.endingState;
    }
}
