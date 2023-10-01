package jessica.modules;

import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.TimerUtils;

public class Zoom extends Module{
	public static float scale = 1.0f;
	public TimerUtils tick = new TimerUtils();
	
	public Zoom() {
		super("Zoom", Category.RENDER);
	}

	@Override
    public void onDisable() {
		scale = 1.0f;
        super.onDisable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (this.tick.check(15L)) {
            if (scale < 4.0f) {
            	scale += 0.5f;
            }
            this.tick.reset();
        }
        super.onClientTick(clientTickEvent);
    }
}
