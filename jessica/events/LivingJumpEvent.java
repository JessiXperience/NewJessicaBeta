package jessica.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.entity.EntityLivingBase;

public class LivingJumpEvent implements Event {
	public static EntityLivingBase entityLiving;
	
	public LivingJumpEvent(EntityLivingBase e) {
		entityLiving = e;
	}
	
}
