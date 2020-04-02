package com.eu.habbo.kickwars.game;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.games.Game;
import com.eu.habbo.habbohotel.games.GamePlayer;
import com.eu.habbo.habbohotel.games.GameTeam;
import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomRightLevels;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;
import com.eu.habbo.kickwars.KickwarsPlugin;
import com.eu.habbo.messages.ServerMessage;
import com.eu.habbo.messages.outgoing.MessageComposer;
import com.eu.habbo.messages.outgoing.Outgoing;
import com.eu.habbo.messages.outgoing.generic.alerts.GenericAlertComposer;
import com.eu.habbo.messages.outgoing.rooms.RoomRightsComposer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserDataComposer;

import java.util.Map;

/**
 * Created on 12-12-2015 21:22.
 */
public class KickwarsGame extends Game
{
    private boolean running = false;
    private int playerCount = 0;

    private HabboItem exitTile;

    public KickwarsGame(Room room)
    {
        super(KickwarsTeam.class, KickwarsPlayer.class, room);
    }

    @Override
    public void initialise()
    {
        this.exitTile = this.room.getRoomSpecialTypes().getFreezeExitTile();
    }

    @Override
    public void start()
    {
        super.start();

        if(this.running && this.exitTile != null)
            return;

        this.running = true;

        this.giveRights();

        this.updateLooks();

        Emulator.getThreading().run(this, 0);
    }

    @Override
    public synchronized void run()
    {
        int teamCount = 0;

        for(Map.Entry<GameTeamColors, GameTeam> set : this.teams.entrySet())
        {
            if(!set.getValue().getMembers().isEmpty())
            {
                teamCount++;
            }
        }

        if(teamCount > 1)
        {
            Emulator.getThreading().run(this, 1000);
        }
        else
        {
            GameTeam winningTeam = null;

            for (Map.Entry<GameTeamColors, GameTeam> set : this.teams.entrySet())
            {
                if (set.getValue().getMembers().isEmpty())
                    continue;

                if (winningTeam == null || (winningTeam.getTeamScore() < set.getValue().getTeamScore() && set.getValue().getMembers().size() > winningTeam.getMembers().size()))
                {
                    winningTeam = set.getValue();
                }
            }

            if(winningTeam != null && teamCount == 1 && winningTeam.teamColor == GameTeamColors.NONE && winningTeam.getMembers().size() > 1)
            {
                Emulator.getThreading().run(this, 1000);
            }
            else
            {
                if (winningTeam != null)
                {
                    String winners = "";

                    for(GamePlayer player : winningTeam.getMembers())
                    {
                        winners += "\r" + player.getHabbo().getHabboInfo().getUsername() + "\t\t" + player.getScore() + " kicks!";
                    }

                    this.room.sendComposer(new GenericAlertComposer("Team %color% has won this round of kickwars! \r\r<b>Winners:</b>%winners%".replace("%color%", winningTeam.teamColor.name().toLowerCase()).replace("%winners%", winners)).compose());
                }

                this.stop();
            }
        }
    }

    @Override
    public void stop()
    {
        this.running = false;

        super.stop();

        this.takeRights();
    }

    public boolean isRunning()
    {
        return this.running;
    }

    public HabboItem getExitTile()
    {
        return this.exitTile;
    }

    public void giveRights()
    {
        for(Map.Entry<GameTeamColors, GameTeam> set : this.teams.entrySet())
        {
            for(GamePlayer player : set.getValue().getMembers())
            {
                player.getHabbo().getClient().sendResponse(new RoomRightsComposer(RoomRightLevels.OWNER));
            }
        }
    }

    public void updateLooks()
    {
        for(Map.Entry<GameTeamColors, GameTeam> set : this.teams.entrySet())
        {
            for(final GamePlayer player : set.getValue().getMembers())
            {
                String look = "";

                switch(player.getHabbo().getHabboInfo().getGender())
                {
                    case M:

                    switch (player.getTeamColor())
                    {
                        case BLUE:   look = KickwarsPlugin.BLUE_M;   break;
                        case GREEN:  look = KickwarsPlugin.GREEN_M;  break;
                        case RED:    look = KickwarsPlugin.RED_M;    break;
                        case YELLOW: look = KickwarsPlugin.YELLOW_M; break;
                    }
                        break;

                    case F:

                        switch (player.getTeamColor())
                        {
                            case BLUE:   look = KickwarsPlugin.BLUE_F;   break;
                            case GREEN:  look = KickwarsPlugin.GREEN_F;  break;
                            case RED:    look = KickwarsPlugin.RED_F;    break;
                            case YELLOW: look = KickwarsPlugin.YELLOW_F; break;
                        }
                        break;
                }

                final String finalLook = look;
                this.room.sendComposer(new MessageComposer()
                {
                    @Override
                    public ServerMessage compose()
                    {
                        this.response.init(Outgoing.RoomUserDataComposer);
                        this.response.appendInt32(player.getHabbo().getRoomUnit() == null ? -1 : player.getHabbo().getRoomUnit().getId());
                        this.response.appendString(finalLook);
                        this.response.appendString(player.getHabbo().getHabboInfo().getGender() + "");
                        this.response.appendString(player.getHabbo().getHabboInfo().getMotto());
                        this.response.appendInt32(player.getHabbo().getHabboInfo().getAchievementScore());
                        return this.response;
                    }
                }.compose());

            }
        }
    }

    public void takeRights()
    {
        for(Map.Entry<GameTeamColors, GameTeam> set : this.teams.entrySet())
        {
            for(GamePlayer player : set.getValue().getMembers())
            {
                if(!this.room.hasRights(player.getHabbo()))
                {
                    player.getHabbo().getClient().sendResponse(new RoomRightsComposer(RoomRightLevels.NONE));
                }
            }
        }
    }

    @Override
    public boolean addHabbo(Habbo habbo, GameTeamColors teamColor)
    {
        boolean result = super.addHabbo(habbo, teamColor);
        this.playerCount++;

        return result;
    }

    @Override
    public void removeHabbo(Habbo habbo)
    {
        super.removeHabbo(habbo);
        this.playerCount--;
    }

    public boolean teleportToExitTile(Habbo habbo)
    {
        if(this.getExitTile() != null)
        {
            room.teleportHabboToItem(habbo, this.getExitTile());

            room.sendComposer(new RoomUserDataComposer(habbo).compose());

            habbo.getClient().sendResponse(new RoomRightsComposer(RoomRightLevels.NONE));

            return true;
        }

        return false;
    }
}
