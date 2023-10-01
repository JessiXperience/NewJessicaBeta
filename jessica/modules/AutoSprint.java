package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import net.minecraft.init.MobEffects;
import net.minecraft.util.MovementInput;

public class AutoSprint extends Module{

	public AutoSprint() {
		super("AutoSprint", Category.MOVEMENT);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (this.canSprint()) {
            Wrapper.player().setSprinting(MotionUtils.isWalking());
        }
        super.onClientTick(clientTickEvent);
    }

    public boolean canSprint() {
        if (!Wrapper.player().onGround) {
            return false;
        }
        if (Wrapper.player().isSprinting()) {
            return false;
        }
        if (Wrapper.player().isOnLadder()) {
            return false;
        }
        if (Wrapper.player().isInWater()) {
            return false;
        }
        if (Wrapper.player().isInLava()) {
            return false;
        }
        if (Wrapper.player().isHandActive()) {
            return false;
        }
        if (Wrapper.player().isCollidedHorizontally) {
            return false;
        }
        if (MovementInput.field_192832_b < 0.8f) {
            return false;
        }
        if (Wrapper.player().isSneaking()) {
            return false;
        }
        if (Wrapper.player().getFoodStats().getFoodLevel() < 6) {
            return false;
        }
        if (Wrapper.player().isRiding()) {
            return false;
        }
        if(Wrapper.player().isPotionActive(MobEffects.BLINDNESS)) {
        	return false;
        }
        return true;
    }
	
}
