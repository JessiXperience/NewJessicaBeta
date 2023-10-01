package jessica.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.player.EntityPlayer;

public class PlayerTickEvent implements Event{
	public enum Phase {
        START, END;
    }
	
	public Phase phase;
	public EntityPlayer player;
	
    public PlayerTickEvent(Phase phase, EntityPlayer player)
    {
    	this.phase = phase;
        this.player = player;
    }
}
