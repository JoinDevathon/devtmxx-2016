package org.devathon.contest2016;

import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.command.SetIngameCommand;
import org.devathon.contest2016.command.SetLobbyCommand;
import org.devathon.contest2016.command.SetNameCommand;
import org.devathon.contest2016.command.SetSpectatorCommand;
import org.devathon.contest2016.config.MainConfig;
import org.devathon.contest2016.config.MapConfig;

/**
 * The main class of the devathon plugin contest 2016.
 *
 * @author tmxx
 * @version 1.0
 */
public class DevathonPlugin extends JavaPlugin {
    private String prefix = "§7[§cDroidFactory§7] §r";
    private MainConfig mainConfig;
    private MapConfig mapConfig;

    @Override
    public void onEnable() {
        this.mainConfig = new MainConfig( this );
        this.mapConfig = new MapConfig( this );
        if ( this.mainConfig.isSetup() ) {
            this.getLogger().info( "Server started in setup mode" );

            this.getCommand( "setlobby" ).setExecutor( new SetLobbyCommand( this ) );
            this.getCommand( "setspectator" ).setExecutor( new SetSpectatorCommand( this ) );
            this.getCommand( "setingame" ).setExecutor( new SetIngameCommand( this ) );
            this.getCommand( "setname" ).setExecutor( new SetNameCommand( this ) );
        } else {
            this.getLogger().info( "Server started in game mode" );
        }
    }

    @Override
    public void onDisable() {
        this.mainConfig.saveConfig();
        this.mapConfig.saveConfig();
        this.getLogger().info( "Configuration saved" );
    }

    public String getPrefix() {
        return this.prefix;
    }

    public MainConfig getMainConfig() {
        return this.mainConfig;
    }

    public MapConfig getMapConfig() {
        return mapConfig;
    }
}

