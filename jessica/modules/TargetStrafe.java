package jessica.modules;

import java.awt.Color;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.RandomUtils;
import jessica.utils.RenderUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;

public class TargetStrafe extends Module {
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Spiral", "Static", "Reverse"});
	ValueMode priority = new ValueMode("Priority", "Closest", new String[] {"Closest", "Health"});
	ValueBoolean renderCircle = new ValueBoolean("Render circle", true);
	ValueBoolean thirdView = new ValueBoolean("Third view", true);
	ValueBoolean autoJump = new ValueBoolean("Auto jump", true);
	ValueNumber distanceToEntity = new ValueNumber("Distance to entity", 8.5, 6.0, 20.0, 0.1);
	ValueNumber speed = new ValueNumber("Speed", 0.23, 0.1, 1.0, 0.01);
	ValueNumber spiralSpeed = new ValueNumber("Spiral speed", 0.28, 0.1, 1.0, 0.01);
	ValueBoolean damageBoost = new ValueBoolean("Damage boost", false);
	ValueNumber boostSpeed = new ValueNumber("Boost speed", 0.5, 0.1, 1.0, 0.01);
	ValueNumber strafeRadius = new ValueNumber("Strafe radius", 2.5, 0.5, 6.0, 0.1);
	public static EntityLivingBase target;
    public TimerUtils m = new TimerUtils();
    public TimerUtils n = new TimerUtils();
    public int o;
    public double p;
	
	public TargetStrafe() {
		super("TargetStrafe", Category.COMBAT);
		addValue(mode);
		addValue(priority);
		addValue(renderCircle);
		addValue(thirdView);
		addValue(autoJump);
		addValue(distanceToEntity);
		addValue(speed);
		addValue(spiralSpeed);
		addValue(damageBoost);
		addValue(boostSpeed);
		addValue(strafeRadius);
	}
	
	@Override
    public void onEnable() {
		target = null;
        this.o = 1;
        this.p = 0.0;
        super.onEnable();
    }

    @Override
    public void onDisable() {
    	target = null;
        if (this.thirdView.getValue()) {
            Wrapper.mc().gameSettings.thirdPersonView = 0;
        }
        super.onDisable();
    }

    @Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        this.getTarget();
        if (!this.renderCircle.getValue()) {
            return;
        }
        if (target != null) {
            RenderUtils.drawCircle(target, this.p, 40, 1.0f, new Color(200, 200, 200, 255).getRGB(), renderWorldLastEvent.partialTicks);
        }
        if (this.p < this.strafeRadius.getDoubleValue() && this.n.check(1L)) {
            this.p += 0.1;
            this.n.reset();
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (target == null || !this.d()) {
            return;
        }
        if (this.thirdView.getValue()) {
            Wrapper.mc().gameSettings.thirdPersonView = 1;
        }
        if (this.autoJump.getValue() && entityPlayerSP.onGround) {
            entityPlayerSP.jump();
        }
        if (Wrapper.mc().gameSettings.keyBindLeft.isKeyDown()) {
            this.o = -1;
        } else if (Wrapper.mc().gameSettings.keyBindRight.isKeyDown()) {
            this.o = 1;
        }
        if (this.m.check(10L) && Wrapper.player().isCollidedHorizontally) {
            this.o = this.o == 1 ? -1 : 1;
            this.m.reset();
        }
        if (this.b() && target != null) {
            double d2;
            float f2 = aim2((Entity)target, AimParts.b, 0.0f)[0];
            boolean bl = entityPlayerSP.getDistanceToEntity((Entity)target) > this.e();
            double d3 = d2 = bl ? 1.0 : 0.0;
            if (this.mode.getValue().equalsIgnoreCase("Static")) {
                double d4 = d2 = bl ? 0.0 : -1.0;
            }
            if (this.mode.getValue().equalsIgnoreCase("Reverse")) {
                d2 = -1.0;
            }
            this.a(f2, this.g(), d2, this.o);
        } else if (this.mode.getValue().equalsIgnoreCase("Spiral")) {
            this.a((Entity)target, this.g(), this.spiralSpeed.getDoubleValue(), this.o);
        }
        if (!this.isValid(target)) {
        	target = null;
        }
        super.onPlayerTick(playerTickEvent);
    }

    public boolean b() {
        return this.mode.getValue().equalsIgnoreCase("Default") || this.mode.getValue().equalsIgnoreCase("Static") || this.mode.getValue().equalsIgnoreCase("Reverse");
    }

    private /* synthetic */ double g() {
        return this.damageBoost.getValue() && Wrapper.player().hurtTime > 5 && (double)Wrapper.player().fallDistance > 0.2 ? this.boostSpeed.getDoubleValue() : this.speed.getDoubleValue();
    }

    private /* synthetic */ void a(Entity entity, double d2, double d3, int n2) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        double d4 = (entityPlayerSP.posX - entity.posX) / Math.sqrt(Math.pow(entityPlayerSP.posX - entity.posX, 2.0) + Math.pow(entityPlayerSP.posZ - entity.posZ, 2.0));
        double d5 = (entityPlayerSP.posZ - entity.posZ) / Math.sqrt(Math.pow(entityPlayerSP.posX - entity.posX, 2.0) + Math.pow(entityPlayerSP.posZ - entity.posZ, 2.0));
        double d6 = d2 * d5 * (double)n2 - d3 * d2 * d4;
        double d7 = -d2 * d4 * (double)n2 - d3 * d2 * d5;
        if (NoSlow.isUseItem()) {
            d6 /= 1.2;
            d7 /= 1.2;
        }
        entityPlayerSP.setVelocity(d6, entityPlayerSP.motionY, d7);
    }

    private /* synthetic */ void a(float f2, double d2, double forward, int n2) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (forward != 0.0) {
            if (n2 < 0) {
                f2 += (float)(forward > 0.0 ? -50 : 50);
            } else if (n2 > 0) {
                f2 += (float)(forward > 0.0 ? 50 : -50);
            }
            n2 = 0;
            if (forward > 0.0) {
            	forward = 1.0;
            } else if (forward < 0.0) {
            	forward = -1.0;
            }
        }
        double d4 = forward * d2 * Math.cos(Math.toRadians(f2 + 90.0f)) - (double)n2 * d2 * Math.sin(Math.toRadians(f2 + 90.0f));
        double d5 = forward * d2 * Math.sin(Math.toRadians(f2 + 90.0f)) + (double)n2 * d2 * Math.cos(Math.toRadians(f2 + 90.0f));
        if (NoSlow.isUseItem()) {
            d4 /= 1.2;
            d5 /= 1.2;
        }
        entityPlayerSP.setVelocity(d4, entityPlayerSP.motionY - 0.001, d5);
    }

    public void getTarget() {
        if (KillAura.target != null && this.isValid(target)) {
        	target = KillAura.target;
            return;
        }
        for (Object e : Wrapper.world().getLoadedEntityList()) {
            EntityLivingBase entityLivingBase;
            if (!(e instanceof EntityLivingBase) || !this.isValid(entityLivingBase = (EntityLivingBase)e)) continue;
            target = entityLivingBase;
        }
    }

    public boolean d() {
        return !Wrapper.player().isOnLadder() && !Wrapper.player().isInWater() && !Wrapper.player().isInLava();
    }

    public float e() {
        return (float)this.strafeRadius.getDoubleValue();
    }

    public boolean getPriority(EntityLivingBase entityLivingBase) {
        return this.priority.getValue().equalsIgnoreCase("Closest") && EntityUtils.isClosest(entityLivingBase, target) || this.priority.getValue().equalsIgnoreCase("Health") && EntityUtils.isLowHealth(entityLivingBase, target);
    }

    public boolean getDistanceToEntity(EntityLivingBase entityLivingBase) {
        return entityLivingBase.getDistanceToEntity(Wrapper.player()) <= this.distanceToEntity.getDoubleValue();
    }

    public boolean isValid(EntityLivingBase entityLivingBase) {
        return !(entityLivingBase instanceof EntityPlayerSP) && !(entityLivingBase instanceof EntityArmorStand) && EntityUtils.isValidEntity(entityLivingBase) && !(Wrapper.mc().currentScreen instanceof GuiScreen) && entityLivingBase != Wrapper.player() && !entityLivingBase.isDead && entityLivingBase.deathTime <= 0 && !FriendManager.isFriend(entityLivingBase.getName()) && this.getDistanceToEntity(entityLivingBase)/* && eQ.b(entityLivingBase)*/ && this.getPriority(entityLivingBase);
    }
    
    enum AimParts {
    	a,
    	b,
    	c;
    }
    
    public static float[] aim2(Entity entity, AimParts cy_02, float f2) {
        double d2;
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        double d3 = entity.posX - entityPlayerSP.posX;
        double d4 = entity.posZ - entityPlayerSP.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            float f3 = RandomUtils.randomFloat((float)(entityLivingBase.posY + (double)(entityLivingBase.getEyeHeight() / 1.5f)), (float)(entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() - (double)(entityLivingBase.getEyeHeight() / 3.0f)));
            d2 = (double)f3 - (entityPlayerSP.posY + (double)entityPlayerSP.getEyeHeight());
        } else {
            d2 = (double)RandomUtils.randomFloat((float)entity.getEntityBoundingBox().minY, (float)entity.getEntityBoundingBox().maxY) - (entityPlayerSP.posY + (double)entityPlayerSP.getEyeHeight());
        }
        switch (cy_02) {
            case a: {
                d2 += 0.6;
                break;
            }
            case c: {
                d2 -= 0.6;
                break;
            }
        }
        double d5 = Math.sqrt(d3 * d3 + d4 * d4);
        float f4 = (float)(Math.atan2(d4, d3) * 180.0 / Math.PI) - 90.0f;
        float f5 = (float)(-(Math.atan2(d2, d5) * 180.0 / Math.PI));
        if ((double)f2 > 0.0) {
            f4 += RandomUtils.randomFloat(-f2, f2);
            f5 += RandomUtils.randomFloat(-f2, f2);
        }
        float f6 = Wrapper.mc().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f7 = f6 * f6 * f6 * 1.2f;
        f4 -= f4 % f7;
        f5 -= f5 % f7;
        double d6 = entityPlayerSP.getDistance(entity.posX, entityPlayerSP.posY, entity.posZ);
        if (d6 < 0.5) {
            f5 = RandomUtils.randomInt(80, 90);
        }
        return new float[]{f4, f5};
    }
}
