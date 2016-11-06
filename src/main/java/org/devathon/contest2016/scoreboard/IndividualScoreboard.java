package org.devathon.contest2016.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.user.GameUser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author tmxx
 * @version 1.0
 */
public class IndividualScoreboard {
    private static final char[] COLOR_CODES = new char[] {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };

    private final DevathonPlugin devathonPlugin;
    private final GameUser owner;
    private final Scoreboard scoreboard;

    private Objective lobbyObjective = null;
    private Objective ingameObjective = null;

    private Team lobbyOnlinePlayers = null;
    private List< Team > ranking = new ArrayList<>();

    public IndividualScoreboard( DevathonPlugin devathonPlugin, GameUser owner ) {
        this.devathonPlugin = devathonPlugin;
        this.owner = owner;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.owner.getPlayer().setScoreboard( this.scoreboard );
    }

    public void setPrefix( String player, String prefix ) {
        Team team = this.getTeam( player );
        team.addEntry( player );
        team.setPrefix( prefix );
    }

    public void addGhost( String player ) {
        Team team = this.getTeam( "ghosts" );
        team.setPrefix( "§7" );
        team.addEntry( player );
        team.setCanSeeFriendlyInvisibles( true );
    }

    public void setupLobbyObjective() {
        this.lobbyObjective = this.getObjective( "lobby" );
        this.lobbyObjective.setDisplaySlot( DisplaySlot.SIDEBAR );
        this.lobbyObjective.setDisplayName( "§e§lDroidFactory" );

        this.lobbyObjective.getScore( "§2" ).setScore( 3 );
        this.lobbyObjective.getScore( "§eOnline:" ).setScore( 2 );
        this.lobbyObjective.getScore( "§1" ).setScore( 1 );

        this.lobbyOnlinePlayers = this.getTeam( "§1_lobby" );
        this.lobbyOnlinePlayers.addEntry( "§1" );
        this.lobbyOnlinePlayers.setPrefix( "§c" + GameUser.getPlayingUsers().size() );
    }

    public void setupIngameObjective() {
        this.ingameObjective = this.getObjective( "ingame" );
        this.ingameObjective.setDisplaySlot( DisplaySlot.SIDEBAR );
        this.ingameObjective.setDisplayName( "§e§lDroidFactory" );

        this.ingameObjective.getScore( "§1§1" ).setScore( 11 );
        for ( int i = 0; i < 10; i++ ) {
            String identifier = "§" + COLOR_CODES[ i ] + "§a";
            Team team = this.getTeam( identifier );
            team.setPrefix( "§e00% " );
            team.addEntry( identifier );
            team.setSuffix( "----------------" );
            this.ranking.add( team );
            this.ingameObjective.getScore( identifier ).setScore( 10 - i );
        }
    }

    public void updateLobby() {
        if ( this.lobbyOnlinePlayers != null ) {
            this.lobbyOnlinePlayers.setPrefix( "§c" + GameUser.getPlayingUsers().size() );
        }
    }

    public void updateIngame() {
        List< GameUser > gameUsers = GameUser.getPlayingUsers();
        Collections.sort( gameUsers, new Comparator< GameUser >() {
            @Override
            public int compare( GameUser o1, GameUser o2 ) {
                return o2.getPercentage() - o1.getPercentage();
            }
        } );

        for ( int i = 0; i < this.ranking.size(); i++ ) {
            Team team = this.ranking.get( i );
            if ( i < gameUsers.size() ) {
                GameUser gameUser = gameUsers.get( i );
                if ( gameUser != null ) {
                    team.setPrefix( "§e" + ( gameUser.getPercentage() < 10 ? "0" + gameUser.getPercentage() : gameUser.getPercentage() ) + "% " );
                    team.setSuffix( gameUser.getName() );
                } else {
                    team.setPrefix( "§e00% " );
                    team.setSuffix( "----------------" );
                }
            } else {
                team.setPrefix( "§e00% " );
                team.setSuffix( "----------------" );
            }
        }
    }

    public void unregisterObjectives() {
        if ( this.lobbyObjective != null ) {
            this.lobbyObjective.unregister();
            this.lobbyObjective = null;
        }
        if ( this.ingameObjective != null ) {
            this.ingameObjective.unregister();
            this.ingameObjective = null;
        }
    }

    private Team getTeam( String identifier ) {
        return this.scoreboard.getTeam( identifier ) == null ?
                this.scoreboard.registerNewTeam( identifier ) :
                this.scoreboard.getTeam( identifier );
    }

    private Objective getObjective( String identifier ) {
        return this.scoreboard.getObjective( identifier ) == null ?
                this.scoreboard.registerNewObjective( identifier, "dummy" ) :
                this.scoreboard.getObjective( identifier );
    }
}
