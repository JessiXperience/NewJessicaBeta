package jessica.modules;

import org.apache.commons.lang3.RandomUtils;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovementInput;

public class AntiWeb extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Matrix"});
	
	public AntiWeb() {
		super("AntiWeb", Category.MOVEMENT);
		addValue(mode);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        try {
            Wrapper.player().isInWeb = false;
        }
        catch (Exception exception) {
            this.setToggled(false);
        }
        super.onClientTick(clientTickEvent);
    }
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (!(mode.getValue().equalsIgnoreCase("Matrix"))) {
        	return;
        }
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (!EntityUtils.isInsideBlock(entityPlayerSP, Blocks.WEB) || !MotionUtils.isWalking()) {
        	return;
        }
        this.setKeyDown(false);
        entityPlayerSP.setSprinting(false);
        float forward = MovementInput.field_192832_b > 0.0f ? 1.0f : (MovementInput.field_192832_b < 0.0f ? -1.0f : 0.0f);
        float strafing = MovementInput.moveStrafe > 0.0f ? 1.0f : (MovementInput.moveStrafe < 0.0f ? -1.0f : 0.0f);
        float yaw = entityPlayerSP.rotationYaw;
        float f6 = 90.0f * strafing;
        float f7 = yaw - (f6 *= forward != 0.0f ? forward * 0.5f : 1.0f);
        f7 -= (float)(forward < 0.0f ? 180 : 0);
        f7 = (float)Math.toRadians(f7);
        float f8 = Wrapper.mc().gameSettings.mouseSensitivity;
        float f9 = f8 * 0.6f + 0.2f;
        float f10 = f9 * f9 * f9 * 1.2f;
        f7 -= f7 % f10;
        double d2 = 1.0E-5 - (double)RandomUtils.nextFloat((float)1.0E-5f, (float)5.0E-5f);
        double d3 = -Math.sin(f7) * d2;
        double d4 = Math.cos(f7) * d2;
        entityPlayerSP.motionX = d3;
        entityPlayerSP.motionZ = d4;
        super.onPlayerTick(playerTickEvent);
    }

    public void setKeyDown(boolean state) {
        KeyBinding.setKeyBindState(Wrapper.mc().gameSettings.keyBindJump.getKeyCode(), state);
        KeyBinding.setKeyBindState(Wrapper.mc().gameSettings.keyBindSneak.getKeyCode(), state);
    }
	
}
