package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ReflectionUtils;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;

public class AutoStep extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "AAC"});
	ValueNumber height = new ValueNumber("Height", 0.5, 0.0, 10.0, 0.5);
	public int ticksCollided = 0;
	
	public AutoStep() {
		super("AutoStep", Category.MOVEMENT);
		addValue(mode);
		addValue(height);
	}
	
	@Override
    public void onEnable() {
        this.ticksCollided = 0;
        super.onEnable();
    }
	
	@Override
    public void onDisable() {
		ReflectionUtils.resetStepHeight();
        super.onDisable();
    }
	
	@Override
	public void onClientTick(ClientTickEvent clientTickEvent) {
		if (mode.getValue().equalsIgnoreCase("AAC")) {
			ReflectionUtils.resetStepHeight();
			EntityPlayerSP entityPlayerSP = Wrapper.player();
            if (entityPlayerSP.isCollidedHorizontally) {
                switch (this.ticksCollided) {
                    case 0: {
                        if (!entityPlayerSP.onGround) break;
                        entityPlayerSP.jump();
                        break;
                    }
                    case 7: {
                        entityPlayerSP.motionY = 0.0;
                        break;
                    }
                    case 8: {
                        if (entityPlayerSP.onGround) break;
                        entityPlayerSP.setPosition(entityPlayerSP.posX, entityPlayerSP.posY + 1.0, entityPlayerSP.posZ);
                    }
                }
                ++this.ticksCollided;
            } else {
                this.ticksCollided = 0;
            }
        } else if (mode.getValue().equalsIgnoreCase("Default")) {
        	Wrapper.player().stepHeight = (float)this.height.getDoubleValue();
        }
        super.onClientTick(clientTickEvent);
    }
	
}
