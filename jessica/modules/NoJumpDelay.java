package jessica.modules;

import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ReflectionUtils;

public class NoJumpDelay extends Module{

	public NoJumpDelay() {
		super("NoJumpDelay", Category.MOVEMENT);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        ReflectionUtils.resetJumpTicks();
        super.onClientTick(clientTickEvent);
    }

}
