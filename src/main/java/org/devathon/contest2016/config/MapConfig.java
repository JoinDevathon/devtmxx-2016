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
public class MapConfig {
    private final DevathonPlugin devathonPlugin;

    private String name = "Not specified";
    private String builder = "Not specified";
    private SpawnPoint ingameSpawn = new SpawnPoint( Bukkit.getWorlds().get( 0 ).getSpawnLocation() );
    private SpawnPoint spectatorSpawn = new SpawnPoint( Bukkit.getWorlds().get( 0 ).getSpawnLocation() );

    public MapConfig( DevathonPlugin devathonPlugin ) {
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

            fileConfiguration.set( "name", this.name );
            fileConfiguration.set( "builder", this.builder );

            fileConfiguration.set( "spawns.ingame", this.ingameSpawn );
            fileConfiguration.set( "spawns.spectator", this.spectatorSpawn );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save configuration file", e );
        }
    }

    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.name = fileConfiguration.getString( "name" );
        this.builder = fileConfiguration.getString( "builder" );

        ConfigurationSection spawnSection = fileConfiguration.getConfigurationSection( "spawns" );
        this.ingameSpawn = ( SpawnPoint ) spawnSection.get( "ingame" );
        this.spectatorSpawn = ( SpawnPoint ) spawnSection.get( "spectator" );

        this.devathonPlugin.getLogger().info( "Loaded configuration file" );
    }

    private File getFile() {
        return new File( this.devathonPlugin.getDataFolder(), "map.yml" );
    }

    public String getName() {
        return this.name;
    }

    public String getBuilder() {
        return this.builder;
    }

    public SpawnPoint getIngameSpawn() {
        return this.ingameSpawn;
    }

    public SpawnPoint getSpectatorSpawn() {
        return this.spectatorSpawn;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public void setBuilder( String builder ) {
        this.builder = builder;
    }

    public void setIngameSpawn( SpawnPoint ingameSpawn ) {
        this.ingameSpawn = ingameSpawn;
    }

    public void setSpectatorSpawn( SpawnPoint spectatorSpawn ) {
        this.spectatorSpawn = spectatorSpawn;
    }
}