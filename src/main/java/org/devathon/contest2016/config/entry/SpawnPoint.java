package org.devathon.contest2016.config.entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class SpawnPoint implements ConfigurationSerializable {
    private String world;
    private double x;
    private double y;
    private double z;
    private float pitch;
    private float yaw;

    public SpawnPoint( Location location ) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.pitch = location.getPitch();
        this.yaw = location.getYaw();
    }

    public SpawnPoint( Map<String, Object> data ) {
        this.world = data.get( "world" ).toString();
        this.x = ( Double ) data.get( "x" );
        this.y = ( Double ) data.get( "y" );
        this.z = ( Double ) data.get( "z" );
        this.pitch = ( Float ) data.get( "pitch" );
        this.yaw = ( Float ) data.get( "yaw" );
        this.loadWorld();
    }

    @Override
    public Map< String, Object > serialize() {
        Map< String, Object > data = new HashMap<>();
        data.put( "world", this.world );
        data.put( "x", this.x );
        data.put( "y", this.y );
        data.put( "z", this.z );
        data.put( "pitch", this.pitch );
        data.put( "yaw", this.yaw );
        return data;
    }

    public World getWorld() {
        return Bukkit.getWorld( this.world );
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public float getPitch() {
        return this.pitch;
    }

    public float getYaw() {
        return this.yaw;
    }

    public Location getLocation() {
        return new Location(
                this.getWorld(),
                this.x,
                this.y,
                this.z,
                this.yaw,
                this.pitch
        );
    }

    private void loadWorld() {
        if ( Bukkit.getWorld( this.world ) == null ) {
            Bukkit.createWorld( new WorldCreator( this.world ) );
        }
    }
}
