package org.devathon.contest2016.command;

import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.config.entry.SoundSpot;

/**
 * @author tmxx
 * @version 1.0
 */
public class SoundSpotCommand implements CommandExecutor {
    private final DevathonPlugin devathonPlugin;

    public SoundSpotCommand( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String commandLabel, String[] args ) {
        if ( commandSender.hasPermission( "droidfactory.soundspot" ) ) {
            if ( commandSender instanceof Player ) {
                Player player = ( Player ) commandSender;
                if ( args.length < 5 ) {
                    player.sendMessage( "§cUse /soundspot <SoundType> <Volume> <Pitch> <Interval> <Module> - To create a sound spot" );
                } else {
                    Sound sound = null;
                    float volume = -1F, pitch = -1F;
                    int interval = -1;
                    String module = args[ 4 ];
                    try {
                        sound = Sound.valueOf( args[ 0 ] );
                        volume = Float.parseFloat( args[ 1 ] );
                        pitch = Float.parseFloat( args[ 2 ] );
                        interval = Integer.parseInt( args[ 3 ] );
                    } catch ( Exception ignored ) {
                    }
                    if ( sound == null ||
                            volume == -1F ||
                            pitch == -1F ||
                            interval == -1 ||
                            module.isEmpty() ) {
                        player.sendMessage( "§cUse /soundspot <SoundType> <Volume> <Pitch> <Interval> <Module> - To create a sound spot" );
                    } else {
                        this.devathonPlugin.getSoundConfig().getSoundSpots().add( new SoundSpot(
                                player.getLocation(),
                                sound,
                                pitch,
                                volume,
                                module,
                                interval
                        ) );
                        this.devathonPlugin.getSoundConfig().saveConfig();
                        player.sendMessage( "§aSuccessfully added sound spot" );
                    }
                }
            } else {
                commandSender.sendMessage( "Only players can execute this command" );
            }
        }
        return true;
    }
}
