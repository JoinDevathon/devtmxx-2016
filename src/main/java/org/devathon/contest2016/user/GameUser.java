package org.devathon.contest2016.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.module.Module;
import org.devathon.contest2016.scoreboard.IndividualScoreboard;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author tmxx
 * @version 1.0
 */
public class GameUser {
    private static Map< UUID, GameUser > uuidGameUserMap = new HashMap<>();
    private static Map< String, GameUser > nameGameUserMap = new HashMap<>();

    public static void addGameUser( GameUser gameUser ) {
        uuidGameUserMap.put( gameUser.getUuid(), gameUser );
        nameGameUserMap.put( gameUser.getName().toLowerCase(), gameUser );
    }

    public static void removeGameUser( GameUser gameUser ) {
        uuidGameUserMap.remove( gameUser.getUuid() );
        nameGameUserMap.remove( gameUser.getName().toLowerCase() );
    }

    public static GameUser getGameUser( UUID uuid ) {
        return uuidGameUserMap.get( uuid );
    }

    public static GameUser getGameUser( String name ) {
        return nameGameUserMap.get( name.toLowerCase() );
    }

    public static Collection< GameUser > getGameUsers() {
        return uuidGameUserMap.values();
    }

    public static List< GameUser > getPlayingUsers() {
        List< GameUser > gameUsers = new ArrayList<>();

        for ( GameUser gameUser : getGameUsers() ) {
            if ( gameUser.getState().equals( State.PLAYING ) ) {
                gameUsers.add( gameUser );
            }
        }

        return gameUsers;
    }

    public static List< GameUser > getSpectatingUsers() {
        List< GameUser > gameUsers = new ArrayList<>();

        for ( GameUser gameUser : getGameUsers() ) {
            if ( gameUser.getState().equals( State.SPECTATING ) ) {
                gameUsers.add( gameUser );
            }
        }

        return gameUsers;
    }

    private final DevathonPlugin devathonPlugin;
    private final UUID uuid;
    private final String name;

    private IndividualScoreboard individualScoreboard;
    private State state = State.PLAYING;
    private boolean movable = true;
    private Module module = null;

    public GameUser( DevathonPlugin devathonPlugin, UUID uuid, String name ) {
        this.devathonPlugin = devathonPlugin;
        this.uuid = uuid;
        this.name = name;
    }

    public void setupScoreboard() {
        this.individualScoreboard = new IndividualScoreboard( devathonPlugin, this );
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public Player getPlayer() {
        return Bukkit.getPlayer( this.uuid );
    }

    public boolean isOnline() {
        return this.getPlayer() != null;
    }

    public IndividualScoreboard getIndividualScoreboard() {
        return this.individualScoreboard;
    }

    public State getState() {
        return this.state;
    }

    public void setState( State state ) {
        this.state = state;
    }

    public boolean isMovable() {
        return this.movable;
    }

    public void setMovable( boolean movable ) {
        this.movable = movable;
    }

    public Module getModule() {
        return this.module;
    }

    public void setModule( Module module ) {
        this.module = module;
    }

    public int getPercentage() {
        int percentage = 0;

        for ( Module module : this.devathonPlugin.getModuleList() ) {
            percentage += module.getPercent( this.uuid, this.getPlayer().getLocation() );
        }

        return percentage;
    }

    public enum State {
        PLAYING, SPECTATING
    }
}
