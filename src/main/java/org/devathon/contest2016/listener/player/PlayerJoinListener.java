package org.devathon.contest2016.listener.player;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.devathon.contest2016.DevathonPlugin;
import org.devathon.contest2016.game.Game;
import org.devathon.contest2016.user.GameUser;

/**
 * @author tmxx
 * @version 1.0
 */
public class PlayerJoinListener implements Listener {
    private final DevathonPlugin devathonPlugin;

    public PlayerJoinListener( DevathonPlugin devathonPlugin ) {
        this.devathonPlugin = devathonPlugin;
    }

    @EventHandler
    public void onPlayerJoin( PlayerJoinEvent event ) {
        GameUser gameUser = GameUser.getGameUser( event.getPlayer().getUniqueId() );
        gameUser.setupScoreboard();
        if ( this.devathonPlugin.getGame().getGameState().equals( this.devathonPlugin.getGame().getLobbyState() ) ) {
            gameUser.getIndividualScoreboard().setupLobbyObjective();
            for ( GameUser playing : GameUser.getPlayingUsers() ) {
                gameUser.getIndividualScoreboard().setPrefix( playing.getName(), "§a" );
                if ( playing.getIndividualScoreboard() != null ) {
                    playing.getIndividualScoreboard().setPrefix( gameUser.getName(), "§a" );
                }
            }
            for ( GameUser spectator : GameUser.getSpectatingUsers() ) {
                gameUser.getIndividualScoreboard().addGhost( spectator.getName() );
                if ( spectator.getIndividualScoreboard() != null ) {
                    spectator.getIndividualScoreboard().setPrefix( gameUser.getName(), "§a" );
                }
            }
            for ( GameUser user : GameUser.getGameUsers() ) {
                if ( user.getIndividualScoreboard() != null ) {
                    user.getIndividualScoreboard().updateLobby();
                }
            }
            event.setJoinMessage( this.devathonPlugin.getPrefix() + "§a" + gameUser.getName() + " §ejoined the game" );
        } else if ( this.devathonPlugin.getGame().getGameState().equals( this.devathonPlugin.getGame().getIngameState() ) ){
            gameUser.getIndividualScoreboard().setupIngameObjective();
            for ( GameUser playing : GameUser.getPlayingUsers() ) {
                gameUser.getIndividualScoreboard().setPrefix( playing.getName(), "§e" + playing.getPercentage() + "% §a" );
                if ( playing.getIndividualScoreboard() != null ) {
                    playing.getIndividualScoreboard().addGhost( gameUser.getName() );
                }
            }
            for ( GameUser spectator : GameUser.getSpectatingUsers() ) {
                gameUser.getIndividualScoreboard().addGhost( spectator.getName() );
                if ( spectator.getIndividualScoreboard() != null ) {
                    spectator.getIndividualScoreboard().addGhost( gameUser.getName() );
                }
            }
            event.setJoinMessage( null );
        } else {
            for ( GameUser playing : GameUser.getPlayingUsers() ) {
                gameUser.getIndividualScoreboard().setPrefix( playing.getName(), "§a" );
                if ( playing.getIndividualScoreboard() != null ) {
                    playing.getIndividualScoreboard().addGhost( gameUser.getName() );
                }
            }
            for ( GameUser spectating : GameUser.getSpectatingUsers() ) {
                gameUser.getIndividualScoreboard().addGhost( spectating.getName() );
                if ( spectating.getIndividualScoreboard() != null ) {
                    spectating.getIndividualScoreboard().addGhost( gameUser.getName() );
                }
            }
            event.setJoinMessage( null );
        }
        event.getPlayer().getInventory().clear();
        event.getPlayer().getInventory().setArmorContents( new ItemStack[] {
                new ItemStack( Material.AIR ),
                new ItemStack( Material.AIR ),
                new ItemStack( Material.AIR ),
                new ItemStack( Material.AIR )
        } );
        event.getPlayer().setFoodLevel( 20 );
        event.getPlayer().setMaxHealth( 20 );
        event.getPlayer().setHealth( 20 );
        event.getPlayer().setGameMode( GameMode.ADVENTURE );
        event.getPlayer().setExp( 0.0F );
        event.getPlayer().setLevel( 0 );
        for ( PotionEffect potionEffect : event.getPlayer().getActivePotionEffects() ) {
            event.getPlayer().removePotionEffect( potionEffect.getType() );
        }
        if ( gameUser.getState().equals( GameUser.State.SPECTATING ) ) {
            for ( GameUser player : GameUser.getPlayingUsers() ) {
                player.getPlayer().hidePlayer( gameUser.getPlayer() );
                gameUser.getPlayer().showPlayer( player.getPlayer() );
            }
            for ( GameUser spectator : GameUser.getSpectatingUsers() ) {
                spectator.getPlayer().showPlayer( gameUser.getPlayer() );
                gameUser.getPlayer().showPlayer( spectator.getPlayer() );
            }
            gameUser.getPlayer().addPotionEffect( PotionEffectType.INVISIBILITY.createEffect( Integer.MAX_VALUE, 1 ) );
        }
    }
}
