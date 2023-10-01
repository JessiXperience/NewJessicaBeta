package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.managers.ModuleManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.MobEffects;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.BlockPos;

public class Criticals extends Module{
	static ValueMode mode = new ValueMode("Mode", "Fall", new String[] {"Packet", "Jump", "Fall", "MatrixDisabler", "Matrix"});
	static ValueNumber fallDistance = new ValueNumber("Fall distance", 0.2, 0.01, 0.41, 0.01);
	ValueNumber delay = new ValueNumber("Delay", 0, 0, 500, 10);
	ValueBoolean fallAutoJump = new ValueBoolean("Fall auto jump", false);
	ValueNumber autoJumpDistance = new ValueNumber("Auto jump distance", 6.0, 1.0, 20.0, 1.0);
	static ValueBoolean smartCriticals = new ValueBoolean("Smart criticals", true);
	public static boolean isToggled;
	public EntityLivingBase target;
	private TimerUtils i = new TimerUtils();
	
	public Criticals() {
		super("Criticals", Category.COMBAT);
		addValue(mode);
		addValue(fallDistance);
		addValue(delay);
		addValue(fallAutoJump);
		addValue(autoJumpDistance);
		addValue(smartCriticals);
	}
	
	@Override
    public void onEnable() {
        this.target = null;
        super.onEnable();
    }

    @Override
    public void onDisable() {
    	isToggled = false;
        super.onDisable();
    }

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        if (side == ConnectionUtils.Side.OUT && object instanceof CPacketUseEntity) {
            CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)object;
            if (cPacketUseEntity.getAction() != CPacketUseEntity.Action.ATTACK) {
                return true;
            }
            Entity entity = cPacketUseEntity.getEntityFromWorld(Wrapper.world());
            if (entity != null && entity instanceof EntityLivingBase) {
                this.a((EntityLivingBase)entity);
            }
        }
        return true;
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
    	isToggled = true;
        this.c();
        super.onClientTick(clientTickEvent);
    }

    public void a(EntityLivingBase entityLivingBase) {
        if (!this.canJump() || this.delay.getDoubleValue() > 0 && !this.i.check((float)this.delay.getDoubleValue())) {
            return;
        }
        if (mode.getValue().equalsIgnoreCase("Packet")) {
            if (entityLivingBase.hurtTime >= 7) {
                return;
            }
            Wrapper.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.player().posX, Wrapper.player().posY + 0.0625, Wrapper.player().posZ, true));
            Wrapper.sendPacket((Packet)new CPacketPlayer.Position(Wrapper.player().posX, Wrapper.player().posY, Wrapper.player().posZ, false));
            Wrapper.player().onCriticalHit((Entity)entityLivingBase);
        } else if (mode.getValue().equalsIgnoreCase("Jump")) {
        	if (Wrapper.player().onGround) {
        		Wrapper.player().motionY = 0.42f;
            }
        }
        this.i.reset();
    }

    public static void b() {
        if (isToggled && mode.getValue().equalsIgnoreCase("MatrixDisabler")) {
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            if (entityPlayerSP.onGround) {
                entityPlayerSP.motionY = 0.16f;
                entityPlayerSP.onGround = false;
            }
        }
    }

    public void c() {
        if (!(this.fallAutoJump.getValue() && mode.getValue().equalsIgnoreCase("Fall") && ModuleManager.getModule("KillAura").isToggled())) {
            return;
        }
        for (Object e : Wrapper.world().loadedEntityList) {
            EntityLivingBase entityLivingBase;
            if (!(e instanceof EntityLivingBase) || !this.c(entityLivingBase = (EntityLivingBase)e)) continue;
            this.target = entityLivingBase;
        }
        if (this.target != null) {
        	if (Wrapper.player().onGround) {
        		Wrapper.player().motionY = 0.42f;
            }
            this.target = null;
        }
    }

    public boolean b(EntityLivingBase entityLivingBase) {
        return (double)entityLivingBase.getDistanceToEntity((Entity)Wrapper.player()) <= autoJumpDistance.getDoubleValue();
    }

    public boolean c(EntityLivingBase entityLivingBase) {
        return !(entityLivingBase instanceof EntityPlayerSP) && EntityUtils.isValidEntity(entityLivingBase) && !(Wrapper.mc().currentScreen instanceof GuiScreen) && entityLivingBase != Wrapper.player() && !entityLivingBase.isDead && entityLivingBase.deathTime <= 0 /*&& eQ.a(entityLivingBase)*/ /*&& EntityUtils.isInvisible(entityLivingBase)*/ && this.b(entityLivingBase) /*&& eQ.b(entityLivingBase)*/;
    }

    public boolean canJump() {
    	if (!Wrapper.player().onGround) {
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
    	if (Wrapper.player().isSneaking()) {
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

    private static boolean inLiquidOrLadderOrRiding() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        return entityPlayerSP.isInWater() || entityPlayerSP.isInLava() || entityPlayerSP.isOnLadder() || entityPlayerSP.isRiding();
    }

    private static boolean isInWeb() {
        return EntityUtils.isInsideBlock((EntityLivingBase)Wrapper.player(), Blocks.WEB) && MotionUtils.isWalking();
    }

    public static boolean canNotJump() {
        return Criticals.inLiquidOrLadderOrRiding() || Criticals.isInWeb();
    }

    public static boolean fallCrits() {
        return KillAura.autoCrit.getValue() || Criticals.modeFallOrMatrixDisabler();
    }

    private static boolean modeFallOrMatrixDisabler() {
        return isToggled && (mode.getValue().equalsIgnoreCase("Fall") || mode.getValue().equalsIgnoreCase("MatrixDisabler"));
    }

    public static boolean g() {
        return isToggled && (mode.getValue().equalsIgnoreCase("Matrix") || mode.getValue().equalsIgnoreCase("Packet") || mode.getValue().equalsIgnoreCase("Jump"));
    }

    public static boolean h() {
        if (Criticals.g()) {
            return true;
        }
        if (Criticals.fallCrits()) {
            boolean bl;
            float f2 = 0.2f;
            if (Criticals.modeFallOrMatrixDisabler()) {
                f2 = (float)fallDistance.getDoubleValue();
            }
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            bl = smartCriticals.getValue() && Wrapper.player().onGround && Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX, entityPlayerSP.posY + 2.0, entityPlayerSP.posZ)).getBlock() != Blocks.AIR;
            return Wrapper.player().fallDistance >= f2 && !Wrapper.player().onGround || bl || Criticals.canNotJump();
        }
        return true;
    }
	
}
