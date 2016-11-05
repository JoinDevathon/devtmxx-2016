package org.devathon.contest2016.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.devathon.contest2016.DevathonPlugin;

/**
 * @author tmxx
 * @version 1.0
 */
public class SetNameCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    public SetNameCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "droidfactory.setname" ) ) {
            if ( args.length == 0 ) {
                commandSender.sendMessage( this.devathonPlugin.getPrefix() + "§cPlease specify a name" );
            } else {
                String name = args[ 0 ];
                String builder = "Not specified";
                if ( args.length >= 2 ) {
                    builder = args[ 1 ];
                }
                this.devathonPlugin.getMapConfig().setName( name );
                this.devathonPlugin.getMapConfig().setBuilder( builder );
                this.devathonPlugin.getMapConfig().saveConfig();
                commandSender.sendMessage( this.devathonPlugin.getPrefix() + "§aSuccessfully set map name to §e" + name + " by " + builder );
            }
        }
        return true;
    }
}
