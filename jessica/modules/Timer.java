package jessica.modules;

import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ReflectionUtils;
import jessica.value.ValueNumber;

public class Timer extends Module{
	ValueNumber timer = new ValueNumber("Timer", 1.0, 0.1, 10.0, 0.1);
	
	public Timer() {
		super("Timer", Category.WORLD);
		addValue(timer);
	}
	
	@Override
    public void onDisable() {
        ReflectionUtils.setTimerSpeedD(1.0);
        super.onDisable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        ReflectionUtils.setTimerSpeedD(this.timer.getDoubleValue());
        super.onClientTick(clientTickEvent);
    }
	
}
