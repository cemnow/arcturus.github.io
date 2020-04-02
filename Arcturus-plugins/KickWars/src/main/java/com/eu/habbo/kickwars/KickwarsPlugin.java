package com.eu.habbo.kickwars;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.kickwars.events.EventRegister;
import com.eu.habbo.plugin.HabboPlugin;

/**
 * Created on 12-12-2015 21:21.
 */
public class KickwarsPlugin extends HabboPlugin
{
    public static final String BLUE_M   = "lg-285-81.sh-290-64.ch-215-81.hd-209-1.hr-100-42";
    public static final String GREEN_M  = "lg-285-84.hd-209-1.sh-290-64.hr-100-42.ch-215-84";
    public static final String RED_M    = "hr-100-42.hd-209-1.sh-290-64.lg-285-72.ch-215-72";
    public static final String YELLOW_M = "hd-209-1.lg-285-66.sh-290-64.ch-215-66.hr-100-42";

    public static final String BLUE_F   = "hd-629-1.lg-695-81.sh-725-64.hr-515-42.ch-635-81";
    public static final String GREEN_F  = "hd-629-1.lg-695-84.sh-725-64.hr-515-42.ch-635-84";
    public static final String RED_F    = "hd-629-1.lg-695-72.sh-725-64.hr-515-42.ch-635-72";
    public static final String YELLOW_F = "hd-629-1.lg-695-66.sh-725-64.hr-515-42.ch-635-66";

    public KickwarsPlugin()
    {
        Emulator.getPluginManager().registerEvents(this, new EventRegister());
    }

    @Override
    public void onEnable()
    {
        Emulator.getLogging().logStart("Starting Kickwars Plugin!");
    }

    @Override
    public void onDisable()
    {
        Emulator.getLogging().logStart("Stopping Kickwars Plugin!");
    }

    @Override
    public boolean hasPermission(Habbo habbo, String s)
    {
        if (s.equals("acc_kickwars"))
        {
            return true;

            /**
             * Add a check in here if you want to exclude certain ranks from starting a kickwars battle.
             *
             * EG:
             * return habbo.getHabboInfo().getRank() > 5;
             */
        }

        return false;
    }
}
