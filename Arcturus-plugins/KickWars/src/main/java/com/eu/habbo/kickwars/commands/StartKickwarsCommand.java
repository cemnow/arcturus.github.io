package com.eu.habbo.kickwars.commands;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.kickwars.game.KickwarsGame;
/**
 * Created on 12-12-2015 22:03.
 */
public class StartKickwarsCommand extends Command
{
    public StartKickwarsCommand()
    {
        super("acc_kickwars", new String[]{ "startkickwars"});
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        KickwarsGame game = (KickwarsGame) room.getGame(KickwarsGame.class);

        if (game == null)
        {
            game = new KickwarsGame(room);
            room.addGame(game);
        }

        if (!game.isRunning())
        {
            game.initialise();
            game.start();
        }

        return true;
    }
}
