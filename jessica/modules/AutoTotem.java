package jessica.modules;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;

public class AutoTotem extends Module{
	ValueNumber health = new ValueNumber("Health", 4.0, 1.0, 20.0, 1.0);
	ValueNumber delay = new ValueNumber("Delay", 100, 0, 2000, 50);
	ValueBoolean reset = new ValueBoolean("Reset", true);
	ValueBoolean explosionDetect = new ValueBoolean("Explosion detect", true);
	ValueNumber distance = new ValueNumber("Distance", 8, 5, 20, 1);
	ValueBoolean fallingDetect = new ValueBoolean("Falling detect", false);
	ValueNumber fallingDistance = new ValueNumber("Falling distance", 10.0, 4.0, 50.0, 1.0);
	public TimerUtils timer = new TimerUtils();
    public int slot;
    public boolean needTotem;
	
	public AutoTotem() {
		super("AutoTotem", Category.COMBAT);
		addValue(health);
		addValue(delay);
		addValue(reset);
		addValue(explosionDetect);
		addValue(distance);
		addValue(fallingDetect);
		addValue(fallingDistance);
	}
	
	@Override
    public void onEnable() {
        this.slot = -2;
        this.needTotem = false;
        super.onEnable();
    }

    @Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        if (this.explosionDetect.getValue()) {
            for (Entity entity : Wrapper.world().getLoadedEntityList()) {
                if (!(Wrapper.player().getDistanceToEntity(entity) < (float)this.distance.getDoubleValue()) || !(entity instanceof EntityEnderCrystal) && !(entity instanceof EntityTNTPrimed) && !(entity instanceof EntityMinecartTNT)) continue;
                this.needTotem = true;
            }
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        this.b();
        super.onPlayerTick(playerTickEvent);
    }

    public void b() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.fallingDetect.getValue() && entityPlayerSP.fallDistance > this.fallingDistance.getDoubleValue()) {
            this.needTotem = true;
        }
        if ((double)(entityPlayerSP.getHealth() + entityPlayerSP.getAbsorptionAmount() + 1.0f) <= this.health.getDoubleValue() || this.needTotem) {
            int slotTotem = this.getSlotOfTotem();
            int n3 = slotTotem < 9 ? slotTotem + 36 : slotTotem;
            ItemStack itemStack = entityPlayerSP.getHeldItemOffhand();
            if (slotTotem == -2 && EntityUtils.isNullOrEmptyStack(itemStack)) {
                this.c();
            }
            if (this.timer.check((float)this.delay.getDoubleValue()) && slotTotem != -2) {
                if (itemStack.getItem() != Items.field_190929_cY) {
                    boolean reset = this.reset.getValue() && !EntityUtils.isNullOrEmptyStack(itemStack);
                    Wrapper.mc().playerController.windowClick(0, n3, 0, ClickType.PICKUP, Wrapper.player());
                    Wrapper.mc().playerController.windowClick(0, 45, 0, ClickType.PICKUP, Wrapper.player());
                    if (reset) {
                        this.slot = n3;
                        Wrapper.mc().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.player());
                    }
                }
                this.timer.reset();
            }
        } else {
            this.c();
        }
        this.needTotem = false;
    }

    public void c() {
        if (this.slot != -2) {
            ItemStack itemStack = Wrapper.player().getHeldItemOffhand();
            boolean empty = !EntityUtils.isNullOrEmptyStack(itemStack);
            Wrapper.mc().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.player());
            Wrapper.mc().playerController.windowClick(0, 45, 0, ClickType.PICKUP, Wrapper.player());
            if (empty) {
            	Wrapper.mc().playerController.windowClick(0, slot, 0, ClickType.PICKUP, Wrapper.player());
            }
            this.slot = -2;
        }
    }

    public int getSlotOfTotem() {
        int n2 = -2;
        int n3 = -2;
        for (int i = 0; i <= 44; ++i) {
            ItemStack itemStack = Wrapper.player().inventory.getStackInSlot(i);
            if (itemStack.getItem() != Items.field_190929_cY) continue;
            if (itemStack.isItemEnchanted()) {
                n3 = i;
                continue;
            }
            n2 = i;
        }
        if (n2 == -2) {
            n2 = n3;
        }
        return n2;
    }
}
