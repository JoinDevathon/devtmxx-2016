package org.devathon.contest2016.config.entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class SoundSpot implements ConfigurationSerializable {
    private String world;
    private int x;
    private int y;
    private int z;
    private String soundType;
    private float pitch;
    private float volume;
    private String module;
    private int interval;

    public SoundSpot( Location location, Sound sound, float pitch, float volume, String module, int interval ) {
        this.world = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
        this.soundType = sound.name();
        this.pitch = pitch;
        this.volume = volume;
        this.module = module;
        this.interval = interval;
    }

    public SoundSpot( Map< String, Object > data ) {
        this.world = ( String ) data.get( "world" );
        this.x = ( Integer ) data.get( "x" );
        this.y = ( Integer ) data.get( "y" );
        this.z = ( Integer ) data.get( "z" );
        this.soundType = ( String ) data.get( "sound" );
        this.pitch = ( ( Double ) data.get( "pitch" ) ).floatValue();
        this.volume = ( ( Double ) data.get( "volume" ) ).floatValue();
        this.module = ( String ) data.get( "module" );
        this.interval = ( Integer ) data.get( "interval" );
        this.loadWorld();
    }

    @Override
    public Map< String, Object > serialize() {
        Map< String, Object > data = new HashMap<>();
        data.put( "world", this.world );
        data.put( "x", this.x );
        data.put( "y", this.y );
        data.put( "z", this.z );
        data.put( "sound", this.soundType );
        data.put( "pitch", this.pitch );
        data.put( "volume", this.volume );
        data.put( "module", this.module );
        data.put( "interval", this.interval );
        return data;
    }

    public void playSound() {
        World world = Bukkit.getWorld( this.world );
        world.playSound( new Location( world, this.x, this.y, this.z ), Sound.valueOf( this.soundType ), this.volume, this.pitch );
    }

    public int getInterval() {
        return this.interval;
    }

    private void loadWorld() {
        if ( Bukkit.getWorld( this.world ) == null ) {
            Bukkit.createWorld( new WorldCreator( this.world ) );
        }
    }
}
