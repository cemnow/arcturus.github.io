package com.eu.habbo.kickwars.commands;

import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.kickwars.game.KickwarsGame;

/**
 * Created on 12-12-2015 22:20.
 */
public class StopKickwarsCommand extends Command
{
    public StopKickwarsCommand()
    {
        super("acc_kickwars", new String[]{ "stopkickwars" });
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        KickwarsGame game = (KickwarsGame) gameClient.getHabbo().getHabboInfo().getCurrentRoom().getGame(KickwarsGame.class);

        if(game != null)
        {
            game.stop();
        }

        return true;
    }
}
