package org.devathon.contest2016.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.SpawnPoint;

/**
 * @author tmxx
 * @version 1.0
 */
public class SetIngameCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    public SetIngameCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings ) {
        if ( commandSender.hasPermission( "droidfactory.setingame" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;
                this.devathonPlugin.getMapConfig().setIngameSpawn( new SpawnPoint( player.getLocation() ) );
                this.devathonPlugin.getMapConfig().saveConfig();
                player.sendMessage( this.devathonPlugin.getPrefix() + "§aSuccessfully set ingame spawn" );
            } else {
                commandSender.sendMessage( "Cannot execute command as console" );
            }
        }
        return true;
    }
}
