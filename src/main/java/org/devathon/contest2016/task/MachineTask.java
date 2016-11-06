package org.devathon.contest2016.task;

import org.bukkit.scheduler.BukkitRunnable;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.MachineConfig;
import org.devathon.contest2016.config.entry.BlockState;

import java.util.List;

/**
 * @author tmxx
 * @version 1.0
 */
public class MachineTask extends BukkitRunnable {
    private final DevathonPlugin devathonPlugin;
    private final MachineConfig machineConfig;

    private int stage = 0;

    public MachineTask( DevathonPlugin devathonPlugin, MachineConfig machineConfig ) {
        this.devathonPlugin = devathonPlugin;
        this.machineConfig = machineConfig;
    }

    public void start() {
        this.runTaskTimer( this.devathonPlugin, 0L, this.machineConfig.getInterval() );
    }

    public void stop() {
        this.cancel();
    }

    @SuppressWarnings( "deprecation" )
    @Override
    public void run() {
        List< List< BlockState > > blockStates = this.machineConfig.getBlockStates();
        if ( !blockStates.isEmpty() ) {
            if ( stage >= blockStates.size() ) {
                stage = 0;
            }
            List< BlockState > stageBlockStates = blockStates.get( stage++ );
            for ( BlockState blockState : stageBlockStates ) {
                blockState.getLocation().getBlock().setType( blockState.getMaterial(), false );
                blockState.getLocation().getBlock().setData( blockState.getData(), false );
            }
        }
    }
}
