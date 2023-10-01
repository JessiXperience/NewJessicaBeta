package jessica.utils;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public class RenderUtils {
	public static Minecraft mc;
	//3042 - GL_BLEND
	//2848 - GL_LINE_SMOOTH
	//2896 - GL_TEXTURE_2D
	//770 - GL_SRC_ALPHA
	//771 - GL_ONE_MINUS_SRC_ALPHA
	private static AxisAlignedBB DEFAULT_AABB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
	public static float renderTick = 0.0F;
	private static EnchantmentUtils[] enchantments = new EnchantmentUtils[]{new EnchantmentUtils(Enchantments.PROTECTION, "P"), new EnchantmentUtils(Enchantments.THORNS, "T"), new EnchantmentUtils(Enchantments.SHARPNESS, "S"), new EnchantmentUtils(Enchantments.FIRE_ASPECT, "F"), new EnchantmentUtils(Enchantments.KNOCKBACK, "K"), new EnchantmentUtils(Enchantments.UNBREAKING, "U"), new EnchantmentUtils(Enchantments.POWER, "Pw"), new EnchantmentUtils(Enchantments.INFINITY, "I"), new EnchantmentUtils(Enchantments.PUNCH, "Ph")};
	
	public static void drawRect(double left, double top, double right, double bottom, int color) {
        double d6;
        if (left < right) {
            d6 = left;
            left = right;
            right = d6;
        }
        if (top < bottom) {
            d6 = top;
            top = bottom;
            bottom = d6;
        }
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        ColorUtils.glColor(color);
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public static void drawRect(float x, float y, float x2, float y2, int color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        RenderUtils.glColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2f((float)x2, (float)y);
        GL11.glVertex2f((float)x, (float)y);
        GL11.glVertex2f((float)x, (float)y2);
        GL11.glVertex2f((float)x2, (float)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }
	
	public static void drawRect(int x, int y, int x2, int y2, int color) {
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        glColor(color);
        GL11.glBegin((int)7);
        GL11.glVertex2i((int)x2, (int)y);
        GL11.glVertex2i((int)x, (int)y);
        GL11.glVertex2i((int)x, (int)y2);
        GL11.glVertex2i((int)x2, (int)y2);
        GL11.glEnd();
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glDisable((int)2848);
    }
	
	public static void drawTestRect(double left, double top, double right, double bottom, int color) {
		if (left < right) {
			double i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			double j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(left, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, bottom, 0.0D).endVertex();
		vertexbuffer.pos(right, top, 0.0D).endVertex();
		vertexbuffer.pos(left, top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	public static void drawRect(float x, float y, float x2, float y2, Color color) {
        RenderUtils.drawRect(x, y, x2, y2, color.getRGB());
    }
	
	public static void drawBorderedRect(double left, double top, double right, double bottom, float lineWidth, int color, int borderColor) {
        RenderUtils.drawRect(left, top, right, bottom, color);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        GlStateManager.glLineWidth(lineWidth);
        ColorUtils.glColor(borderColor);
        bufferBuilder.begin(1, DefaultVertexFormats.POSITION);
        bufferBuilder.pos(left, top, 0.0).endVertex();
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, top, 0.0).endVertex();
        bufferBuilder.pos(right, top, 0.0).endVertex();
        bufferBuilder.pos(left, bottom, 0.0).endVertex();
        bufferBuilder.pos(right, bottom, 0.0).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public static void drawBlockESP(BlockPos blockPos, float red, float green, float blue) {
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glLineWidth(1.0f);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        double renderPosX = Wrapper.mc().getRenderManager().viewerPosX;
        double renderPosY = Wrapper.mc().getRenderManager().viewerPosY;
        double renderPosZ = Wrapper.mc().getRenderManager().viewerPosZ;
        GL11.glTranslated(-renderPosX, -renderPosY, -renderPosZ);
        GL11.glTranslated((double)Math.floor(blockPos.getX()), (double)Math.floor(blockPos.getY()), (double)Math.floor(blockPos.getZ()));
        GlStateManager.color(red, green, blue, 0.3f);
        RenderUtils.drawSolidBox();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3553);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glPopMatrix();
    }
	
	public static void drawSolidBox() {
        RenderUtils.drawSolidBox(DEFAULT_AABB);
    }
	
	public static void drawSolidBox(AxisAlignedBB axisAlignedBB) {
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glEnd();
    }
	
	public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(3, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		tessellator.draw();
		vertexbuffer.begin(1, DefaultVertexFormats.POSITION);
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
		vertexbuffer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
		tessellator.draw();
	}
	
	public static void esp3DBOX(AxisAlignedBB axisAlignedBB) {
		GL11.glBegin((int)1);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.minY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.maxX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.maxZ);
        GL11.glVertex3d((double)axisAlignedBB.minX, (double)axisAlignedBB.maxY, (double)axisAlignedBB.minZ);
        GL11.glEnd();
    }
	
	public static void drawESP3D(Entity entity, int n2, float partialTicks) {
        try {
            RenderManager renderManager = Wrapper.mc().getRenderManager();
            double d2 = renderManager.viewerPosX;
            double d3 = renderManager.viewerPosY;
            double d4 = renderManager.viewerPosZ;
            if (entity instanceof EntityItem) {
                d3 -= (double)entity.height;
            }
            GL11.glPushMatrix();
            GL11.glEnable((int)3042);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            GL11.glLineWidth((float)1.0f);
            GL11.glDisable((int)3553);
            GL11.glEnable((int)2884);
            GL11.glDisable((int)2929);
            GL11.glDisable((int)2896);
            GL11.glTranslated((double)(-d2), (double)(-d3), (double)(-d4));
            ColorUtils.glColor(n2);
            RenderUtils.esp3DBOX(entity.getEntityBoundingBox());
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glEnable((int)2896);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glDisable((int)3042);
            GL11.glDisable((int)2848);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
           // ChatUtils.exception("RenderUtils: drawESP3D", exception);
        }
    }
	
	public static void drawTracer(Entity entity, int n2, float f2) {
        double d2 = Wrapper.mc().getRenderManager().viewerPosX;
        double d3 = Wrapper.mc().getMinecraft().getRenderManager().viewerPosY;
        double d4 = Wrapper.mc().getMinecraft().getRenderManager().viewerPosZ;
        double d5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f2 - d2;
        double d6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f2 + (double)(entity.height / 2.0f) - d3;
        double d7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f2 - d4;
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)3042);
        GL11.glEnable((int)2848);
        GL11.glDisable((int)2896);
        GL11.glLineWidth((float)1.0f);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        ColorUtils.glColor(n2);
        Vec3d vec3d = null;
        vec3d = new Vec3d(0.0, 0.0, 1.0).rotatePitch(-((float)Math.toRadians(Wrapper.player().rotationPitch))).rotateYaw(-((float)Math.toRadians(Wrapper.player().rotationYaw)));
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)vec3d.xCoord, (double)((double)Wrapper.player().getEyeHeight() + vec3d.yCoord), (double)vec3d.zCoord);
        float f3 = entity.height / 2.0f;
        GL11.glVertex3d((double)d5, (double)(d6 - (double)f3 - 0.2), (double)d7);
        GL11.glEnd();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glEnable((int)3553);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glDepthMask((boolean)true);
    }
	
	public static void drawESPWatchdogs(Entity entity, int n2, float partialTicks) {
        try {
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            RenderManager renderManager = Wrapper.mc().getRenderManager();
            if (renderManager == null || renderManager.options == null) {
                return;
            }
            double d2 = renderManager.viewerPosX;
            double d3 = renderManager.viewerPosY;
            double d4 = renderManager.viewerPosZ;
            double d5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - d2;
            double d6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks + (double)(entity.height / 2.0f) - d3;
            double d7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - d4;
            float f3 = renderManager.playerViewY;
            float f4 = renderManager.playerViewX;
            boolean bl = renderManager.options.thirdPersonView == 2;
            int n3 = 1;
            float f5 = entityPlayerSP.getDistanceToEntity(entity);
            float f6 = f5 / 4.0f;
            if (f6 < 1.6f) {
                f6 = 1.6f;
            }
            float f7 = f6 / 2.2f + 2.2f;
            f7 *= 0.3f;
            GL11.glPushMatrix();
            GlStateManager.translate((double)d5, (double)d6, (double)d7);
            GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-f3), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((float)(bl ? -1 : 1) * f4), (float)1.0f, (float)0.0f, (float)0.0f);
            GL11.glScalef((float)f7, (float)f7, (float)f7);
            GL11.glEnable((int)3042);
            GL11.glDisable((int)3553);
            GL11.glDisable((int)2896);
            GL11.glDisable((int)2929);
            GL11.glDepthMask((boolean)false);
            GL11.glLineWidth((float)1.0f);
            GL11.glBlendFunc((int)770, (int)771);
            GL11.glEnable((int)2848);
            ColorUtils.glColor(n2);
            GL11.glBegin((int)n3);
            GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
            GL11.glVertex3d((double)-0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)1.0, (double)0.0);
            GL11.glVertex3d((double)0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)-0.5, (double)0.5, (double)0.0);
            GL11.glVertex3d((double)0.0, (double)0.0, (double)0.0);
            GL11.glVertex3d((double)0.5, (double)0.5, (double)0.0);
            GL11.glEnd();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GL11.glDepthMask((boolean)true);
            GL11.glEnable((int)2929);
            GL11.glEnable((int)3553);
            GL11.glEnable((int)2896);
            GL11.glDisable((int)2848);
            GL11.glDisable((int)3042);
            GL11.glPopMatrix();
        }
        catch (Exception exception) {
           // ChatUtils.exception("RenderUtils: drawESPWatchdogs", exception);
        }
    }
	
	public static int[] sizeArmorPlusItemsInHands(EntityPlayer entityPlayer, int n2, int n3, int n4) {
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        ItemStack itemStack = entityPlayer.getHeldItemMainhand();
        ItemStack itemStack2 = entityPlayer.getHeldItemOffhand();
        ItemStack itemStack3 = inventoryPlayer.armorItemInSlot(0);
        ItemStack itemStack4 = inventoryPlayer.armorItemInSlot(1);
        ItemStack itemStack5 = inventoryPlayer.armorItemInSlot(2);
        ItemStack itemStack6 = inventoryPlayer.armorItemInSlot(3);
        ItemStack[] itemStackArray = new ItemStack[]{itemStack2, itemStack, itemStack6, itemStack5, itemStack4, itemStack3};
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (ItemStack itemStack7 : itemStackArray) {
            if (itemStack7 == null || itemStack7.func_190926_b()) continue;
            arrayList.add(itemStack7);
        }
        if (n4 != 1) {
            n2 = n2 * arrayList.size() / 3;
        }
        int n5 = 0;
        for (ItemStack itemStack8 : arrayList) {
            int n6 = RenderUtils.drawItem(itemStack8, n2, n3, n4);
            if (n6 > n5) {
                n5 = n6;
            }
            if (n4 == 0) {
                n3 += 18;
            }
            if (n4 != 1 && n4 != 3) continue;
            n2 += 18;
        }
        return new int[]{arrayList.size(), n5};
    }
	
	public static void renderItem(ItemStack itemStack, int x, int y) {
        RenderItem renderItem = Wrapper.mc().getRenderItem();
        GlStateManager.enableDepth();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.pushMatrix();
        float f2 = 1.0625f;
        GlStateManager.translate(x, y, 0.0f);
        GlStateManager.scale(1.1f / f2, (f2 + 1.0f) / 2.0f, 1.0f);
        GlStateManager.translate(-x, -(y + 16), 0.0f);
        renderItem.zLevel = 200.0f;
        renderItem.renderItemAndEffectIntoGUI(Wrapper.player(), itemStack, x, y);
        GlStateManager.popMatrix();
        renderItem.renderItemOverlays(Wrapper.mc().fontRendererObj, itemStack, x, y - 17);
        renderItem.zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        GlStateManager.disableDepth();
    }
	
	private static int drawItem(ItemStack itemStack, int n2, int n3, int n4) {
		RenderItem renderItem = Wrapper.mc().getRenderItem();
        FontRenderer fontRenderer = Wrapper.mc().fontRendererObj;
        int n5 = fontRenderer.FONT_HEIGHT + 3;
        GlStateManager.pushMatrix();
        GlStateManager.pushMatrix();
        float f2 = 1.0f;
        if (n4 == 1) {
            f2 = 1.4f;
        }
        GlStateManager.scale(f2, f2, f2);
        RenderHelper.enableGUIStandardItemLighting();
        renderItem.zLevel = -100.0f;
        GlStateManager.disableDepth();
        renderItem.renderItemIntoGUI(itemStack, n2, n3);
        renderItem.renderItemOverlayIntoGUI(fontRenderer, itemStack, n2, n3, null);
        GlStateManager.popMatrix();
        int n6 = 0;
        if (n4 == 3) {
            n3 -= n5;
            GlStateManager.disableDepth();
            GlStateManager.disableLighting();
            for (EnchantmentUtils enchantment : enchantments) {
                int n7 = EnchantmentHelper.getEnchantmentLevel((Enchantment)enchantment.getEnchantment(), (ItemStack)itemStack);
                String string = "" + n7;
                int n8 = new Color(200, 200, 200, 255).getRGB();
                if (n7 > 9) {
                    string = "9+";
                    n8 = new Color(255, 0, 0, 255).getRGB();
                }
                if (n7 <= 0) continue;
                String string2 = String.format("%s%s", enchantment.getName(), string);
                Wrapper.mc().fontRendererObj.drawString("&q" + string2, n2, n3, n8);
                n3 -= n5;
                ++n6;
            }
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
        renderItem.zLevel = 0.0f;
        RenderHelper.disableStandardItemLighting();
        GlStateManager.enableAlpha();
        GlStateManager.disableBlend();
        GlStateManager.disableLighting();
        GlStateManager.enableDepth();
        GlStateManager.popMatrix();
        return n6;
    }
	
	public static void renderTarget(EntityLivingBase entityLivingBase, int n2, float f2) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        long l2 = 2500L;
        long l3 = System.currentTimeMillis() % l2;
        boolean bl = l3 > l2 / 2L;
        double d2 = (double)l3 / ((double)l2 / 2.0);
        d2 = !bl ? 1.0 - d2 : (d2 -= 1.0);
        d2 = pow(d2);
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)2929);
        GL11.glDisable((int)2884);
        GL11.glDisable((int)2896);
        GL11.glShadeModel((int)7425);
        AxisAlignedBB axisAlignedBB = entityLivingBase.getEntityBoundingBox();
        double d3 = (axisAlignedBB.maxX - axisAlignedBB.minX + (axisAlignedBB.maxZ - axisAlignedBB.minZ)) * 0.5;
        double d4 = axisAlignedBB.maxY - axisAlignedBB.minY;
        double d5 = entityLivingBase.lastTickPosX + (entityLivingBase.posX - entityLivingBase.lastTickPosX) * (double)f2 - renderManager.viewerPosX;
        double d6 = entityLivingBase.lastTickPosY + (entityLivingBase.posY - entityLivingBase.lastTickPosY) * (double)f2 - renderManager.viewerPosY + d4 * d2;
        double d7 = entityLivingBase.lastTickPosZ + (entityLivingBase.posZ - entityLivingBase.lastTickPosZ) * (double)f2 - renderManager.viewerPosZ;
        double d8 = d2 > 0.5 ? 1.0 - d2 : d2;
        double d9 = bl ? -1.0 : 1.0;
        double d10 = d4 / 2.0 * d8 * d9;
        for (int i2 = 0; i2 < 360; i2 += 5) {
            double d11 = d5 - Math.sin((double)i2 * Math.PI / 180.0) * d3;
            double d12 = d7 + Math.cos((double)i2 * Math.PI / 180.0) * d3;
            double d13 = d5 - Math.sin((double)(i2 - 5) * Math.PI / 180.0) * d3;
            double d14 = d7 + Math.cos((double)(i2 - 5) * Math.PI / 180.0) * d3;
            GL11.glBegin((int)7);
            ColorUtils.glColor(ColorUtils.setColor(n2, 0.0f));
            GL11.glVertex3d((double)d11, (double)(d6 + d10), (double)d12);
            GL11.glVertex3d((double)d13, (double)(d6 + d10), (double)d14);
            ColorUtils.glColor(ColorUtils.setColor(n2, 0.9f));
            GL11.glVertex3d((double)d13, (double)d6, (double)d14);
            GL11.glVertex3d((double)d11, (double)d6, (double)d12);
            GL11.glEnd();
        }
        GL11.glEnable((int)2884);
        GL11.glShadeModel((int)7424);
        ColorUtils.glColor(new Color(200, 200, 200, 255).getRGB());
        GL11.glEnable((int)2929);
        GL11.glDisable((int)2848);
        GL11.glDisable((int)3042);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }
	
	public static void draw2DBox(float f2, float f3) {
        GL11.glBegin((int)1);
        GL11.glVertex3d((double)0.0, (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f2), (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)0.0, (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 - (double)f2), (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f2), (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f2), (double)(0.0 - (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 - (double)f2), (double)(0.0 + (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 - (double)f2), (double)(0.0 - (double)f3), (double)0.0);
        GL11.glVertex3d((double)0.0, (double)(0.0 - (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f2), (double)(0.0 - (double)f3), (double)0.0);
        GL11.glVertex3d((double)0.0, (double)(0.0 - (double)f3), (double)0.0);
        GL11.glVertex3d((double)(0.0 - (double)f2), (double)(0.0 - (double)f3), (double)0.0);
        GL11.glEnd();
    }
	
	public static void draw2DESP(EntityLivingBase entityLivingBase, int n2) {
        if (!((double)entityLivingBase.getHealth() > 0.0)) {
            return;
        }
        float f2 = 0.05f;
        float f3 = entityLivingBase.width + f2;
        float f4 = (float)((double)entityLivingBase.height / 1.6);
        ColorUtils.glColor(n2);
        GL11.glBegin((int)1);
        float f5 = 0.001f;
        float f6 = f2 + 0.01f;
        GL11.glVertex3d((double)(0.0 + (double)(f3 += 0.055f) + (double)f5), (double)(0.0 + (double)(f4 += 0.005f)), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 - (double)f6), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f5), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f5), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 - (double)f6), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 - (double)f6), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f5), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 - (double)f6), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glEnd();
        double d2 = (double)entityLivingBase.getHealth() / (double)entityLivingBase.getMaxHealth();
        float f7 = (float)((double)(f4 -= 0.005f) * d2);
        f3 -= 0.055f;
        if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
            ColorUtils.glColor(new Color(155, 155, 155, 155).getRGB());
            GL11.glBegin((int)7);
            GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 + (double)f4), (double)0.0);
            GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f2), (double)(0.0 + (double)f4), (double)0.0);
            GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f2), (double)(0.0 - (double)f4), (double)0.0);
            GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 - (double)f4), (double)0.0);
            GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 + (double)f4), (double)0.0);
            GL11.glEnd();
        }
        if (!(f7 > f4)) {
            f4 = f7;
        }
        ColorUtils.glColor(n2);
        GL11.glBegin((int)7);
        GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f2), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3 + (double)f2), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 - (double)f4), (double)0.0);
        GL11.glVertex3d((double)(0.0 + (double)f3), (double)(0.0 + (double)f4), (double)0.0);
        GL11.glEnd();
    }
	
	public static void drawESP2D(Entity entity, int n2, float partialTicks) {
		RenderManager renderManager = Wrapper.mc().getRenderManager();
		if (renderManager == null || renderManager.options == null) {
			return;
		}
		double d2 = renderManager.viewerPosX;
		double d3 = renderManager.viewerPosY;
		double d4 = renderManager.viewerPosZ;
		if (entity instanceof EntityItem) {
			d3 -= (double)entity.height;
		}
		double d5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - d2;
		double d6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks + (double)(entity.height / 2.0f) - d3;
		double d7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - d4;
		float f3 = renderManager.playerViewY;
		float f4 = renderManager.playerViewX;
		boolean bl = renderManager.options.thirdPersonView == 2;
		GL11.glPushMatrix();
		GL11.glTranslated((double)d5, (double)d6, (double)d7);
		GL11.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
		GL11.glRotatef((float)(-f3), (float)0.0f, (float)1.0f, (float)0.0f);
		GL11.glRotatef((float)((float)(bl ? -1 : 1) * f4), (float)1.0f, (float)0.0f, (float)0.0f);
		GL11.glEnable((int)3042);
		GL11.glDisable((int)3553);
		GL11.glDisable((int)2896);
		GL11.glDisable((int)2929);
		GL11.glDepthMask((boolean)false);
		GL11.glLineWidth((float)1.0f);
		GL11.glBlendFunc((int)770, (int)771);
		GL11.glEnable((int)2848);
		ColorUtils.glColor(n2);
		float f5 = entity.width;
		float f6 = (float)((double)entity.height / 1.6);
		RenderUtils.draw2DBox(f5, f6);
		if (entity instanceof EntityLivingBase && (double)entity.width > 0.0 && (double)entity.height > 0.0) {
			EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
			RenderUtils.draw2DESP(entityLivingBase, n2);
		}
		GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
		GL11.glDepthMask((boolean)true);
		GL11.glEnable((int)2929);
		GL11.glEnable((int)3553);
		GL11.glEnable((int)2896);
		GL11.glDisable((int)2848);
		GL11.glDisable((int)3042);
		GL11.glPopMatrix();     
	}
	
	public static void drawColorBox(AxisAlignedBB axisalignedbb, float red, float green, float blue, float alpha) {
		Tessellator ts = Tessellator.getInstance();
		BufferBuilder vb = ts.getBuffer();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts X.
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends X.
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Y.
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends Y.
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);// Starts Z.
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();
		vb.begin(7, DefaultVertexFormats.POSITION_TEX);
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		vb.pos(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ)
			.color(red, green, blue, alpha).endVertex();
		ts.draw();// Ends Z.
	}
	
	public static void drawCircle(Entity entity, double d2, int n2, float f2, int n3, float f3) {
        RenderManager renderManager = Wrapper.mc().getRenderManager();
        double d3 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)f3 - renderManager.viewerPosX;
        double d4 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)f3 - renderManager.viewerPosY;
        double d5 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)f3 - renderManager.viewerPosZ;
        GL11.glPushMatrix();
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)2896);
        GL11.glDisable((int)2929);
        GL11.glDepthMask((boolean)false);
        GL11.glLineWidth((float)f2);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        ColorUtils.glColor(n3);
        GL11.glBegin((int)3);
        for (int i2 = 0; i2 <= n2; ++i2) {
            GL11.glVertex3d((double)(d3 + d2 * Math.cos((double)i2 * (Math.PI * 2) / (double)n2)), (double)d4, (double)(d5 + d2 * Math.sin((double)i2 * (Math.PI * 2) / (double)n2)));
        }
        GL11.glEnd();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GL11.glDepthMask((boolean)true);
        GL11.glDisable((int)2848);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)2896);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glPopMatrix();
    }
	
	public static void drawRadialCrosshair(float f2, float f3, float f4, float f5, boolean bl, int n2, int n3) {
        int n4;
        float f6 = MathHelper.clamp(f5 * 2.0f, 0.0f, 2.0f);
        int n5 = 40;
        float f7 = (float)Math.PI * 2;
        GL11.glPushMatrix();
        GL11.glDisable((int)3553);
        GL11.glEnable((int)3042);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glEnable((int)2848);
        GL11.glLineWidth((float)5.0f);
        ColorUtils.glColor(n2);
        GL11.glBegin((int)3);
        for (n4 = 0; n4 <= n5; ++n4) {
            GL11.glVertex2f((float)((float)((double)f2 + (double)f4 * Math.cos((float)n4 * f7 / (float)n5))), (float)((float)((double)f3 + (double)f4 * Math.sin((float)n4 * f7 / (float)n5))));
        }
        ColorUtils.glColor(n3);
        f7 = (float)((double)f6 * Math.PI);
        for (n4 = 0; n4 <= n5; ++n4) {
            GL11.glVertex2f((float)((float)((double)f2 + (double)f4 * Math.cos((float)n4 * f7 / (float)n5))), (float)((float)((double)f3 + (double)f4 * Math.sin((float)n4 * f7 / (float)n5))));
        }
        GL11.glEnd();
        if (bl) {
            GL11.glLineWidth((float)3.0f);
            GL11.glBegin((int)3);
            for (n4 = 0; n4 <= n5; ++n4) {
                GL11.glVertex2f((float)((float)((double)f2 + (double)(f4 / 1.18f) * Math.cos((float)n4 * f7 / (float)n5))), (float)((float)((double)f3 + (double)(f4 / 1.18f) * Math.sin((float)n4 * f7 / (float)n5))));
            }
            GL11.glEnd();
        }
        GL11.glDisable((int)2848);
        GL11.glEnable((int)3553);
        GL11.glPopMatrix();
    }
	
	private static double pow(double d2) {
        if (d2 < 0.5) {
            return 2.0 * d2 * d2;
        }
        return 1.0 - Math.pow(-2.0 * d2 + 2.0, 2.0) / 2.0;
    }
	  public static void drawBorderedRect(double x, double y, double x1, double y1, double size, int borderC, int insideC) {
		    drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
		    drawRect(x + size, y + size, x1, y, borderC);
		    drawRect(x, y, x + size, y1, borderC);
		    drawRect(x1, y1, x1 - size, y + size - 1.0D, borderC);
		    drawRect(x, y1 - size, x1, y1, borderC);
		  }
	  
	  public static int rainbow(int delay, long index) {
	        double rainbowState = Math.ceil(System.currentTimeMillis() + index + (long) delay) / 15.0;
	        return Color.getHSBColor((float) ((rainbowState %= 360.0) / 360.0), 0.4f, 1.0f).getRGB();
	    }
	  
	  public static int drawStringWithShadow(String text, float x, float y, int color) {
		    glColor(color);
		    //bindDefaultFontTexture();
		    return Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, rainbow(2, 55));
		  }
	  public static void glColor(int hex) {
		    float alpha = (hex >> 24 & 0xFF) / 255.0F;
		    float red = (hex >> 16 & 0xFF) / 255.0F;
		    float green = (hex >> 8 & 0xFF) / 255.0F;
		    float blue = (hex & 0xFF) / 255.0F;
		    GL11.glColor4d(red, green, blue, alpha);
		  }
	  public static void bindDefaultFontTexture() {
		    //mc.getTextureManager().getTexture(mc.fontRendererObj.readFontTexture());
		    glEnableTexture2D();
		  }
	  public static void glEnableTexture2D() {
		    GlStateManager.enableTexture2D();
		  }
}
