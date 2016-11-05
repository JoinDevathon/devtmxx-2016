package org.devathon.contest2016.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.MachineConfig;
import org.devathon.contest2016.config.entry.BlockState;
import org.devathon.contest2016.config.entry.Cuboid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author tmxx
 * @version 1.0
 */
public class MachineCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    private Map< UUID, Cuboid > uuidCuboidMap = new HashMap<>();

    public MachineCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "droidfactory.machine" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;
                if ( args.length == 0 ) {
                    player.sendMessage( "§cUse the following commands:" );
                    player.sendMessage( " §c/machine create <Name> - To create a machine" );
                    player.sendMessage( " §c/machine pos1 - To set the first position" );
                    player.sendMessage( " §c/machine pos2 - To set the second position" );
                    player.sendMessage( " §c/machine stage <Name> - To save a stage of the machine" );
                    player.sendMessage( " §c/machine interval <Name> <Value> - Set the interval of the machine" );
                    player.sendMessage( " §c/machine module <Name> <Module> - Bind the machine to the module" );
                } else {
                    if ( args[ 0 ].equalsIgnoreCase( "create" ) ) {
                        if ( args.length < 2 ) {
                            player.sendMessage( "§cPlease specify a name" );
                        } else {
                            String name = args[ 1 ];
                            MachineConfig machineConfig = new MachineConfig( this.devathonPlugin, name.toLowerCase() );
                            machineConfig.setName( name );
                            machineConfig.saveConfig();
                            player.sendMessage( "§aSuccessfully created module §e" + name );
                        }
                    } else if ( args[ 0 ].equalsIgnoreCase( "pos1" ) ) {
                        Cuboid cuboid = this.uuidCuboidMap.get( player.getUniqueId() );
                        if ( cuboid == null ) {
                            cuboid = new Cuboid( player.getWorld().getName(), new Cuboid.Position( 0, 0, 0 ), new Cuboid.Position( 0, 0, 0 ) );
                        }
                        cuboid.setFirst( new Cuboid.Position(
                                player.getLocation().getBlockX(),
                                player.getLocation().getBlockY(),
                                player.getLocation().getBlockZ()
                        ) );
                        this.uuidCuboidMap.put( player.getUniqueId(), cuboid );
                        player.sendMessage( "§aFirst position set" );
                    } else if ( args[ 0 ].equalsIgnoreCase( "pos2" ) ) {
                        Cuboid cuboid = this.uuidCuboidMap.get( player.getUniqueId() );
                        if ( cuboid == null ) {
                            cuboid = new Cuboid( player.getWorld().getName(), new Cuboid.Position( 0, 0, 0 ), new Cuboid.Position( 0, 0, 0 ) );
                        }
                        cuboid.setSecond( new Cuboid.Position(
                                player.getLocation().getBlockX(),
                                player.getLocation().getBlockY(),
                                player.getLocation().getBlockZ()
                        ) );
                        this.uuidCuboidMap.put( player.getUniqueId(), cuboid );
                        player.sendMessage( "§aSecond position set" );
                    } else if ( args[ 0 ].equalsIgnoreCase( "stage" ) ) {
                        Cuboid cuboid = uuidCuboidMap.get( player.getUniqueId() );
                        if ( cuboid == null ) {
                            player.sendMessage( "§cPlease select a region first" );
                        } else {
                            if ( args.length < 2 ) {
                                player.sendMessage( "§cPlease specify a name" );
                            } else {
                                String name = args[ 1 ].toLowerCase();
                                List< BlockState > blockStates = new ArrayList<>();
                                for ( int x = cuboid.getMinX(); x < cuboid.getMaxX(); x++ ) {
                                    for ( int y = cuboid.getMinY(); y < cuboid.getMaxY(); y++ ) {
                                        for ( int z = cuboid.getMinZ(); z < cuboid.getMaxZ(); z++ ) {
                                            blockStates.add( new BlockState( player.getWorld().getBlockAt( x, y, z ) ) );
                                        }
                                    }
                                }
                                MachineConfig machineConfig = new MachineConfig( this.devathonPlugin, name );
                                machineConfig.getBlockStates().add( blockStates );
                                machineConfig.saveConfig();
                                player.sendMessage( "§aSuccessfully staged " + blockStates.size() + " blocks for machine " + name );
                            }
                        }
                    } else if ( args[ 0 ].equalsIgnoreCase( "interval" ) ) {
                        if ( args.length < 3 ) {
                            player.sendMessage( "§cPlease specify a machine and an interval in seconds" );
                        } else {
                            String name = args[ 1 ].toLowerCase();
                            int seconds = -1;
                            try {
                                seconds = Integer.parseInt( args[ 2 ] );
                            } catch ( Exception e ) {
                                player.sendMessage( "§cPlease specify a machine and an interval in seconds" );
                            }
                            if ( seconds != -1 ) {
                                MachineConfig machineConfig = new MachineConfig( this.devathonPlugin, name );
                                machineConfig.setInterval( seconds );
                                machineConfig.saveConfig();
                                player.sendMessage( "§aSuccessfully set interval of " + name + " to " + seconds );
                            }
                        }
                    } else if ( args[ 0 ].equalsIgnoreCase( "module" ) ) {
                        if ( args.length < 3 ) {
                            player.sendMessage( "§cPlease specify a machine and a module" );
                        } else {
                            String name = args[ 1 ].toLowerCase();
                            String module = args[ 2 ].toLowerCase();
                            MachineConfig machineConfig = new MachineConfig( this.devathonPlugin, name );
                            machineConfig.setModule( module );
                            machineConfig.saveConfig();
                            player.sendMessage( "§aSuccessfully set module of machine " + name + " to " + module );
                        }
                    } else {
                        player.sendMessage( "§cUnknown command" );
                    }
                }
            } else {
                commandSender.sendMessage( "Only players can execute this command" );
            }
        }
        return true;
    }
}
