package org.devathon.contest2016.config.entry;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.WorldCreator;
import org.bukkit.block.Block;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class BlockState implements ConfigurationSerializable {
    private String world;
    private int x;
    private int y;
    private int z;
    private String material;
    private byte data;

    @SuppressWarnings( "deprecation" )
    public BlockState( Block block ) {
        this.world = block.getWorld().getName();
        this.x = block.getX();
        this.y = block.getY();
        this.z = block.getZ();
        this.material = block.getType().name();
        this.data = block.getData();
    }

    public BlockState( Map< String, Object > data ) {
        this.world = ( String ) data.get( "world" );
        this.x = ( Integer ) data.get( "x" );
        this.y = ( Integer ) data.get( "y" );
        this.z = ( Integer ) data.get( "z" );
        this.material = ( String ) data.get( "material" );
        this.data = ( Byte ) data.get( "data" );
        this.loadWorld();
    }

    @Override
    public Map< String, Object > serialize() {
        Map< String, Object > data =  new HashMap<>();
        data.put( "world", this.world );
        data.put( "x", this.x );
        data.put( "y", this.y );
        data.put( "z", this.z );
        data.put( "material", this.material );
        data.put( "data", this.data );
        return data;
    }

    public Location getLocation() {
        return new Location(
                Bukkit.getWorld( this.world ),
                this.x,
                this.y,
                this.z
        );
    }

    public Material getMaterial() {
        return Material.valueOf( this.material );
    }

    public byte getData() {
        return this.data;
    }

    public void loadWorld() {
        if ( Bukkit.getWorld( this.world ) == null ) {
            Bukkit.createWorld( new WorldCreator( this.world ) );
        }
    }
}
