package org.devathon.contest2016.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.SpawnPoint;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author tmxx
 * @version 1.0
 */
public class MainConfig {
    private final DevathonPlugin devathonPlugin;

    private boolean setup = true;
    private SpawnPoint lobbySpawn = new SpawnPoint( Bukkit.getWorlds().get( 0 ).getSpawnLocation() );
    private int lobbyTime = 60;
    private int endingTime = 15;
    private int maxPlayers = 24;
    private int necessaryPlayers = 3;


    public MainConfig( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
        if ( !this.devathonPlugin.getDataFolder().exists() && !this.devathonPlugin.getDataFolder().mkdir() ) {
            this.devathonPlugin.getLogger().severe( "Could not create data folder" );
        } else {
            File file = this.getFile();
            if ( !file.exists() ) {
                try {
                    if ( !file.createNewFile() ) {
                        this.devathonPlugin.getLogger().severe( "Could not create configuration file" );
                    } else {
                        this.saveConfig();
                    }
                } catch ( IOException e ) {
                    this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not load configuration file", e );
                }
            }
            this.loadConfig();
        }
    }

    public void saveConfig() {
        try {
            File file = this.getFile();
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

            fileConfiguration.set( "setup", this.setup );

            fileConfiguration.set( "spawns.lobby", this.lobbySpawn );

            fileConfiguration.set( "times.lobby", this.lobbyTime );
            fileConfiguration.set( "times.ending", this.endingTime );

            fileConfiguration.set( "players.max", this.maxPlayers );
            fileConfiguration.set( "players.necessary", this.necessaryPlayers );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save configuration file", e );
        }
    }

    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.setup = fileConfiguration.getBoolean( "setup" );

        ConfigurationSection spawnSection = fileConfiguration.getConfigurationSection( "spawns" );
        this.lobbySpawn = ( SpawnPoint ) spawnSection.get( "lobby" );

        ConfigurationSection timeSection = fileConfiguration.getConfigurationSection( "times" );
        this.lobbyTime = timeSection.getInt( "lobby" );
        this.endingTime = timeSection.getInt( "ending" );

        ConfigurationSection playerSection = fileConfiguration.getConfigurationSection( "players" );
        this.maxPlayers = playerSection.getInt( "max" );
        this.necessaryPlayers = playerSection.getInt( "necessary" );

        this.devathonPlugin.getLogger().info( "Loaded configuration file" );
    }

    private File getFile() {
        return new File( this.devathonPlugin.getDataFolder(), "config.yml" );
    }

    public boolean isSetup() {
        return this.setup;
    }

    public SpawnPoint getLobbySpawn() {
        return this.lobbySpawn;
    }

    public int getLobbyTime() {
        return this.lobbyTime;
    }

    public int getEndingTime() {
        return this.endingTime;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public int getNecessaryPlayers() {
        return this.necessaryPlayers;
    }

    public void setLobbySpawn( SpawnPoint lobbySpawn ) {
        this.lobbySpawn = lobbySpawn;
    }
}
