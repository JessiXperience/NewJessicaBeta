package jessica.modules;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import jessica.events.RenderWorldLastEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class ChinaHat extends Module{
	ValueBoolean onlyYou = new ValueBoolean("Only you", true);
	ValueBoolean onlyFriends = new ValueBoolean("Only friends", false);
	ValueNumber radius = new ValueNumber("Radius", 0.7, 0.5, 1.0, 0.1);
	
	public ChinaHat() {
		super("ChinaHat", Category.RENDER);
		addValue(onlyYou);
		addValue(onlyFriends);
		addValue(radius);
	}
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        if (this.onlyYou.getValue()) {
            this.drawChinaHat((EntityPlayer)Wrapper.player(), renderWorldLastEvent.partialTicks);
            return;
        }
        if (this.onlyFriends.getValue()) {
            this.drawChinaHat((EntityPlayer)Wrapper.player(), renderWorldLastEvent.partialTicks);
        }
        for (EntityPlayer entityPlayer : Wrapper.world().playerEntities) {
            if (this.onlyFriends.getValue() && !FriendManager.isFriend(entityPlayer.getName())) continue;
            this.drawChinaHat(entityPlayer, renderWorldLastEvent.partialTicks);
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }

    public void drawChinaHat(EntityPlayer entityPlayer, float f2) {
        RenderManager renderManager = Wrapper.mc().getRenderManager();
        if (renderManager == null || renderManager.options == null || entityPlayer == Wrapper.player() && Wrapper.mc().gameSettings.thirdPersonView == 0) {
            return;
        }
        double d2 = renderManager.viewerPosX;
        double d3 = renderManager.viewerPosY;
        double d4 = renderManager.viewerPosZ;
        double d5 = entityPlayer.lastTickPosX + (entityPlayer.posX - entityPlayer.lastTickPosX) * (double)f2 - d2;
        double d6 = entityPlayer.lastTickPosY + (entityPlayer.posY - entityPlayer.lastTickPosY) * (double)f2 + (double)(entityPlayer.height / 2.0f) - d3;
        double d7 = entityPlayer.lastTickPosZ + (entityPlayer.posZ - entityPlayer.lastTickPosZ) * (double)f2 - d4;
        ItemStack itemStack = entityPlayer.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        double d8 = (itemStack.getItem() instanceof ItemArmor ? (entityPlayer.isSneaking() ? -0.18 : 0.04) : (entityPlayer.isSneaking() ? -0.22 : 0.0)) - 0.02;
        GL11.glPushMatrix();
        GL11.glTranslated((double)d5, (double)((float)(d6 + (double)(entityPlayer.height / 2.0f) + d8)), (double)d7);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
    //    GL11.glDisable((int)2896);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2884);
        GL11.glRotatef((float)(-entityPlayer.rotationYaw), (float)0.0f, (float)1.0f, (float)0.0f);
        GL11.glBegin((int)6);
        GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
        double d9 = this.radius.getDoubleValue();
        for (int i2 = 0; i2 <= 360; ++i2) {
            ColorUtils.glColor(ColorUtils.setColor(ColorUtils.setAstolfo(i2, 361, 0.7f), 0.7f));
            GL11.glVertex3d((double)(Math.cos((double)i2 * Math.PI / 180.0) * d9), (double)0.0, (double)(Math.sin((double)i2 * Math.PI / 180.0) * d9));
            GL11.glVertex3d((double)(Math.cos(Math.toRadians(i2)) * d9), (double)0.0, (double)(Math.sin(Math.toRadians(i2)) * d9));
        }
        GL11.glVertex3d((double)0.0, (double)0.3, (double)0.0);
        GL11.glEnd();
        GlStateManager.resetColor();
        GL11.glEnable((int)2884);
        GL11.glEnable((int)3553);
       // GL11.glEnable((int)2896);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }
}
