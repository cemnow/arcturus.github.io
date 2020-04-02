package com.eu.habbo.kickwars.items;

import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.games.battlebanzai.BattleBanzaiGame;
import com.eu.habbo.habbohotel.items.Item;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomUnit;
import com.eu.habbo.kickwars.game.KickwarsGame;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 12-12-2015 21:27.
 */
public class KickwarsGateGreyInteraction extends KickwarsGateInteraction
{
    public KickwarsGateGreyInteraction(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem, GameTeamColors.NONE);
    }

    public KickwarsGateGreyInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells, GameTeamColors.NONE);
    }
}
