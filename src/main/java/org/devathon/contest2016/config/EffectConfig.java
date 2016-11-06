package org.devathon.contest2016.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.EffectSpot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

/**
 * @author tmxx
 * @version 1.0
 */
public class EffectConfig {
    private final DevathonPlugin devathonPlugin;

    private List< EffectSpot > effectSpots = new ArrayList<>();

    public EffectConfig( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
        if ( !this.devathonPlugin.getDataFolder().exists() && !this.devathonPlugin.getDataFolder().mkdir() ) {
            this.devathonPlugin.getLogger().severe( "Could not create data folder" );
        } else {
            File file = this.getFile();
            if ( !file.exists() ) {
                try {
                    if ( !file.createNewFile() ) {
                        this.devathonPlugin.getLogger().severe( "Could not create effect configuration file" );
                    } else {
                        this.saveConfig();
                    }
                } catch ( IOException e ) {
                    this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not load effect configuration file", e );
                }
            }
            this.loadConfig();
        }
    }

    public void saveConfig() {
        try {
            File file = this.getFile();
            FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

            fileConfiguration.set( "effect-spots", this.effectSpots );

            fileConfiguration.save( file );
            this.devathonPlugin.getLogger().info( "Saved effect configuration file" );
        } catch ( IOException e ) {
            this.devathonPlugin.getLogger().log( Level.SEVERE, "Could not save effect configuration file", e );
        }
    }

    @SuppressWarnings( "unchecked" )
    private void loadConfig() {
        File file = this.getFile();
        FileConfiguration fileConfiguration = YamlConfiguration.loadConfiguration( file );

        this.effectSpots = ( List< EffectSpot > ) fileConfiguration.get( "effect-spots" );

        this.devathonPlugin.getLogger().info( "Loaded effect configuration file" );
    }

    private File getFile() {
        return new File( this.devathonPlugin.getDataFolder(), "effects.yml" );
    }

    public List< EffectSpot > getEffectSpots() {
        return this.effectSpots;
    }
}
