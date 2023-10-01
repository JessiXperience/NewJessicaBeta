package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.CPacketUtils;
import jessica.utils.EntityUtils;
import jessica.utils.RaytraceUtils;
import jessica.value.ValueBoolean;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;

public class BowAimBot extends Module{
	public static EntityLivingBase target;
	ValueBoolean predict = new ValueBoolean("Predict", false);
	
	public BowAimBot() {
		super("BowAimBot", Category.COMBAT);
		addValue(predict);
	}
	
	@Override
    public void onDisable() {
		target = null;
        super.onDisable();
    }

    @Override
    public void onEnable() {
    	target = null;
        super.onEnable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        ItemStack itemStack = Wrapper.player().inventory.getCurrentItem();
        if (itemStack == null || !(itemStack.getItem() instanceof ItemBow)) {
        	target = null;
            return;
        }
        if (!Wrapper.mc().gameSettings.keyBindUseItem.isKeyDown()) {
            CPacketUtils.i();
            target = null;
            return;
        }
        target = this.getClosestEntity();
        if (target == null) {
            return;
        }
        RaytraceUtils.faceBow(target, true, predict.getValue(), 2.0f);
        super.onClientTick(clientTickEvent);
    }

    public boolean check(EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityArmorStand) {
            return false;
        }
        if (!EntityUtils.isValidEntity(entityLivingBase)) {
            return false;
        }
        if (entityLivingBase == Wrapper.player()) {
            return false;
        }
        if (entityLivingBase.isDead) {
            return false;
        }
        if (FriendManager.isFriend(entityLivingBase.getName())) {
            return false;
        }
        if (!Wrapper.player().canEntityBeSeen(entityLivingBase)) {
            return false;
        }
        
     //   if (!eQ.b(entityLivingBase)) {
     //       return false;
     //   }
        return true;
    }

    public EntityLivingBase getClosestEntity() {
        EntityLivingBase closestEntity = null;
        for (Object e : Wrapper.world().getLoadedEntityList()) {
            EntityLivingBase entityLivingBase2;
            if (!(e instanceof EntityLivingBase) || e instanceof EntityArmorStand || !this.check(entityLivingBase2 = (EntityLivingBase)e) || closestEntity != null && !(Wrapper.player().getDistanceToEntity(entityLivingBase2) < Wrapper.player().getDistanceToEntity(closestEntity))) continue;
            closestEntity = entityLivingBase2;
        }
        return closestEntity;
    }
}
