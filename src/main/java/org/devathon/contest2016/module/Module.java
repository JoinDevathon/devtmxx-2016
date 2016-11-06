package org.devathon.contest2016.module;

import org.bukkit.Location;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.MachineConfig;
import org.devathon.contest2016.config.ModuleConfig;
import org.devathon.contest2016.task.MachineTask;
import org.devathon.contest2016.user.GameUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author tmxx
 * @version 1.0
 */
public class Module {
    private final DevathonPlugin devathonPlugin;
    private final ModuleConfig moduleConfig;

    private boolean running = false;
    private List< MachineConfig > machineConfigs = new ArrayList<>();
    private List< MachineTask > machineTasks = new ArrayList<>();
    private Map< UUID, Integer > userPercentMap = new HashMap<>();

    public Module( DevathonPlugin devathonPlugin, ModuleConfig moduleConfig ) {
        this.devathonPlugin = devathonPlugin;
        this.moduleConfig = moduleConfig;

        for ( MachineConfig machineConfig : this.devathonPlugin.getMachineConfigList() ) {
            if ( machineConfig.getModule().equals( this.moduleConfig.getId() ) ) {
                this.machineConfigs.add( machineConfig );
            }
        }
    }

    public void check() {
        boolean run = false;
        for ( GameUser gameUser : GameUser.getPlayingUsers() ) {
            if ( gameUser.isOnline() && !run ) {
                run = this.isInBounds( gameUser.getPlayer().getLocation() );
            }
        }

        if ( run ) {
            if ( !running ) {
                running = true;
                for ( MachineConfig machineConfig : this.machineConfigs ) {
                    MachineTask machineTask = new MachineTask( this.devathonPlugin, machineConfig );
                    machineTask.start();
                    this.machineTasks.add( machineTask );
                }
            }
        } else {
            if ( running ) {
                running = false;
                for ( MachineTask machineTask : this.machineTasks ) {
                    machineTask.stop();
                }
                this.machineTasks.clear();
            }
        }
    }

    public boolean isInBounds( Location location ) {
        Location max = this.moduleConfig.getCuboid().getMaxLocation();
        Location min = this.moduleConfig.getCuboid().getMinLocation();
        return max.getBlockX() >= location.getBlockX() &&
                max.getBlockY() >= location.getBlockY() &&
                max.getBlockZ() >= location.getBlockZ() &&
                min.getBlockX() <= location.getBlockX() &&
                min.getBlockY() <= location.getBlockY() &&
                min.getBlockZ() <= location.getBlockZ();
    }

    public ModuleConfig getModuleConfig() {
        return this.moduleConfig;
    }

    public int getPercent( UUID uuid, Location location ) {
        if ( this.isInBounds( location ) ) {
            double fullDistance = this.moduleConfig.getStartPosition().getLocation().distanceSquared( this.moduleConfig.getEndPosition().getLocation() );
            double distance = location.distanceSquared( this.moduleConfig.getEndPosition().getLocation() );
            int percentage = ( int ) ( ( double ) this.devathonPlugin.getPercentPerModule() * ( distance / fullDistance ) );
            this.userPercentMap.put( uuid, percentage );
            return percentage;
        } else {
            return this.userPercentMap.containsKey( uuid ) ? this.userPercentMap.get( uuid ) : 0;
        }
    }
}
