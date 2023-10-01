package jessica.modules;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.init.MobEffects;

public class WaterSpeed extends Module{
	
	ValueNumber speed = new ValueNumber("Speed", 0.7, 0.2, 2.0, 0.1);
	ValueBoolean onlySpeedPotion = new ValueBoolean("Only speed potion", false);
	ValueBoolean autoSwim = new ValueBoolean("Auto swim", false);
	
	public WaterSpeed() {
		super("WaterSpeed", Category.MOVEMENT);
		addValue(speed);
		addValue(onlySpeedPotion);
		addValue(autoSwim);
	}
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (this.onlySpeedPotion.getValue() && !Wrapper.player().isPotionActive(MobEffects.SPEED)) {
            return;
        }
        if (Wrapper.player() == null || !Wrapper.player().isInWater() || !MotionUtils.isWalking()) {
            return;
        }
        if (this.autoSwim.getValue()) {
        	Wrapper.player().motionY += (double)0.04f;
        }
        MotionUtils.setMotion((float)this.speed.getDoubleValue());
        super.onPlayerTick(playerTickEvent);
    }
	
}
