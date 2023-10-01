package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ReflectionUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class Parkour extends Module{

	public Parkour() {
		super("Parkour", Category.MOVEMENT);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
		if(clientTickEvent.phase == ClientTickEvent.Phase.END) {
			if (ReflectionUtils.isBlockEdge(Wrapper.player()) && !Wrapper.player().isSneaking()) {
				Wrapper.player().jump();
			}
		}
        super.onClientTick(clientTickEvent);
    }
}
