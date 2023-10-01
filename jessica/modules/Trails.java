package jessica.modules;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.utils.TrailUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueColor;
import jessica.value.ValueNumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class Trails extends Module{
	
	public static ValueNumber ticks = new ValueNumber("Ticks", 60.0D, 10.0D, 100.0D, 1.0D);
	ValueBoolean customColor = new ValueBoolean("Custom color", false);
	ValueColor colorTop = new ValueColor("Color top", new Color(5, 171, 240, 255).getRGB());
	ValueColor colorBottom = new ValueColor("Color bottom", new Color(247, 104, 2, 255).getRGB());
	ArrayList<TrailUtils> e = new ArrayList<TrailUtils>();
	
	public Trails() {
		super("Trails", Category.RENDER);
		this.addValue(ticks);
		this.addValue(customColor);
		this.addValue(colorTop);
		this.addValue(colorBottom);
	}
		
	@Override
    public void onEnable() {
        this.e.clear();
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.e.clear();
        super.onDisable();
    }
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        double d2;
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        double d3 = renderManager.viewerPosX;
        double d4 = renderManager.viewerPosY;
        double d5 = renderManager.viewerPosZ;
        float[] colorTop = null;
        float[] colorBottom = null;
        if(customColor.getValue()) {
        	colorTop = ColorUtils.getRGBA(this.colorTop.getValue());
        	colorBottom = ColorUtils.getRGBA(this.colorBottom.getValue());
        } else {
        	colorTop = ColorUtils.getRGBA(setColor(1000, 10L, 0.4f));
        	colorBottom = ColorUtils.getRGBA(setColor2(1100, 11L, 0.4f));
        }
        
        if (MotionUtils.isMoving(entityPlayerSP)) {
            double d6 = entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * (double)renderWorldLastEvent.partialTicks;
            double d7 = entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * (double)renderWorldLastEvent.partialTicks;
            d2 = entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * (double)renderWorldLastEvent.partialTicks;
            Vec3d vec3d = new Vec3d(d6, d7, d2);
            Vec3d vec3d2 = new Vec3d((double)colorBottom[0], (double)colorBottom[1], (double)colorBottom[2]);
            this.e.add(new TrailUtils(vec3d, vec3d2));
        }
        GL11.glPushMatrix();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        GL11.glBegin((int)8);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        int n2 = 0;
        double sneak = entityPlayerSP.isSneaking() ? 1.5D : 1.8D;
        for (TrailUtils b_03 : e) {
            d2 = b_03.b.xCoord - d3;
            double d8 = b_03.b.yCoord - d4;
            double d9 = b_03.b.zCoord - d5;
            float f3 = (float)n2 * 0.01f * (0.8f - (float)(b_03.tick / ticks.getValue()));
            GL11.glColor4d(b_03.c.xCoord, b_03.c.yCoord, b_03.c.zCoord, (double)f3);
            GL11.glVertex3d(d2, d8, d9);
            GL11.glColor4f(colorTop[0], colorTop[1], colorTop[2], f3);
            GL11.glVertex3d(d2, d8 + sneak, d9);
            ++n2;
        }
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GL11.glEnable((int)3008);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)2884);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.e.removeIf(TrailsUtils -> TrailsUtils.hasTicked());
    }
	
	public static int setColor(int n2, long l2, float f2) {
        Color color = astolfo(n2, l2, f2);
        return new Color(0, color.getGreen(), color.getBlue(), color.getAlpha()).getRGB();
    }
	
	public static Color astolfo(int n2, long l2, float f2) {
        double d2 = Math.ceil(System.currentTimeMillis() + l2 + (long)n2) / 15.0;
        Color color = ColorUtils.getHSB((float)((d2 %= 360.0) / 360.0), f2, 1.0f);
        return color;
    }
	
	public static int setColor2(int n2, long l2, float f2) {
        Color color = astolfo(n2, l2, f2);
        return new Color(color.getRed(), 0, color.getBlue(), color.getAlpha()).getRGB();
    }
}
