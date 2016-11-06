package org.devathon.contest2016.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    public static Collection< GameUser > getPlayingUsers() {
        Set< GameUser > gameUsers = new HashSet<>();

        for ( GameUser gameUser : getGameUsers() ) {
            if ( gameUser.getState().equals( State.PLAYING ) ) {
                gameUsers.add( gameUser );
            }
        }

        return gameUsers;
    }

    public static Collection< GameUser > getSpectatingUsers() {
        Set< GameUser > gameUsers = new HashSet<>();

        for ( GameUser gameUser : getGameUsers() ) {
            if ( gameUser.getState().equals( State.SPECTATING ) ) {
                gameUsers.add( gameUser );
            }
        }

        return gameUsers;
    }

    private final UUID uuid;
    private final String name;

    private State state = State.PLAYING;
    private boolean movable = true;

    public GameUser( UUID uuid, String name ) {
        this.uuid = uuid;
        this.name = name;
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

    public enum State {
        PLAYING, SPECTATING
    }
}
