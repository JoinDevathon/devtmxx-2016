package org.devathon.contest2016;

import com.google.common.reflect.ClassPath;
import net.minecraft.server.v1_10_R1.EnumDifficulty;
import net.minecraft.server.v1_10_R1.EnumGamemode;
import net.minecraft.server.v1_10_R1.PacketPlayOutRespawn;
import net.minecraft.server.v1_10_R1.WorldType;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.craftbukkit.v1_10_R1.CraftWorld;
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
import org.devathon.contest2016.config.MachineConfig;
import org.devathon.contest2016.config.MainConfig;
import org.devathon.contest2016.config.MapConfig;
import org.devathon.contest2016.config.ModuleConfig;
import org.devathon.contest2016.config.SoundConfig;
import org.devathon.contest2016.game.Game;
import org.devathon.contest2016.listener.block.BlockBreakListener;
import org.devathon.contest2016.listener.block.BlockPhysicsListener;
import org.devathon.contest2016.listener.block.BlockPlaceListener;
import org.devathon.contest2016.listener.entity.EntityChangeBlockListener;
import org.devathon.contest2016.listener.entity.EntityDamageByEntityListener;
import org.devathon.contest2016.listener.entity.EntityDamageListener;
import org.devathon.contest2016.listener.misc.CreatureSpawnListener;
import org.devathon.contest2016.listener.misc.FoodLevelChangeListener;
import org.devathon.contest2016.listener.misc.ServerListPingListener;
import org.devathon.contest2016.listener.player.AsyncPlayerChatListener;
import org.devathon.contest2016.listener.player.PlayerDeathListener;
import org.devathon.contest2016.listener.player.PlayerJoinListener;
import org.devathon.contest2016.listener.player.PlayerLoginListener;
import org.devathon.contest2016.listener.player.PlayerMoveListener;
import org.devathon.contest2016.listener.player.PlayerQuitListener;
import org.devathon.contest2016.listener.player.PlayerRespawnListener;
import org.devathon.contest2016.listener.player.PlayerSpawnLocationListener;
import org.devathon.contest2016.listener.world.ChunkLoadListener;
import org.devathon.contest2016.listener.world.WeatherChangeListener;
import org.devathon.contest2016.listener.world.WorldLoadListener;
import org.devathon.contest2016.module.Module;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private List< Module > moduleList = new ArrayList<>();
    private List< MachineConfig > machineConfigList = new ArrayList<>();
    private int percentPerModule;

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

        try {
            for ( ClassPath.ClassInfo classInfo : ClassPath.from( this.getClassLoader() ).getTopLevelClasses( "org.devathon.contest2016.config.entry" ) ) {
                classInfo.load();
                getLogger().info( "Loaded configuration entry " + classInfo.getName() );
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

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

            pluginManager.registerEvents( new EntityChangeBlockListener(), this );
            pluginManager.registerEvents( new EntityDamageByEntityListener(), this );
            pluginManager.registerEvents( new EntityDamageListener( this ), this );

            pluginManager.registerEvents( new CreatureSpawnListener(), this );
            pluginManager.registerEvents( new FoodLevelChangeListener(), this );
            pluginManager.registerEvents( new ServerListPingListener( this ), this );

            pluginManager.registerEvents( new AsyncPlayerChatListener(), this );
            pluginManager.registerEvents( new PlayerDeathListener( this ), this );
            pluginManager.registerEvents( new PlayerJoinListener( this ), this );
            pluginManager.registerEvents( new PlayerLoginListener( this ), this );
            pluginManager.registerEvents( new PlayerMoveListener(), this );
            pluginManager.registerEvents( new PlayerQuitListener( this ), this );
            pluginManager.registerEvents( new PlayerRespawnListener( this ), this );
            pluginManager.registerEvents( new PlayerSpawnLocationListener( this ), this );

            pluginManager.registerEvents( new ChunkLoadListener(), this );
            pluginManager.registerEvents( new WeatherChangeListener(), this );
            pluginManager.registerEvents( new WorldLoadListener(), this );

            this.mapConfig = new MapConfig( this );
            this.soundConfig = new SoundConfig( this );
            this.effectConfig = new EffectConfig( this );

            File machineFolder = new File( this.getDataFolder(), "machines" );
            if ( !machineFolder.exists() && !machineFolder.mkdir() ) {
                this.getLogger().severe( "Could not create machines folder" );
            } else {
                File[] files = machineFolder.listFiles();
                if ( files != null ) {
                    for ( File file : files ) {
                        if ( file.getAbsolutePath().endsWith( ".yml" ) ) {
                            MachineConfig machineConfig = new MachineConfig( this, file.getName().replace( ".yml", "" ) );
                            this.getLogger().info( "Loaded machine " + machineConfig.getName() );
                            this.machineConfigList.add( machineConfig );
                        }
                    }
                }
            }
            File moduleFolder = new File( this.getDataFolder(), "modules" );
            if ( !moduleFolder.exists() && !moduleFolder.mkdir() ) {
                this.getLogger().severe( "Could not create modules folder" );
            } else {
                File[] files = moduleFolder.listFiles();
                if ( files != null ) {
                    for ( File file : files ) {
                        if ( file.getAbsolutePath().endsWith( ".yml" ) ) {
                            ModuleConfig moduleConfig = new ModuleConfig( this, file.getName().replace( ".yml", "" ) );
                            this.getLogger().info( "Loaded module " + moduleConfig.getName() );
                            this.moduleList.add( new Module( this, moduleConfig ) );
                        }
                    }
                }
            }
            this.percentPerModule = 100 / this.moduleList.size();

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

    public List< MachineConfig > getMachineConfigList() {
        return this.machineConfigList;
    }

    public List< Module > getModuleList() {
        return this.moduleList;
    }

    public int getPercentPerModule() {
        return this.percentPerModule;
    }
}

