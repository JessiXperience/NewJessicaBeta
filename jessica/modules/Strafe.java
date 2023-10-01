package jessica.modules;

import jessica.Wrapper;
import jessica.events.InputUpdateEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import jessica.utils.MotionUtils;
import jessica.utils.ReflectionUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.block.BlockAir;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.init.MobEffects;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

public class Strafe extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Matrix", "NCP"});
	ValueBoolean autoJump = new ValueBoolean("AutoJump", true);
	ValueBoolean useTimer = new ValueBoolean("UseTimer", true);
	ValueNumber matrixSpeed = new ValueNumber("Matrix speed", 0.25, 0.2, 0.3, 0.01);

	public Strafe() {
		super("Strafe", Category.MOVEMENT);
		addValue(mode);
		addValue(autoJump);
		addValue(useTimer);
		addValue(matrixSpeed);
	}
	
	@Override
    public void onEnable() {
        if (this.useTimer.getValue()) {
            ReflectionUtils.setTimerSpeedF(1.0866f);
        }
        if (this.mode.getValue().equalsIgnoreCase("NCP")) {
            this.c();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.b();
        super.onDisable();
    }

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        if (this.mode.getValue().equalsIgnoreCase("Default") && object instanceof CPacketPlayer.Rotation && MotionUtils.isWalking()) {
            return false;
        }
        return super.onPacket(object, side);
    }

    @Override
    public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.mode.getValue().equalsIgnoreCase("Default")) {
            if (MotionUtils.isWalking()) {
                if (entityPlayerSP.onGround) {
                    if (this.autoJump.getValue()) {
                        entityPlayerSP.jump();
                    }
                    entityPlayerSP.motionX *= 1.01;
                    entityPlayerSP.motionZ *= 1.01;
                    MotionUtils.setSpeedInAir(0.0223f);
                }
                entityPlayerSP.motionY -= 9.9999E-4;
                MotionUtils.setStrafe();
            } else {
                entityPlayerSP.motionX = 0.0;
                entityPlayerSP.motionZ = 0.0;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("Matrix")) {
            if (entityPlayerSP.isCollidedHorizontally || entityPlayerSP.isSneaking()) {
                return;
            }
            if (entityPlayerSP.onGround) {
                this.d();
            }
            if (MotionUtils.strafe() < 0.22f) {
            	MotionUtils.setMotion(MotionUtils.strafe() * 1.01f);
            }
            if (entityPlayerSP.onGround) {
            	MotionUtils.setMotion(MotionUtils.strafe() * 1.01f);
            }
        } else if (this.mode.getValue().equalsIgnoreCase("NCP")) {
            BlockPos blockPos = new BlockPos(entityPlayerSP.posX, entityPlayerSP.posY, entityPlayerSP.posZ);
            BlockPos blockPos2 = new BlockPos(blockPos.getX(), blockPos.getY() - 1, blockPos.getZ());
            if (Wrapper.world().getBlockState(blockPos2).getBlock() instanceof BlockAir) {
                return;
            }
            if (this.autoJump.getValue()) {
                KeyBinding.setKeyBindState((int)Wrapper.mc().gameSettings.keyBindJump.getKeyCode(), (boolean)false);
                if (entityPlayerSP.onGround) {
                    entityPlayerSP.jump();
                }
            } else if (!Wrapper.mc().gameSettings.keyBindJump.isKeyDown()) {
                this.c();
                return;
            }
            ReflectionUtils.setTimerSpeedF(1.1f);
            if (entityPlayerSP.onGround) {
            	MotionUtils.setStrafe();
            }
            if (entityPlayerSP.motionY == -0.4448259643949201) {
                float f2 = 1.3f;
                float f3 = 1.3f;
                entityPlayerSP.motionX *= (double)f2;
                entityPlayerSP.motionZ *= (double)f3;
            }
        }
        super.onInputUpdate(inputUpdateEvent);
    }

    public void b() {
        if (this.useTimer.getValue()) {
            this.c();
        }
        MotionUtils.setSpeedInAir(0.02f);
    }

    public void c() {
        ReflectionUtils.setTimerSpeedD(1.0);
    }

    public void d() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        entityPlayerSP.motionY = 0.42f;
        if (entityPlayerSP.isPotionActive(MobEffects.JUMP_BOOST)) {
            entityPlayerSP.motionY += (double)((float)(entityPlayerSP.getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1f);
        }
        if (entityPlayerSP.isSprinting()) {
            float f2 = (float)this.matrixSpeed.getDoubleValue();
            float f3 = (float)MotionUtils.a(true);
            entityPlayerSP.motionX -= (double)MathHelper.sin((float)f3) * (double)f2;
            entityPlayerSP.motionZ += (double)MathHelper.cos((float)f3) * (double)f2;
        }
        entityPlayerSP.isAirBorne = true;
    }
}
