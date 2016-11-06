package org.devathon.contest2016.config.entry;

import net.minecraft.server.v1_10_R1.EnumParticle;
import net.minecraft.server.v1_10_R1.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tmxx
 * @version 1.0
 */
public class EffectSpot implements ConfigurationSerializable {
    private String world;
    private double positionX;
    private double positionY;
    private double positionZ;
    private String particle;
    private double vectorX;
    private double vectorY;
    private double vectorZ;
    private float speed;
    private int amount;
    private int interval;

    public EffectSpot( Location location, EnumParticle enumParticle, Vector vector, float speed, int amount, int interval ) {
        this.world = location.getWorld().getName();
        this.positionX = location.getX();
        this.positionY = location.getY();
        this.positionZ = location.getZ();
        this.particle = enumParticle.name();
        this.vectorX = vector.getX();
        this.vectorY = vector.getY();
        this.vectorZ = vector.getZ();
        this.speed = speed;
        this.amount = amount;
        this.interval = interval;
    }

    public EffectSpot( Map< String, Object > data ) {
        this.world = ( String ) data.get( "world" );
        this.positionX = ( Double ) data.get( "position.x" );
        this.positionY = ( Double ) data.get( "position.y" );
        this.positionZ = ( Double ) data.get( "position.z" );
        this.particle = ( String ) data.get( "particle" );
        this.vectorX = ( Double ) data.get( "vector.x" );
        this.vectorY = ( Double ) data.get( "vector.y" );
        this.vectorZ = ( Double ) data.get( "vector.z" );
        this.speed = ( ( Double ) data.get( "speed" ) ).floatValue();
        this.amount = ( Integer ) data.get( "amount" );
        this.interval = ( Integer ) data.get( "interval" );
        this.loadWorld();
    }

    @Override
    public Map< String, Object > serialize() {
        Map< String, Object > data = new HashMap<>();
        data.put( "world", this.world );
        data.put( "position.x", this.positionX );
        data.put( "position.y", this.positionY );
        data.put( "position.z", this.positionZ );
        data.put( "particle", this.particle );
        data.put( "vector.x", this.vectorX );
        data.put( "vector.y", this.vectorY );
        data.put( "vector.z", this.vectorZ );
        data.put( "speed", this.speed );
        data.put( "amount", this.amount );
        data.put( "interval", this.interval );
        return data;
    }

    public void playEffect() {
        PacketPlayOutWorldParticles packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(
                EnumParticle.valueOf( this.particle ),
                true,
                ( float ) this.positionX,
                ( float ) this.positionY,
                ( float ) this.positionZ,
                ( float ) this.vectorX,
                ( float ) this.vectorY,
                ( float ) this.vectorZ,
                this.speed,
                this.amount,
                null
        );
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            ( ( CraftPlayer ) player ).getHandle().playerConnection.sendPacket( packetPlayOutWorldParticles );
        }
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
