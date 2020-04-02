package com.eu.habbo.kickwars.game;

import com.eu.habbo.habbohotel.games.GamePlayer;
import com.eu.habbo.habbohotel.games.GameTeam;
import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.rooms.RoomRightLevels;
import com.eu.habbo.messages.outgoing.rooms.RoomRightsComposer;

/**
 * Created on 12-12-2015 21:22.
 */
public class KickwarsTeam extends GameTeam
{
    public KickwarsTeam(GameTeamColors teamColor)
    {
        super(teamColor);
    }

    /**
     * Removes the client side rights.
     */
    @Override
    public void removeMember(GamePlayer gamePlayer)
    {
        super.removeMember(gamePlayer);

        if(!gamePlayer.getHabbo().getHabboInfo().getCurrentRoom().hasRights(gamePlayer.getHabbo()))
        {
            gamePlayer.getHabbo().getClient().sendResponse(new RoomRightsComposer(RoomRightLevels.NONE));
        }
    }
}
