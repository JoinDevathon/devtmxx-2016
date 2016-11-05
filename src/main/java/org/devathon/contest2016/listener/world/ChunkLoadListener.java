package org.devathon.contest2016.listener.world;

import org.bukkit.Chunk;
import org.bukkit.block.Biome;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

/**
 * @author tmxx
 * @version 1.0
 */
public class ChunkLoadListener implements Listener {
    @EventHandler
    public void onChunkLoad( ChunkLoadEvent event ) {
        Chunk chunk = event.getChunk();
        event.getWorld().setBiome( chunk.getX(), chunk.getZ(), Biome.HELL );
    }
}
