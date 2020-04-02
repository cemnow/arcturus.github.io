package com.eu.habbo.kickwars.items;

import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 19-12-2015 13:45.
 */
public class KickwarsGateYellowInteraction extends KickwarsGateInteraction
{
    public KickwarsGateYellowInteraction(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem, GameTeamColors.YELLOW);
    }

    public KickwarsGateYellowInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells, GameTeamColors.YELLOW);
    }
}
