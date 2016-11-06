package org.devathon.contest2016.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.Cuboid;
import org.devathon.contest2016.config.entry.SpawnPoint;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;

/**
 * @author tmxx
 * @version 1.0
 */
public class ModuleConfig {
    private final DevathonPlugin devathonPlugin;
    private final String id;

    private String name = "Unknown";
    private Cuboid cuboid = new Cuboid(
            Bukkit.getWorlds().get( 0 ).getName(),
            new Cuboid.Position( 0, 0, 0 ),
            new Cuboid.Position( 0, 0, 0 )
    );
    private SpawnPoint startPosition = new SpawnPoint( Bukkit.getWorlds().get( 0 ).getSpawnLocation() );
    private SpawnPoint endPosition = new SpawnPoint( Bukkit.getWorlds().get( 0 ).getSpawnLocation() );

    public ModuleConfig( DevathonPlugin devathonPlugin, String id ) {
        this.devathonPlugin = devathonPlugin;
        this.id = id;
        File folder = new File( devathonPlugin.getDataFolder(), "modules" );
        if ( !folder.exists() && !folder.mkdir() ) {
            this.devathonPlugin.getLogger().severe( "Could not create module folder" );
        } else {
            File file = this.getFile();
            if ( !file.exists() ) {
                try {
                    if ( !file.createNewFile() ) {
                        this.devathonPlugin.getLogger().severe( "Could not create module configuration file" );
                    } else {
                        this.saveConfig();
                    }
                } catch ( IOException e ) {
                    this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not load module configuration file", e );
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
            fileConfiguration.set( "cuboid", this.cuboid );
            fileConfiguration.set( "positions.start", this.startPosition );
            fileConfiguration.set( "positions.end", this.endPosition );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved module configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save module configuration file", e );
        }
    }

    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.name = fileConfiguration.getString( "name" );
        this.cuboid = ( Cuboid ) fileConfiguration.get( "cuboid" );

        ConfigurationSection positionSection = fileConfiguration.getConfigurationSection( "positions" );
        this.startPosition = ( SpawnPoint ) positionSection.get( "start" );
        this.endPosition = ( SpawnPoint ) positionSection.get( "end" );

        this.devathonPlugin.getLogger().info( "Loaded module configuration file" );
    }

    private File getFile() {
        return new File( new File( this.devathonPlugin.getDataFolder(), "modules" ), this.id + ".yml" );
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public Cuboid getCuboid() {
        return this.cuboid;
    }

    public void setCuboid( Cuboid cuboid ) {
        this.cuboid = cuboid;
    }

    public SpawnPoint getStartPosition() {
        return this.startPosition;
    }

    public void setStartPosition( SpawnPoint startPosition ) {
        this.startPosition = startPosition;
    }

    public SpawnPoint getEndPosition() {
        return this.endPosition;
    }

    public void setEndPosition( SpawnPoint endPosition ) {
        this.endPosition = endPosition;
    }
}
