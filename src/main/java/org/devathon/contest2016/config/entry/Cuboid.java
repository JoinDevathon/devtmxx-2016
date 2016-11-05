package org.devathon.contest2016.config.entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class Cuboid implements ConfigurationSerializable {
    private String world;
    private Position first;
    private Position second;

    public Cuboid( String world, Position first, Position second ) {
        this.world = world;
        this.first = first;
        this.second = second;
    }

    public Cuboid( Map< String, Object > data ) {
        this.world = ( String ) data.get( "world" );
        this.first = new Position(
                ( Integer ) data.get( "first.x" ),
                ( Integer ) data.get( "first.y" ),
                ( Integer ) data.get( "first.z" )
        );
        this.second = new Position(
                ( Integer ) data.get( "second.x" ),
                ( Integer ) data.get( "second.y" ),
                ( Integer ) data.get( "second.z" )
        );
    }

    @Override
    public Map< String, Object > serialize() {
        Map< String, Object > data = new HashMap<>();
        data.put( "world", this.world );
        data.put( "first.x", this.first.getX() );
        data.put( "first.y", this.first.getY() );
        data.put( "first.z", this.first.getZ() );
        data.put( "second.x", this.second.getX() );
        data.put( "second.y", this.second.getY() );
        data.put( "second.z", this.second.getZ() );
        return data;
    }

    public Location getMaxLocation() {
        return new Location(
                Bukkit.getWorld( this.world ),
                Math.max( this.first.getX(), this.second.getX() ),
                Math.max( this.first.getY(), this.second.getY() ),
                Math.max( this.first.getZ(), this.second.getZ() )
        );
    }

    public Location getMinLocation() {
        return new Location(
                Bukkit.getWorld( this.world ),
                Math.min( this.first.getX(), this.second.getX() ),
                Math.min( this.first.getY(), this.second.getY() ),
                Math.min( this.first.getZ(), this.second.getZ() )
        );
    }

    public void setWorld( String world ) {
        this.world = world;
    }

    public void setFirst( Position first ) {
        this.first = first;
    }

    public void setSecond( Position second ) {
        this.second = second;
    }

    private void loadWorld() {
        if ( Bukkit.getWorld( this.world ) == null ) {
            Bukkit.createWorld( new WorldCreator( this.world ) );
        }
    }

    public static class Position {
        private final int x;
        private final int y;
        private final int z;

        public Position( int x, int y, int z ) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }
    }
}
