package jessica.modules;

import java.awt.Color;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.events.PlayerTickEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.CPacketUtils;
import jessica.utils.ConnectionUtils;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.utils.RandomUtils;
import jessica.utils.RaytraceUtils;
import jessica.utils.RenderUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketAnimation;
import net.minecraft.network.play.client.CPacketChatMessage;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;

public class KillAura extends Module{
	ValueMode mode = new ValueMode("Mode", "Switch", new String[] {"Switch", "Single"});
	static ValueMode rotation = new ValueMode("Rotation", "Default", new String[] {"Default", "Matrix", "AAC", "Static", "SunRise"});
	static ValueBoolean autoCrit = new ValueBoolean("Auto crit", true);
	ValueMode aim = new ValueMode("Aim", "Body", new String[] {"Head", "Body", "Legs", "All"});
	ValueBoolean killMessage = new ValueBoolean("Kill message", true);
	static ValueBoolean look = new ValueBoolean("Look", false);
	ValueMode shieldBreakMode = new ValueMode("Shield break mode", "Old", new String[] {"Old", "New"});
	ValueBoolean shieldBlock = new ValueBoolean("Shield block", false);
	ValueMode targetPriority = new ValueMode("Target priority", "Closest", new String[] {"Closest", "Health", "Equip"});
	ValueBoolean cooldown = new ValueBoolean("Cooldown", true);
	ValueBoolean shieldBreak = new ValueBoolean("Shield break", true);
	ValueNumber distance = new ValueNumber("Distance", 3.8D, 1.0D, 7.0D, 0.1D);
	ValueNumber FOV = new ValueNumber("FOV", 360, 1, 360, 1);
	ValueBoolean renderTarget = new ValueBoolean("Render target", true);
	ValueBoolean rayTrace = new ValueBoolean("Ray trace", false);
	ValueBoolean onlyWeapon = new ValueBoolean("Only weapon", false);
	ValueBoolean disableOnDeath = new ValueBoolean("Disable on death", false);
	ValueBoolean throughWalls = new ValueBoolean("Through walls", false);
	ValueNumber minCPS = new ValueNumber("Min CPS", 6, 1, 30, 1);
	ValueNumber maxCPS = new ValueNumber("Max CPS", 12, 1, 30, 1);
	public TimerUtils D = new TimerUtils();
    public TimerUtils E = new TimerUtils();
    public TimerUtils G = new TimerUtils();
    public TimerUtils H = new TimerUtils();
    public TimerUtils I = new TimerUtils();
	public static EntityLivingBase target;
	public static float[] K;
    public float L = 0.1f;
    public boolean shieldPressed;
    public int slot = -2;
    public boolean P;
    public float Q;
    public float R;
	public static double aaa;
	public KillAura() {
		super("KillAura", Category.COMBAT);
		addValue(rotation);
		addValue(aim);
		addValue(targetPriority);
		addValue(mode);
		addValue(rayTrace);
		addValue(distance);
		addValue(FOV);
		addValue(autoCrit);
		addValue(cooldown);
		addValue(minCPS);
		addValue(maxCPS);
		addValue(look);
		addValue(shieldBreakMode);
		addValue(shieldBlock);
		addValue(shieldBreak);
		addValue(onlyWeapon);
		addValue(disableOnDeath);
		addValue(throughWalls);
		addValue(renderTarget);
		addValue(killMessage);
		K = new float[2];
	}
	
	@Override
    public void onEnable() {
        this.b();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.b();
        this.setPressShield(false);
        super.onDisable();
    }

    @Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        this.g();
        if (this.renderTarget.getValue() && target != null) {
            RenderUtils.renderTarget(target, new Color(255, 0, 0, 255).getRGB(), renderWorldLastEvent.partialTicks);
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (!this.disableOnDeath.getValue()) {
            return;
        }
        if (EntityUtils.isDead()) {
            this.setToggled(false);
            this.onDisable();
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (KillAura.modeMatrixOrSunrise() || KillAura.modeStatic()) {
            this.f();
        }
        if (shieldBlock.getValue()) {
            this.c();
        }
        if (!KillAura.modeMatrixOrSunrise() || KillAura.lookOrModeAAC() || KillAura.modeAAC() || KillAura.modeStatic() || Criticals.fallCrits()) {
            this.z();
        }
        super.onPlayerTick(playerTickEvent);
    }

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
    	if (side != ConnectionUtils.Side.OUT) {
            return true;
        }
        if ((object instanceof CPacketPlayer.PositionRotation || object instanceof CPacketPlayer.Rotation) && KillAura.modeMatrixOrSunrise() && !KillAura.lookOrModeAAC() && !Criticals.fallCrits()) {
            this.z();
        }
        return super.onPacket(object, side);
    }

    public void b() {
    	target = null;
        KillAura.K[0] = 0.0f;
        KillAura.K[1] = 0.0f;
        this.Q = this.d();
        this.R = this.e();
        if (!KillAura.lookOrModeAAC()) {
        	CPacketUtils.stopRotating();
        }
        CPacketUtils.inAir();
        this.L = rotation.getValue().equalsIgnoreCase("SunRise") ? 0.5f : 0.1f;
    }

    public void a(EntityLivingBase entityLivingBase) {
        int n2;
        EntityPlayer entityPlayer;
        if (entityLivingBase instanceof EntityPlayer && EntityUtils.b(entityPlayer = (EntityPlayer)entityLivingBase) && (n2 = EntityUtils.getAxe()) != -2) {
            this.slot = Wrapper.player().inventory.currentItem;
            Wrapper.sendPacket((Packet)new CPacketHeldItemChange(n2));
        }
    }

    public void b(EntityLivingBase entityLivingBase) {
        int n2;
        EntityPlayer entityPlayer;
        if (entityLivingBase instanceof EntityPlayer && (entityPlayer = (EntityPlayer)entityLivingBase).isActiveItemStackBlocking() && (n2 = EntityUtils.getAxe()) != -2) {
            Wrapper.sendPacket((Packet)new CPacketHeldItemChange(n2));
            Wrapper.mc().playerController.attackEntity((EntityPlayer)Wrapper.player(), (Entity)entityPlayer);
            Wrapper.player().swingArm(EnumHand.MAIN_HAND);
            Wrapper.sendPacket((Packet)new CPacketHeldItemChange(Wrapper.player().inventory.currentItem));
        }
    }

    public void c() {
        EnumAction enumAction;
        if (!EntityUtils.f()) {
            return;
        }
        ItemStack itemStack = Wrapper.player().getHeldItemMainhand();
        if (!(EntityUtils.isNullOrEmptyStack(itemStack) || Mouse.isButtonDown((int)1) || (enumAction = itemStack.getItem().getItemUseAction(itemStack)) == EnumAction.NONE && !(itemStack.getItem() instanceof ItemBlock))) {
            this.setPressShield(false);
            this.shieldPressed = false;
            return;
        }
        if (target == null) {
            if (this.shieldPressed) {
                this.shieldPressed = false;
                this.setPressShield(this.shieldPressed);
            }
            return;
        }
        if (Wrapper.player().getCooledAttackStrength(0.0f) >= 0.75f) {
            this.shieldPressed = false;
        }
        if (Wrapper.player().getCooledAttackStrength(0.0f) <= 0.1f) {
            this.shieldPressed = true;
        }
        if (this.G.check(RandomUtils.randomInt(0, 55))) {
            this.setPressShield(this.shieldPressed);
            this.G.reset();
        }
    }

    public void setPressShield(boolean bl) {
        if (Wrapper.player().getHeldItemOffhand().getItem() instanceof ItemShield) {
            KeyBinding.setKeyBindState((int)Wrapper.mc().gameSettings.keyBindUseItem.getKeyCode(), (boolean)bl);
        }
    }

    public float d() {
        return KillAura.lookOrModeAAC() ? Wrapper.player().rotationPitch : CPacketUtils.e();
    }

    public float e() {
        return KillAura.lookOrModeAAC() ? Wrapper.player().rotationYaw : CPacketUtils.d();
    }

    public void f() {
        if (target == null) {
            return;
        }
        if (rotation.getValue().equalsIgnoreCase("Default")) {
            if (KillAura.lookOrModeAAC()) {
                K = aim1((Entity)target, this.getPlayerParts());
                Wrapper.player().rotationYaw = K[0];
                Wrapper.player().rotationPitch = K[1];
            }
        } else if (KillAura.notModeDefault()) {
            if (!KillAura.modeStatic()) {
                boolean bl = rotation.getValue().equalsIgnoreCase("SunRise");
                float f2 = 2.2f - RandomUtils.getRandom().nextFloat();
                if (bl) {
                    f2 = 5.0f - RandomUtils.getRandom().nextFloat();
                    f2 += RandomUtils.getRandom().nextFloat();
                }
                K = aim2((Entity)target, this.getPlayerParts(), f2);
                if (bl) {
                    this.R = RaytraceUtils.updateRotation(this.e(), K[0], 75.0f + RandomUtils.randomFloat(0.1f, 1.0f));
                    this.Q = RaytraceUtils.updateRotation(this.d(), K[1], 2.0f + RandomUtils.randomFloat(0.1f, 1.0f));
                    KillAura.K[0] = this.R;
                    KillAura.K[1] = this.Q;
                }
            } else {
                K = aim3((Entity)target, KillAura.lookOrModeAAC());
            }
            if (KillAura.lookOrModeAAC()) {
                Wrapper.player().rotationYaw = K[0];
                Wrapper.player().rotationPitch = K[1];
            } else {
            	CPacketUtils.a(K);
                Wrapper.player().rotationYaw = (float)(Wrapper.player().rotationYaw + 1.0E-4);
                if (this.E.check(1L)) {
                    Wrapper.player().rotationYaw = (float)(Wrapper.player().rotationYaw + 1.0E-4);
                }
                if (this.E.check(2L)) {
                    Wrapper.player().rotationYaw = (float)(Wrapper.player().rotationYaw - 2.0E-4);
                    this.E.reset();
                }
            }
        }
    }

    public static float[] aim1(Entity entity, AimCharacter cy_02) {
        double d2;
        double d3 = entity.posX - Wrapper.mc().player.posX;
        double d4 = entity.posZ - Wrapper.mc().player.posZ;
        if (entity instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            d2 = entityLivingBase.posY + (double)entityLivingBase.getEyeHeight() - (Wrapper.mc().player.posY + (double)Wrapper.mc().player.getEyeHeight());
        } else {
            d2 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (Wrapper.mc().player.posY + (double)Wrapper.mc().player.getEyeHeight());
        }
        switch (cy_02) {
            case BODY: {
                d2 -= 0.5;
                break;
            }
            case LEGS: {
                d2 -= 1.4;
                break;
            }
        }
        double d5 = Math.sqrt(d3 * d3 + d4 * d4);
        float f2 = (float)(Math.atan2(d4, d3) * 180.0 / Math.PI) - 90.0f;
        float f3 = (float)(-(Math.atan2(d2, d5) * 180.0 / Math.PI));
        float f4 = f2;
        float f5 = f3;
        return new float[]{f4, f5};
    }
    
    public static float[] aim2(Entity entity, AimCharacter cy_02, float f2) {
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
            case HEAD: {
                d2 += 0.6;
                break;
            }
            case LEGS: {
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
    
    public static float[] aim3(Entity entity, boolean bl) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        float f2 = bl ? entityPlayerSP.rotationYaw : CPacketUtils.d();
        float f3 = bl ? entityPlayerSP.rotationPitch : CPacketUtils.e();
        float f4 = 4.096f;
        double d2 = entity.posX - entityPlayerSP.posX;
        double d3 = entity.getPositionEyes((float)1.0f).yCoord - entityPlayerSP.getPositionEyes((float)1.0f).yCoord;
        double d4 = entity.posZ - entityPlayerSP.posZ;
        double d5 = Math.sqrt(Math.pow(d2, 2.0) + Math.pow(d4, 2.0));
        float f5 = (float)MathHelper.wrapDegrees((double)(Math.toDegrees(Math.atan2(d4, d2)) - 90.0));
        float f6 = (float)(-Math.toDegrees(Math.atan2(d3, d5))) + 10.0f;
        float f7 = MathHelper.wrapDegrees((float)(f5 - f2));
        float f8 = f6 - f3;
        int n2 = (int)Math.abs(f7);
        int n3 = (int)Math.abs(f8);
        float f9 = f2;
        float f10 = f3;
        if (n2 > 4 || n3 > 4) {
            if (f7 > 180.0f) {
                f7 -= 180.0f;
            }
            int n4 = (int)(Math.abs(Math.sin(aaa)) * 7.0);
            int n5 = (int)(Math.abs(Math.sin(aaa)) * 3.0);
            if (n2 < 10) {
                n4 = 0;
            }
            if (n3 < 10) {
                n5 = 0;
            }
            if (n4 == 0 && new Random().nextBoolean()) {
                n4 = 1;
            }
            f9 = f2 + (float)(f7 > 0.0f ? n4 : -n4) * f4;
            f10 = f3 + (float)(f8 > 0.0f ? n5 : -n5) * f4;
            aaa += new Random().nextDouble() * 0.19666;
        }
        return new float[]{f9, f10};
    }
    
    public void g() {
        for (Object e : Wrapper.world().loadedEntityList) {
            EntityLivingBase entityLivingBase;
            if (!(e instanceof EntityLivingBase) || !this.h(entityLivingBase = (EntityLivingBase)e) || this.mode.getValue().equalsIgnoreCase("Single") && target != null) continue;
            target = entityLivingBase;
        }
    }

    public Entity h() {
        Entity entity = null;
        if (KillAura.notModeDefault()) {
            float f2 = Wrapper.player().rotationYaw;
            float f3 = Wrapper.player().rotationPitch;
            if (!KillAura.lookOrModeAAC()) {
                f2 = CPacketUtils.d();
                f3 = CPacketUtils.e();
            }
            entity = RaytraceUtils.b(this.G(), f2, f3);
        }
        return entity;
    }

    public void z() {
        if (!this.g(target)) {
            this.b();
        }
        if (target == null) {
            return;
        }
        if (this.onlyWeapon.getValue() && !EntityUtils.e()) {
            return;
        }
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        Criticals.b();
        if (!Criticals.h()) {
            return;
        }
        if (!this.e(target)) {
            return;
        }
        if (this.cooldown.getValue()) {
            float f2 = Criticals.fallCrits() && Criticals.canNotJump() ? 1.0f : 0.92f;
            if (entityPlayerSP.getCooledAttackStrength(0.0f) >= f2) {
                if (Criticals.isToggled && Criticals.mode.getValue().equalsIgnoreCase("Matrix")) {
                    if (!MotionUtils.isWalking()) {
                        entityPlayerSP.setPosition(entityPlayerSP.posX, entityPlayerSP.posY + 0.01, entityPlayerSP.posZ);
                    }
                    if (entityPlayerSP.onGround) {
                        CPacketUtils.setGround(false);
                        entityPlayerSP.motionY = 0.0042f;
                        this.P = true;
                    }
                } else {
                    this.attack(target);
                    entityPlayerSP.resetCooldown();
                }
            }
            if (this.P && this.H.check(800L)) {
                this.attack(target);
                entityPlayerSP.resetCooldown();
                CPacketUtils.inAir();
                this.P = false;
                this.H.reset();
            }
        } else {
            int n2 = RandomUtils.randomInt((int)this.minCPS.getDoubleValue(), (int)this.maxCPS.getDoubleValue());
            if (this.D.check(1000 / n2)) {
                this.attack(target);
                this.D.reset();
            }
        }
        if (!this.g(target) && this.mode.getValue().equalsIgnoreCase("Switch")) {
            this.b();
        }
    }

    public void attack(EntityLivingBase entityLivingBase) {
        boolean bl;
        EntityPlayerSP entityPlayerSP = Wrapper.player();
   //     if (((Boolean)this.q.getObjectValue()).booleanValue()) {
   //         entityPlayerSP.setPosition(entityLivingBase.posX, entityPlayerSP.posY, entityLivingBase.posZ);
   //     }
        if (shieldBlock.getValue() && EntityUtils.f() && this.shieldPressed) {
            return;
        }
   //     if (((Boolean)this.i.getObjectValue()).booleanValue()) {
            Wrapper.sendPacket((Packet)new CPacketEntityAction((Entity)entityPlayerSP, CPacketEntityAction.Action.STOP_SPRINTING));
    //    }
        if (KillAura.modeAAC()) {
            this.f();
        }
        EntityLivingBase entityLivingBase2 = entityLivingBase;
        if (KillAura.notModeDefault() && (this.rayTrace.getValue() || rotation.getValue().equalsIgnoreCase("SunRise"))) {
            Entity entity = this.h();
            if (entity == null) {
                return;
            }
            entityLivingBase2 = (EntityLivingBase)entity;
        }
        if (shieldBreak.getValue() && this.shieldBreakMode.getValue().equalsIgnoreCase("Old")) {
            this.a(entityLivingBase2);
        }
        boolean bl2 = bl = entityPlayerSP.fallDistance > 0.0f && !entityPlayerSP.onGround && !entityPlayerSP.isOnLadder() && !entityPlayerSP.isInWater() && entityPlayerSP.getRidingEntity() == null;
        if (bl) {
            Wrapper.player().onCriticalHit((Entity)entityLivingBase2);
        }
        Wrapper.sendPacket((Packet)new CPacketUseEntity((Entity)entityLivingBase2));
        Wrapper.player().swingArm(EnumHand.MAIN_HAND);
        if (shieldBreak.getValue() && this.shieldBreakMode.getValue().equalsIgnoreCase("New")) {
            this.b(entityLivingBase2);
        }
        if (this.slot != -2) {
            Wrapper.sendPacket((Packet)new CPacketHeldItemChange(this.slot));
            this.slot = -2;
        }
        this.shieldPressed = true;
    }

    enum AimCharacter {
   	 HEAD,
   	 BODY,
   	 LEGS;
   }
    
    public AimCharacter getPlayerParts() {
        if (this.aim.getValue().equalsIgnoreCase("Head")) {
            return AimCharacter.HEAD;
        }
        if (this.aim.getValue().equalsIgnoreCase("Body")) {
            return AimCharacter.BODY;
        }
        if (this.aim.getValue().equalsIgnoreCase("Legs")) {
            return AimCharacter.LEGS;
        }
        if (this.aim.getValue().equalsIgnoreCase("All")) {
            int n2 = RandomUtils.randomInt(0, 3);
            return AimCharacter.values()[n2];
        }
        return AimCharacter.BODY;
    }

    public static boolean notModeDefault() {
        return KillAura.modeMatrixOrSunrise() || KillAura.modeAAC() || KillAura.modeStatic();
    }

    public static boolean modeMatrixOrSunrise() {
        return rotation.getValue().equalsIgnoreCase("Matrix") || rotation.getValue().equalsIgnoreCase("SunRise");
    }

    public static boolean modeAAC() {
        return rotation.getValue().equalsIgnoreCase("AAC");
    }

    public static boolean modeStatic() {
        return rotation.getValue().equalsIgnoreCase("Static");
    }

    public static boolean lookOrModeAAC() {
        return look.getValue() || KillAura.modeAAC();
    }

    public boolean targetPriority(EntityLivingBase entityLivingBase) {
        return this.targetPriority.getValue().equalsIgnoreCase("Closest") && EntityUtils.isClosest(entityLivingBase, target) || this.targetPriority.getValue().equalsIgnoreCase("Health") && EntityUtils.isLowHealth(entityLivingBase, target) || this.targetPriority.getValue().equalsIgnoreCase("Equip") && EntityUtils.getMostArmored(entityLivingBase, target);
    }

    public boolean e(EntityLivingBase entityLivingBase) {
        return entityLivingBase.getDistanceToEntity((Entity)Wrapper.player()) <= this.G() - this.L;
    }

    public boolean f(EntityLivingBase entityLivingBase) {
        return entityLivingBase.getDistanceToEntity((Entity)Wrapper.player()) <= this.G() + this.L;
    }

    public boolean a(EntityLivingBase entityLivingBase, float f2) {
        return entityLivingBase.getDistanceToEntity((Entity)Wrapper.player()) <= this.G() + f2;
    }

    public float G() {
        return (float)(Wrapper.player().isSprinting() ? (double)this.distance.getDoubleValue() - 0.1 : (double)this.distance.getDoubleValue());
    }

    public boolean g(EntityLivingBase entityLivingBase) {
        return entityLivingBase != null && !entityLivingBase.isDead && entityLivingBase.deathTime <= 0 && (this.f(entityLivingBase) || !this.mode.getValue().equalsIgnoreCase("Switch")) && (this.a(entityLivingBase, 2.0f) || !this.mode.getValue().equalsIgnoreCase("Single"));
    }

    public boolean h(EntityLivingBase entityLivingBase) {
        this.i(entityLivingBase);
        return !(entityLivingBase instanceof EntityPlayerSP) && EntityUtils.isValidEntity(entityLivingBase) && !(Wrapper.mc().currentScreen instanceof GuiScreen) && entityLivingBase != Wrapper.player() && !entityLivingBase.isDead && entityLivingBase.deathTime <= 0 && !FriendManager.isFriend(entityLivingBase.getName()) /*&& EntityUtils.isInvisible(entityLivingBase)*/ && this.f(entityLivingBase) /*&& eQ.b(entityLivingBase)*/ && EntityUtils.isInFOV(entityLivingBase, this.FOV.getDoubleValue()) && (this.throughWalls.getValue() || Wrapper.player().canEntityBeSeen((Entity)entityLivingBase)) && this.targetPriority(entityLivingBase);
    }

    public void i(EntityLivingBase entityLivingBase) {
        if (entityLivingBase != null && entityLivingBase instanceof EntityPlayer && entityLivingBase.deathTime > 0 && this.I.check(1000L)) {
            if (this.killMessage.getValue()) {
                Wrapper.sendPacket((Packet)new CPacketChatMessage(this.H()));
            }
            //++DiscordRPC.b;
            this.I.reset();
        }
    }

    public String H() {
        int n2 = RandomUtils.randomInt(0, 14);
        switch (n2) {
            case 0: {
                return "\u043a\u0438\u043a\u0430\u0435\u0442 \u0437\u0430 \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u0443? \u043a\u0443\u043f\u0438 DeadCode";
            }
            case 1: {
                return "\u041d\u0435 \u043f\u0440\u043e\u0431\u0438\u0432\u0430\u0435\u0448\u044c \u0449\u0438\u0442 \u0447\u0435\u043b\u0430\u043c? \u0433\u0435\u0442\u043d\u0438 DeadCode";
            }
            case 2: {
                return "\u0435\u0441\u043b\u0438 \u043d\u0435 \u0445\u043e\u0447\u0435\u0448\u044c \u0441\u043b\u0438\u0432\u0430\u0442\u044c \u0441\u0432\u043e\u044e \u043f\u043e\u0441\u043b\u0435\u0434\u043d\u0438\u044e \u04374 - \u043f\u0440\u0438\u043e\u0431\u0440\u0435\u0442\u0438 DeadCode";
            }
            case 3: {
                return "\u0445\u043e\u0447\u0435\u0448\u044c \u043a\u0440\u0443\u0442\u0443\u044e \u043e\u043f\u0442\u0438\u043c\u0438\u0437\u0430\u0446\u0438\u044e, \u0447\u0442\u043e\u0431\u044b \u0438\u0433\u0440\u0430\u0442\u044c \u0431\u0435\u0437 \u043b\u0430\u0433\u043e\u0432? - \u0431\u0435\u0440\u0438 DeadCode";
            }
            case 4: {
                return "\u0425\u043e\u0447\u0435\u0448\u044c \u0432\u044b\u0432\u043e\u0437\u0438\u0442\u044c \u0432\u0441\u0435 \u0445\u0432\u0445? \u041a\u0443\u043f\u0438 DeadCode";
            }
            case 5: {
                return "\u0435\u0441\u043b\u0438 \u043d\u0435 \u0445\u043e\u0447\u0435\u0448\u044c \u043f\u043e\u0442\u0435\u0440\u044f\u0442\u044c \u043c\u0430\u0442\u044c - \u0433\u0435\u0442\u043d\u0438 DeadCode \u043a\u043b\u043e\u0443\u043d";
            }
            case 6: {
                return "\u0425\u043e\u0447\u0435\u0448\u044c \u043f\u043e\u0447\u0443\u0432\u0441\u0442\u0432\u043e\u0432\u0430\u0442\u044c \u0441\u0435\u0431\u044f \u0431\u043e\u0433\u043e\u043c \u0432 \u043c\u0430\u0439\u043d\u043a\u0440\u0430\u0444\u0442\u0435 - \u043f\u0440\u0438\u043e\u0431\u0440\u0435\u0442\u0438 DeadCode";
            }
            case 7: {
                return "\u0437\u0430\u04356\u0430\u043b\u0441\u044f \u0441\u043e\u0441\u0430\u0442\u044c? \u0433\u0435\u0442\u043d\u0438 \u0434\u043a";
            }
            case 8: {
                return "\u0441\u0432\u0438\u043d\u044c\u044f \u0442\u0443\u043f\u0430\u044f \u0433\u0435\u0442\u043d\u0438 DeadCode";
            }
            case 9: {
                return "\u043d\u0435 \u0438\u0433\u0440\u0430\u0439 \u0441 \u043f\u043e\u043c\u043e\u0439\u043a\u043e\u0439 \u043b\u0443\u0447\u0448\u0435 \u0433\u0435\u0442\u043d\u0438 deadcode";
            }
            case 10: {
                return "\u0421\u0430\u043c\u0430\u044f \u043b\u0443\u0447\u0448\u0430\u044f \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u0430 \u0442\u043e\u043b\u044c\u043a\u043e \u0432 DeadCode, \u0432\u043e\u0442 \u0442\u044b \u0438 \u0441\u043e\u0441\u0435\u0448\u044c";
            }
            case 11: {
                return "\u0425\u043e\u0447\u0435\u0448\u044c \u043b\u0443\u0447\u0448\u0443\u044e \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u0443 \u043d\u0430 4 \u0431\u043b\u043e\u043a\u0430? \u0431\u0435\u0440\u0438 DeadCode";
            }
            case 12: {
                return "\u0422\u0435\u0431\u044f \u0431\u0430\u043d\u044f\u0442 \u043c\u043e\u0434\u0435\u0440\u044b \u043d\u0430 \u043f\u0440\u043e\u0432\u0435\u0440\u043a\u0435? \u043a\u0443\u043f\u0438 \u0434\u0435\u0434\u043a\u043e\u0434, \u043d\u0435 \u0441\u043f\u0430\u043b\u044f\u0442!";
            }
            case 13: {
                return "\u0432 \u0434\u0435\u0434\u043a\u043e\u0434\u0435 \u0435\u0441\u0442\u044c \u043d\u0435 \u0442\u043e\u043b\u044c\u043a\u043e \u043b\u0443\u0447\u0448\u0430\u044f \u043a\u0438\u043b\u043b\u0430\u0443\u0440\u0430, \u0434\u0430\u043a \u0435\u0449\u0451 \u0438 \u043b\u0443\u0447\u0448\u0438\u0435 \u043e\u0431\u0445\u043e\u0434\u044b!";
            }
        }
        return "";
    }	
}
