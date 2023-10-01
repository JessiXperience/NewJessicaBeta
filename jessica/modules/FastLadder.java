package jessica.modules;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;

public class FastLadder extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Matrix"});
	
	public FastLadder() {
		super("FastLadder", Category.MOVEMENT);
		addValue(mode);
	}
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (!entityPlayerSP.isOnLadder() || !entityPlayerSP.isCollidedHorizontally) {
            return;
        }
        if (this.mode.getValue().equalsIgnoreCase("Default")) {
            entityPlayerSP.motionY = 0.5;
        } else if (this.mode.getValue().equalsIgnoreCase("Matrix")) {
            entityPlayerSP.noClip = true;
            entityPlayerSP.motionY = 0.0;
            entityPlayerSP.onGround = true;
            entityPlayerSP.motionY = 0.429;
        }
        super.onPlayerTick(playerTickEvent);
    }
	
}
