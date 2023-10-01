package jessica.events;

import com.darkmagician6.eventapi.events.Event;

public class ClientTickEvent implements Event{
	public enum Phase {
        START, END;
    }
	
	public Phase phase;
	
    public ClientTickEvent(Phase phase)
    {
    	this.phase = phase;
    }
}
