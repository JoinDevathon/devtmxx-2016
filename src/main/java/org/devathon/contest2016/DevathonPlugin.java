package org.devathon.contest2016;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.devathon.contest2016.command.EffectSpotCommand;
import org.devathon.contest2016.command.MachineCommand;
import org.devathon.contest2016.command.ModuleCommand;
import org.devathon.contest2016.command.SetIngameCommand;
import org.devathon.contest2016.command.SetLobbyCommand;
import org.devathon.contest2016.command.SetNameCommand;
import org.devathon.contest2016.command.SetSpectatorCommand;
import org.devathon.contest2016.command.SoundSpotCommand;
import org.devathon.contest2016.config.EffectConfig;
import org.devathon.contest2016.config.MainConfig;
import org.devathon.contest2016.config.MapConfig;
import org.devathon.contest2016.config.SoundConfig;
import org.devathon.contest2016.game.Game;
import org.devathon.contest2016.listener.block.BlockBreakListener;
import org.devathon.contest2016.listener.block.BlockPhysicsListener;
import org.devathon.contest2016.listener.block.BlockPlaceListener;
import org.devathon.contest2016.listener.entity.EntityDamageByEntityListener;
import org.devathon.contest2016.listener.entity.EntityDamageListener;
import org.devathon.contest2016.listener.misc.CreatureSpawnListener;
import org.devathon.contest2016.listener.misc.FoodLevelChangeListener;
import org.devathon.contest2016.listener.player.PlayerMoveListener;
import org.devathon.contest2016.listener.player.PlayerSpawnLocationListener;
import org.devathon.contest2016.listener.world.ChunkLoadListener;
import org.devathon.contest2016.listener.world.WeatherChangeListener;
import org.devathon.contest2016.listener.world.WorldLoadListener;

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
    private SoundConfig soundConfig;
    private EffectConfig effectConfig;
    private Game game;

    @Override
    public void onEnable() {
        this.getLogger().info( "===================================" );
        this.getLogger().info( "|                                 |" );
        this.getLogger().info( "|           DroidFactory          |" );
        this.getLogger().info( "|             by tmxx             |" );
        this.getLogger().info( "|                                 |" );
        this.getLogger().info( "|          Devathon 2016          |" );
        this.getLogger().info( "|                                 |" );
        this.getLogger().info( "===================================" );

        this.mainConfig = new MainConfig( this );
        if ( this.mainConfig.isSetup() ) {
            this.getLogger().info( "Server started in setup mode" );

            this.mapConfig = new MapConfig( this );
            this.soundConfig = new SoundConfig( this );
            this.effectConfig = new EffectConfig( this );

            this.getCommand( "setlobby" ).setExecutor( new SetLobbyCommand( this ) );
            this.getCommand( "setspectator" ).setExecutor( new SetSpectatorCommand( this ) );
            this.getCommand( "setingame" ).setExecutor( new SetIngameCommand( this ) );
            this.getCommand( "setname" ).setExecutor( new SetNameCommand( this ) );
            this.getCommand( "module" ).setExecutor( new ModuleCommand( this ) );
            this.getCommand( "machine" ).setExecutor( new MachineCommand( this ) );
            this.getCommand( "soundspot" ).setExecutor( new SoundSpotCommand( this ) );
            this.getCommand( "effectspot" ).setExecutor( new EffectSpotCommand( this ) );
        } else {
            this.getLogger().info( "Server started in game mode" );

            PluginManager pluginManager = this.getServer().getPluginManager();
            pluginManager.registerEvents( new BlockBreakListener(), this );
            pluginManager.registerEvents( new BlockPhysicsListener(), this );
            pluginManager.registerEvents( new BlockPlaceListener(), this );

            pluginManager.registerEvents( new EntityDamageByEntityListener(), this );
            pluginManager.registerEvents( new EntityDamageListener( this ), this );

            pluginManager.registerEvents( new CreatureSpawnListener(), this );
            pluginManager.registerEvents( new FoodLevelChangeListener(), this );

            pluginManager.registerEvents( new PlayerMoveListener(), this );
            pluginManager.registerEvents( new PlayerSpawnLocationListener( this ), this );

            pluginManager.registerEvents( new ChunkLoadListener(), this );
            pluginManager.registerEvents( new WeatherChangeListener(), this );
            pluginManager.registerEvents( new WorldLoadListener(), this );

            this.mapConfig = new MapConfig( this );
            this.soundConfig = new SoundConfig( this );
            this.effectConfig = new EffectConfig( this );

            this.game = new Game( this );
            this.game.setGameState( this.game.getLobbyState() );
            this.game.start();
        }
    }

    @Override
    public void onDisable() {
        for ( Player player : Bukkit.getOnlinePlayers() ) {
            player.kickPlayer( "§cRestart" );
        }
        this.getLogger().info( "Players kicked" );

        if ( this.game != null ) {
            this.game.stop();
            this.getLogger().info( "Game stopped" );
        }

        this.mainConfig.saveConfig();
        this.mapConfig.saveConfig();
        this.soundConfig.saveConfig();
        this.effectConfig.saveConfig();
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

    public SoundConfig getSoundConfig() {
        return this.soundConfig;
    }

    public Game getGame() {
        return this.game;
    }
}

