package jessica.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class TextureUtils {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static void bindTexture(String location, float f, float g, int par1, int par2)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0D, par2, -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferBuilder.pos(par1, par2, -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferBuilder.pos(par1, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferBuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    public static void bindTexture(String location, int par1, int par2, float alpha)
    {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, alpha);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        mc.getTextureManager().bindTexture(new ResourceLocation(location));
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(0.0D, par2, -90.0D).tex(0.0D, 1.0D).endVertex();
        bufferBuilder.pos(par1, par2, -90.0D).tex(1.0D, 1.0D).endVertex();
        bufferBuilder.pos(par1, 0.0D, -90.0D).tex(1.0D, 0.0D).endVertex();
        bufferBuilder.pos(0.0D, 0.0D, -90.0D).tex(0.0D, 0.0D).endVertex();
        tessellator.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }
    
    public static void renderTexture(float x, float y, float x2, float y2) {
        GL11.glPushMatrix();
        GL11.glTranslatef(x, y, 0.0F);
        GL11.glBegin(7);
        GL11.glTexCoord2f(0.0F, 0.0F);
        GL11.glVertex3d(0.0D, 0.0D, 0.0D);
        GL11.glTexCoord2f(0.0F, 1.0F);
        GL11.glVertex3d(0.0D, y2, 0.0D);
        GL11.glTexCoord2f(1.0F, 1.0F);
        GL11.glEnd();
        GL11.glPopMatrix();
      }
}
