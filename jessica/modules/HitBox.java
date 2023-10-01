package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class HitBox extends Module{
	ValueNumber width = new ValueNumber("Width", 0.6, 0.1, 5.0, 0.1);
	ValueNumber height = new ValueNumber("Height", 1.8, 0.1, 5.0, 0.1);
	
	public HitBox() {
		super("HitBox", Category.COMBAT);
		addValue(width);
		addValue(height);
	}

	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        for (EntityPlayer entityPlayer : Wrapper.world().playerEntities) {
            if (!this.check(entityPlayer)) continue;
            float width = (float)this.width.getDoubleValue();
            float height = (float)this.height.getDoubleValue();
            EntityUtils.setEntityBoundingBoxSize(entityPlayer, width, height);
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public void onDisable() {
        for (EntityPlayer entityPlayer : Wrapper.world().playerEntities) {
        	EntityUtils.setEntityBoundingBoxSize(entityPlayer);
        }
        super.onDisable();
    }

    public boolean check(EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof EntityPlayerSP) {
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
        return /*eQ.b(entityLivingBase)*/ true;
    }
	
}
