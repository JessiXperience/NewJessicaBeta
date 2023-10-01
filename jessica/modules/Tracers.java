package jessica.modules;

import java.awt.Color;
import java.util.List;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import jessica.events.RenderGameOverlayEvent;
import jessica.managers.FriendManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.utils.EntityUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueColor;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.MathHelper;

public class Tracers extends Module{
	public static ValueMode mode = new ValueMode("Mode", "Arrow", new String[] {"Arrow", "Tracer"});
	ValueBoolean astolfo = new ValueBoolean("Arrow astolfo", true);
	ValueNumber radius = new ValueNumber("Arrow radius", 40, 30, 100, 1);
	static ValueColor color = new ValueColor("Color", new Color(5, 171, 240, 255).getRGB());
	
	public Tracers() {
		super("Tracers", Category.RENDER);
		addValue(mode);
		addValue(astolfo);
		addValue(radius);
		addValue(color);
	}
	
	@Override
	public void onRenderGameOverlay(float partialTicks) {
		if(!mode.getValue().equalsIgnoreCase("Arrow")) {
			return;
		}
		int n2 = 0;
		List<Entity> list = Wrapper.world().getLoadedEntityList();
		for(Entity entity : list) {
			EntityLivingBase entityLiving;
			int color;
			if(entity instanceof EntityLivingBase) {
			if(entity instanceof EntityArmorStand || !(entity instanceof EntityItem) && !EntityUtils.isValidEntity(entity) && !EntityUtils.isInvisible((EntityLivingBase)entity) || entity == Wrapper.player()) continue;

				color = this.astolfo.getValue() != false ? ColorUtils.setRainbow(n2, list.size(), 0.7f) : this.color.getValue();
				if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
					color = new Color(0, 255, 0, 255).getRGB();
				}
				if (entity.isInvisible()) {
					color = new Color(0, 0, 0, 255).getRGB();
				}
				if (entity instanceof EntityLivingBase) {
					entityLiving = (EntityLivingBase)entity;
					if (entityLiving.hurtTime > 0 || entityLiving == BowAimBot.target) {
						color = new Color(255, 0, 0, 255).getRGB();
					}
				}
				drawArrow(entity, (float)radius.getDoubleValue(), color, partialTicks);
				if (!(this.astolfo.getValue())) continue;
				n2 += 10;
			}
        }
		super.onRenderGameOverlay(partialTicks);
	}
	
	public static void drawArrow(Entity entity, float f2, int n2, float f3) {
        ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc());
        float f4 = scaledResolution.getScaledWidth() / 2;
        float f5 = scaledResolution.getScaledHeight() / 2;
        coord(f4, f5, entity, f2, n2, f3);
	}
	
	public static void coord(float f2, float f3, Entity entity, float f4, int n2, float f5) {
        float f6 = (float)(entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f5);
        float f7 = (float)(entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f5);
        draw(f2, f3, f6, f7, f4, n2, f5);
    }
	
	public static void draw(float f2, float f3, float f4, float f5, float f6, int n2, float f7) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        float f8 = (float)(0.08 * (135.0 / (double)f6));
        float f9 = 14.0f;
        float[] fArray = ColorUtils.getRGBA(n2);
        float f10 = (rotate(f4, f5, f7) + 360.0f) % 360.0f;
        float f11 = (f10 - entityPlayerSP.rotationYaw + 360.0f) % 360.0f - 90.0f;
        double d2 = Math.toRadians(f11);
        float f12 = f8 / 100.0f;
        float f13 = f9 - 4.0f;
        glStart();
        GL11.glPushMatrix();
        GL11.glEnable((int)2881);
        GL11.glLineWidth((float)1.0f);
        GL11.glColor4f((float)fArray[0], (float)fArray[1], (float)fArray[2], (float)fArray[3]);
        GL11.glBegin((int)5);
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)));
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f8))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f8))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glEnd();
        GL11.glColor4f((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GL11.glBegin((int)2);
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f8))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f8))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glEnd();
        GL11.glColor4f((float)(fArray[0] / 1.5f), (float)(fArray[1] / 1.5f), (float)(fArray[2] / 1.5f), (float)fArray[3]);
        GL11.glBegin((int)5);
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 + (double)f8))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f13)));
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glVertex2d((double)((double)f2 + (double)MathHelper.cos((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f9)), (double)((double)f3 + (double)MathHelper.sin((float)((float)(d2 - (double)f12))) * ((double)f6 - (double)f9)));
        GL11.glVertex2d((double)((double)f2 + (double)(MathHelper.cos((float)((float)d2)) * f6)), (double)((double)f3 + (double)(MathHelper.sin((float)((float)d2)) * f6)));
        GL11.glEnd();
        GL11.glDisable((int)2881);
        GL11.glPopMatrix();
        glend();
    }
	
	public static float rotate(double d2, double d3, float f2) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        double d4 = d2 - (entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * (double)f2);
        double d5 = d3 - (entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * (double)f2);
        return (float)(Math.atan2(d5, d4) * 180.0 / Math.PI) - 90.0f;
    }
	
	public static void glStart() {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
    }
	
	public static void glend() {
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }
}
