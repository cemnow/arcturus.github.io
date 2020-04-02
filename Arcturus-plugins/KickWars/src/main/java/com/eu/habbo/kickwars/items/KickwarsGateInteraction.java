package com.eu.habbo.kickwars.items;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.items.interactions.games.InteractionGameGate;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.kickwars.game.KickwarsGame;
import com.eu.habbo.kickwars.game.KickwarsPlayer;
import com.eu.habbo.messages.outgoing.rooms.users.RoomUserWhisperComposer;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 12-12-2015 21:25.
 */
public class KickwarsGateInteraction extends InteractionGameGate
{
    public KickwarsGateInteraction(ResultSet set, Item baseItem, GameTeamColors teamColor) throws SQLException
    {
        super(set, baseItem, teamColor);
    }

    public KickwarsGateInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells, GameTeamColors teamColor)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells, teamColor);
    }

    @Override
    public boolean canWalkOn(RoomUnit roomUnit, Room room, Object[] objects)
    {
        return room.getGame(KickwarsGame.class) == null || !((KickwarsGame)room.getGame(KickwarsGame.class)).isRunning();
    }

    @Override
    public boolean isWalkable()
    {
        Room room = Emulator.getGameEnvironment().getRoomManager().getRoom(this.getRoomId());
        if(room == null)
            return false;

        return (this.getExtradata().isEmpty() || Integer.valueOf(this.getExtradata()) < 5) && ((room.getGame(KickwarsGame.class))) == null || !((KickwarsGame)(room.getGame(KickwarsGame.class))).isRunning();
    }

    @Override
    public void onWalkOn(RoomUnit roomUnit, Room room, Object[] objects) throws Exception
    {
        super.onWalkOn(roomUnit, room, objects);

        Emulator.getLogging().logDebugLine("User walked on grey gate!");

        Habbo habbo = room.getHabbo(roomUnit);

        if(habbo != null)
        {
            /**
             * Note: Regular games like Freeze, Battlebanzai get instantiated when the timer
             * is triggered. As we don't have a timer we instantiate it here if needed.
             */
            KickwarsGame game = (KickwarsGame) room.getGame(KickwarsGame.class);

            if(game == null)
            {
                game = new KickwarsGame(room);
                room.addGame(game);
            }

            if(!game.isRunning())
            {
                if(!(habbo.getHabboInfo().getGamePlayer() != null && habbo.getHabboInfo().getGamePlayer() instanceof KickwarsPlayer))
                {
                    game.addHabbo(habbo, teamColor);
                }
                else
                {
                    game.removeHabbo(habbo);
                }
            }
        }
    }

    @Override
    public void onWalk(RoomUnit roomUnit, Room room, Object[] objects) throws Exception
    {

    }
}
