package org.devathon.contest2016.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.SoundSpot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author tmxx
 * @version 1.0
 */
public class SoundConfig {
    private final DevathonPlugin devathonPlugin;

    private List< SoundSpot > soundSpots = new ArrayList<>();

    public SoundConfig( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
        if ( !this.devathonPlugin.getDataFolder().exists() && !this.devathonPlugin.getDataFolder().mkdir() ) {
            this.devathonPlugin.getLogger().severe( "Could not create data folder" );
        } else {
            File file = this.getFile();
            if ( !file.exists() ) {
                try {
                    if ( !file.createNewFile() ) {
                        this.devathonPlugin.getLogger().severe( "Could not create sound configuration file" );
                    } else {
                        this.saveConfig();
                    }
                } catch ( IOException e ) {
                    this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not load sound configuration file", e );
                }
            }
            this.loadConfig();
        }
    }

    public void saveConfig() {
        try {
            File file = this.getFile();
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

            fileConfiguration.set( "sound-spots", this.soundSpots );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved sound configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save sound configuration file", e );
        }
    }

    @SuppressWarnings( "unchecked" )
    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.soundSpots = ( List< SoundSpot > ) fileConfiguration.get( "sound-spots" );

        this.devathonPlugin.getLogger().info( "Loaded sound configuration file" );
    }

    private File getFile() {
        return new File( this.devathonPlugin.getDataFolder(), "sounds.yml" );
    }

    public List< SoundSpot > getSoundSpots() {
        return this.soundSpots;
    }
}
