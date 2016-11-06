package org.devathon.contest2016.command;

import net.minecraft.server.v1_10_R1.EnumParticle;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;

/**
 * @author tmxx
 * @version 1.0
 */
public class EffectSpotCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    public EffectSpotCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "droidfactory.effectspot" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;
                if ( args.length < 7 ) {
                    player.sendMessage( "§Use /effectspot <Effect> <VectorX> <VectorY> <VectorZ> <Speed> <Amount> <Interval>" );
                } else {
                    EnumParticle enumParticle = null;
                    double vectorX = -1, vectorY = -1, vectorZ = -1, speed = -1;
                    int amount = -1, interval = -1;
                    try {
                        enumParticle = EnumParticle.valueOf( args[ 0 ] );
                        vectorX = Double.parseDouble( args[ 1 ] );
                        vectorY = Double.parseDouble( args[ 2 ] );
                        vectorZ = Double.parseDouble( args[ 3 ] );
                        speed = Double.parseDouble( args[ 4 ] );
                        amount = Integer.parseInt( args[ 5 ] );
                        interval = Integer.parseInt( args[ 6 ] );
                    } catch ( Exception ignored ) {
                    }
                    if ( enumParticle == null ||
                            vectorX == -1 ||
                            vectorY == -1 ||
                            vectorZ == -1 ||
                            speed == -1 ||
                            amount == -1 ||
                            interval == -1 ) {
                        player.sendMessage( "§Use /effectspot <Effect> <VectorX> <VectorY> <VectorZ> <Speed> <Amount> <Interval>" );
                    } else {

                    }
                }
            } else {
                commandSender.sendMessage( "Only players can execute this command" );
            }
        }
        return true;
    }
}
