package jessica.utils;

import java.lang.reflect.Field;

import jessica.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class ReflectionUtils {
	public static void resetJumpTicks() {
		Wrapper.player().jumpTicks = 0;
    }
	
	public static void resetStepHeight() {
		Wrapper.player().stepHeight = 0.6f;
    }
	
	public static boolean isBlockEdge(EntityLivingBase entityLivingBase) {
        return Wrapper.world().getCollisionBoxes(entityLivingBase, entityLivingBase.getEntityBoundingBox().offset(0.0, -0.5, 0.0).expand(0.001, 0.0, 0.001)).isEmpty() && entityLivingBase.onGround;
    }
	
	public static void setTimerSpeedD(double d) {
		Wrapper.mc().timer.tickLength = (float)(50.0f / d);
    }
	
	public static void setTimerSpeedF(float f) {
		Wrapper.mc().timer.tickLength = 50.0f / f;
    }
}
