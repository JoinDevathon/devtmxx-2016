package org.devathon.contest2016.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.ModuleConfig;
import org.devathon.contest2016.config.entry.Cuboid;
import org.devathon.contest2016.config.entry.SpawnPoint;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author tmxx
 * @version 1.0
 */
public class ModuleCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    private Map< UUID, Cuboid > uuidCuboidMap = new HashMap<>();

    public ModuleCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "droidfactory.module" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;
                if ( args.length == 0 ) {
                    player.sendMessage( "§cUse the following commands:" );
                    player.sendMessage( " §c/module create <Name> - To create a module" );
                    player.sendMessage( " §c/module pos1 - To set the first position" );
                    player.sendMessage( " §c/module pos2 - To set the second position" );
                    player.sendMessage( " §c/module start <Name> - To set the module start" );
                    player.sendMessage( " §c/module end <Name> - To set the module end" );
                } else {
                    if ( args[ 0 ].equalsIgnoreCase( "create" ) ) {
                        if ( args.length < 2 ) {
                            player.sendMessage( "§cPlease specify a name" );
                        } else {
                            String name = args[ 1 ];
                            if ( this.uuidCuboidMap.containsKey( player.getUniqueId() ) ) {
                                Cuboid cuboid = this.uuidCuboidMap.remove( player.getUniqueId() );
                                ModuleConfig moduleConfig = new ModuleConfig( this.devathonPlugin, name.toLowerCase() );
                                moduleConfig.setName( name );
                                moduleConfig.setCuboid( cuboid );
                                moduleConfig.saveConfig();
                                player.sendMessage( "§aSuccessfully created module §e" + name );
                            } else {
                                player.sendMessage( "§cPlease select a region first" );
                            }
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
                    } else if ( args[ 0 ].equalsIgnoreCase( "start" ) ) {
                        if ( args.length < 2 ) {
                            player.sendMessage( "§cPlease specify a name" );
                        } else {
                            String name = args[ 1 ].toLowerCase();
                            ModuleConfig moduleConfig = new ModuleConfig( this.devathonPlugin, name );
                            moduleConfig.setStartPosition( new SpawnPoint( player.getLocation() ) );
                            moduleConfig.saveConfig();
                            player.sendMessage( "§aStart position set for module " + name );
                        }
                    } else if ( args[ 0 ].equalsIgnoreCase( "end" ) ) {
                        if ( args.length < 2 ) {
                            player.sendMessage( "§cPlease specify a name" );
                        } else {
                            String name = args[ 1 ].toLowerCase();
                            ModuleConfig moduleConfig = new ModuleConfig( this.devathonPlugin, name );
                            moduleConfig.setEndPosition( new SpawnPoint( player.getLocation() ) );
                            moduleConfig.saveConfig();
                            player.sendMessage( "§aEnd position set for module " + name );
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
