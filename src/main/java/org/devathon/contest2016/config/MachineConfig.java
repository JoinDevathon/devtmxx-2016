package org.devathon.contest2016.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.BlockState;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author tmxx
 * @version 1.0
 */
public class MachineConfig {
    private final DevathonPlugin devathonPlugin;
    private final String id;

    private String name = "Unknown";
    private int interval = 20;
    private List< List< BlockState > > blockStates = new ArrayList<>();
    private String module = "not_specified";

    public MachineConfig( DevathonPlugin devathonPlugin, String id ) {
        this.devathonPlugin = devathonPlugin;
        this.id = id;
        File folder = new File( this.devathonPlugin.getDataFolder(), "machines" );
        if ( !folder.exists() && !folder.mkdir() ) {
            this.devathonPlugin.getLogger().severe( "Could not create machines folder" );
        } else {
            File file = this.getFile();
            if ( !file.exists() ) {
                try {
                    if ( !file.createNewFile() ) {
                        this.devathonPlugin.getLogger().severe( "Could not create machine configuration file" );
                    } else {
                        this.saveConfig();
                    }
                } catch ( IOException e ) {
                    this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not load machine configuration file", e );
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
            fileConfiguration.set( "interval", this.interval );
            fileConfiguration.set( "block-states", this.blockStates );
            fileConfiguration.set( "module", this.module );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved machine configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save machine configuration file", e );
        }
    }

    @SuppressWarnings( "unchecked" )
    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.name = fileConfiguration.getString( "name" );
        this.interval = fileConfiguration.getInt( "interval" );
        this.blockStates = ( List< List< BlockState > > ) fileConfiguration.get( "block-states" );
        this.module = fileConfiguration.getString( "module" );

        this.devathonPlugin.getLogger().info( "Loaded machine configuration file" );
    }

    private File getFile() {
        return new File( new File( this.devathonPlugin.getDataFolder(), "machines" ), this.id + ".yml" );
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

    public int getInterval() {
        return this.interval;
    }

    public void setInterval( int interval ) {
        this.interval = interval;
    }

    public List< List< BlockState > > getBlockStates() {
        return this.blockStates;
    }

    public String getModule() {
        return this.module;
    }

    public void setModule( String module ) {
        this.module = module;
    }
}
