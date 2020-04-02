package com.eu.habbo.kickwars.events;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.games.Game;
import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.items.ItemInteraction;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomRightLevels;
import com.eu.habbo.kickwars.commands.StartKickwarsCommand;
import com.eu.habbo.kickwars.commands.StopKickwarsCommand;
import com.eu.habbo.kickwars.game.KickwarsGame;
import com.eu.habbo.kickwars.game.KickwarsPlayer;
import com.eu.habbo.kickwars.items.*;
import com.eu.habbo.messages.outgoing.rooms.RoomRightsComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserDataComposer;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadItemsManagerEvent;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.users.UserKickEvent;

/**
 * Created on 12-12-2015 21:23.
 */
public class EventRegister implements EventListener
{
    /**
     * Rules of Free 4 All kickwars (FFA):
     *
     * -> User enters game trough the grey gate.
     * -> User can leave the game untill it starts.
     * -> User cannot leave the game untill it finishes.
     * -> Last player standing wins.
     */

    @EventHandler
    public static void onUserKick(UserKickEvent event)
    {
        if(event.habbo.getHabboInfo().getCurrentRoom() != null)
        {
            Room room = event.habbo.getHabboInfo().getCurrentRoom();

            Game game = room.getGame(KickwarsGame.class);

            if (game != null && game instanceof KickwarsGame)
            {
                if (((KickwarsGame) game).isRunning())
                {
                    KickwarsGame kickwarsGame = (KickwarsGame)game;

                    if (event.habbo.getHabboInfo().getGamePlayer() != null)
                    {
                        if (event.habbo.getHabboInfo().getGamePlayer() instanceof KickwarsPlayer)
                        {
                            KickwarsPlayer player = (KickwarsPlayer) event.habbo.getHabboInfo().getGamePlayer();

                            if (event.target.getHabboInfo().getGamePlayer() != null)
                            {
                                if (event.target.getHabboInfo().getGamePlayer() instanceof KickwarsPlayer)
                                {
                                    KickwarsPlayer targetPlayer = (KickwarsPlayer) event.target.getHabboInfo().getGamePlayer();

                                    if (player.getTeamColor() != targetPlayer.getTeamColor() || player.getTeamColor() == GameTeamColors.NONE)
                                    {
                                        kickwarsGame.removeHabbo(event.target);

                                        player.addScore(1);

                                        game.getTeam(player.getTeamColor()).addTeamScore(1);

                                        if(!((KickwarsGame) game).teleportToExitTile(event.target))
                                        {
                                            room.kickHabbo(event.target, true);
                                        }

                                        event.setCancelled(true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public static void onItemsLoading(EmulatorLoadItemsManagerEvent event)
    {
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("kickwars_gate_grey", KickwarsGateGreyInteraction.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("kickwars_gate_blue", KickwarsGateBlueInteraction.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("kickwars_gate_green", KickwarsGateGreenInteraction.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("kickwars_gate_pink", KickwarsGateRedInteraction.class));
        Emulator.getGameEnvironment().getItemManager().addItemInteraction(new ItemInteraction("kickwars_gate_yllw", KickwarsGateYellowInteraction.class));
    }

    @EventHandler
    public static void systemLoaded(EmulatorLoadedEvent event)
    {
        CommandHandler.addCommand(StartKickwarsCommand.class);
        CommandHandler.addCommand(StopKickwarsCommand.class);
    }
}
