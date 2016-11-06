package org.devathon.contest2016.game.state;

import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.game.Game;

/**
 * @author tmxx
 * @version 1.0
 */
public abstract class GameState {
    private final DevathonPlugin devathonPlugin;
    private final Game game;
    private final String name;
    private final int startTime;

    private int time;
    private boolean running = true;

    public GameState( DevathonPlugin devathonPlugin, Game game, String name, int startTime ) {
        this.devathonPlugin = devathonPlugin;
        this.game = game;
        this.name = name;
        this.startTime = startTime;
        this.time = startTime;
    }

    public DevathonPlugin getDevathonPlugin() {
        return this.devathonPlugin;
    }

    public Game getGame() {
        return this.game;
    }

    public String getName() {
        return this.name;
    }

    public int getStartTime() {
        return this.startTime;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime( int time ) {
        this.time = time;
    }

    public boolean isRunning() {
        return this.running;
    }

    public void setRunning( boolean running ) {
        this.running = running;
    }

    public abstract void run();
    public abstract void start();
    public abstract void end();
}
