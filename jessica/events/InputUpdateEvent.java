package jessica.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovementInput;

public class InputUpdateEvent implements Event{
	private final MovementInput movementInput;
	private final EntityPlayer player;
	
	public InputUpdateEvent(EntityPlayer player, MovementInput movementInput)
    {
        this.player = player;
        this.movementInput = movementInput;
    }

    public MovementInput getMovementInput()
    {
        return movementInput;
    }
}
