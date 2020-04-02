package com.eu.habbo.threading.runnables;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomTile;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.habbohotel.users.HabboItem;

public class RoomUnitVendingMachineAction implements Runnable
{
    private final Habbo habbo;
    private final HabboItem habboItem;
    private final Room room;

    public RoomUnitVendingMachineAction(Habbo habbo, HabboItem habboItem, Room room)
    {
        this.habbo = habbo;
        this.habboItem = habboItem;
        this.room = room;
    }

    @Override
    public void run()
    {
        if(this.habbo.getHabboInfo().getCurrentRoom() == this.room)
        {
            if(this.habboItem.getRoomId() == this.room.getId())
            {
                RoomTile tile = HabboItem.getSquareInFront(this.room.getLayout(), this.habboItem);
                if (tile != null)
                {
                    if (this.habbo.getRoomUnit().getGoal().equals(tile))
                    {
                        if (this.habbo.getRoomUnit().getCurrentLocation().equals(tile))
                        {
                            try
                            {
                                this.habboItem.onClick(this.habbo.getClient(), this.room, new Object[]{0});
                            }
                            catch (Exception e)
                            {
                                Emulator.getLogging().logErrorLine(e);
                            }
                        }
                        else
                        {
                            if (this.room.getLayout().getTile(tile.x, tile.y).isWalkable())
                            {
                                this.habbo.getRoomUnit().setGoalLocation(tile);
                                Emulator.getThreading().run(this, this.habbo.getRoomUnit().getPath().size() + 2 * 510);
                            }
                        }
                    }
                }
            }
        }
    }
}
