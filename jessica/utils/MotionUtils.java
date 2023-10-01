package jessica.utils;

import jessica.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovementInput;

public class MotionUtils {
	public static boolean isMoving(Entity entity) {
        return entity.motionX != 0.0 || entity.motionZ != 0.0 || entity.motionY != 0.0;
    }
	
	public static boolean isWalking() {
        return MovementInput.field_192832_b != 0.0f || Wrapper.player().moveStrafing != 0.0f;
    }
	
	public static void setMotion(float speed) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        float f3 = MovementInput.field_192832_b > 0.0f ? 1.0f : (MovementInput.field_192832_b < 0.0f ? -1.0f : 0.0f);
        float f5 = entityPlayerSP.moveStrafing > 0.0f ? 1.0f : (entityPlayerSP.moveStrafing < 0.0f ? -1.0f : 0.0f);
        float rotationYaw = entityPlayerSP.rotationYaw;
        float f7 = 90.0f * f5;
        float f8 = rotationYaw - (f7 *= f3 != 0.0f ? f3 * 0.5f : 1.0f);
        f8 -= (float)(f3 < 0.0f ? 180 : 0);
        f8 = (float)Math.toRadians(f8);
        float f9 = Wrapper.mc().gameSettings.mouseSensitivity;
        float f10 = f9 * 0.6f + 0.2f;
        float f11 = f10 * f10 * f10 * 1.2f;
        f8 -= f8 % f11;
        double d2 = -Math.sin(f8) * (double)speed;
        double d3 = Math.cos(f8) * (double)speed;
        entityPlayerSP.motionX = d2;
        entityPlayerSP.motionZ = d3;
    }
	
	public static float strafe() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        return (float)Math.sqrt(entityPlayerSP.motionX * entityPlayerSP.motionX + entityPlayerSP.motionZ * entityPlayerSP.motionZ);
    }
	
	public static void setStrafe() {
        MotionUtils.setMotion(MotionUtils.strafe());
    }
	
	public static void setSpeedInAir(float f2) {
		Wrapper.player().speedInAir = f2;
    }
	
	public static double a(boolean bl) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        float f2 = entityPlayerSP.rotationYaw;
        if (MovementInput.field_192832_b < 0.0f) {
            f2 += 180.0f;
        }
        float f3 = 1.0f;
        if (MovementInput.field_192832_b < 0.0f) {
            f3 = -0.5f;
        } else if (MovementInput.field_192832_b > 0.0f) {
            f3 = 0.5f;
        }
        if (entityPlayerSP.moveStrafing > 0.0f) {
            f2 -= 90.0f * f3;
        }
        if (entityPlayerSP.moveStrafing < 0.0f) {
            f2 += 90.0f * f3;
        }
        return bl ? Math.toRadians(f2) : (double)f2;
    }
}
