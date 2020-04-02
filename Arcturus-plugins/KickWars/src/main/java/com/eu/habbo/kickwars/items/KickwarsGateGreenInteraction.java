package com.eu.habbo.kickwars.items;

import com.eu.habbo.habbohotel.games.GameTeamColors;
import com.eu.habbo.habbohotel.items.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created on 19-12-2015 13:45.
 */
public class KickwarsGateGreenInteraction extends KickwarsGateInteraction
{
    public KickwarsGateGreenInteraction(ResultSet set, Item baseItem) throws SQLException
    {
        super(set, baseItem, GameTeamColors.GREEN);
    }

    public KickwarsGateGreenInteraction(int id, int userId, Item item, String extradata, int limitedStack, int limitedSells)
    {
        super(id, userId, item, extradata, limitedStack, limitedSells, GameTeamColors.GREEN);
    }
}
