package jessica.utils;

import java.awt.Color;

import net.minecraft.client.renderer.GlStateManager;
import optifine.MathUtils;

public class ColorUtils {
	public static float[] getRGBA(int color) {
        float a = (color >> 24 & 255) / 255.0f;
        float r = (color >> 16 & 255) / 255.0f;
        float g = (color >> 8 & 255) / 255.0f;
        float b = (color & 255) / 255.0f;
        return new float[]{r, g, b, a};
    }
	
	public static Color getHSB(float f2, float f3, float f4) {
        return Color.getHSBColor(f2, f3, f4);
    }
	
	public static int setRainbow(int n2, int n3, float f2) {
        //float f3;
        float f4;
        float f5 = 2900.0f;
        for (f4 = (float)(System.currentTimeMillis() % (long)((int)f5)) + (float)((n3 - n2) * 9); f4 > f5; f4 -= f5) {
        }
        f4 /= f5;
        if ((double)f2 > 0.5) {
            f4 = 0.5f - (f4 - 0.5f);
        }
        return Color.HSBtoRGB(f4 += 0.5f, f2, 1.0f);
    }
	
	public static int setAstolfo(int n2, int n3, float f2) {
        //float f3;
        float f4;
        float f5 = 2900.0f;
        for (f4 = (float)(System.currentTimeMillis() % (long)((int)f5)) + (float)((n3 - n2) * 9); f4 > f5; f4 -= f5) {
        }
        f4 /= f5;
        if ((double)f4 > 0.5) {
            f4 = 0.5f - (f4 - 0.5f);
        }
        return Color.HSBtoRGB(f4 += 0.5f, f2, 1.0f);
    }
	
	public static Color pulse(int n2, long l2, float f2) {
        double d2 = Math.ceil(System.currentTimeMillis() + l2 + (long)n2) / 15.0;
        Color color = ColorUtils.getHSB((float)((d2 %= 360.0) / 360.0), f2, 1.0f);
        return color;
    }
	
	public static int fade(Color color, int delay) {
		float[] hsb = new float[3];
		Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
		float brightness = Math.abs(((float) (System.currentTimeMillis() % 2000L + delay) / 1000.0F) % 2.0F - 1.0F);
		brightness = 0.5F + 0.5F * brightness;
		hsb[2] = brightness % 2.0F;
		return Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
	}
	
	public static int setColor(int rgb, float alpha) {
        float[] fArray = ColorUtils.getRGBA(rgb);
        int color = new Color(fArray[0], fArray[1], fArray[2], alpha).getRGB();
        return color;
    }
	
	public static void glColor(int color) {

		GlStateManager.color(
				(float) (color >> 16 & 255) / 255F, 
        		(float) (color >> 8 & 255) / 255F, 
        		(float) (color & 255) / 255F, 
        		(float) (color >> 24 & 255) / 255F);
	}
	  public static float clampFloat(float i_color) {
		    if (i_color > 1.0F)
		      i_color = 1.0F; 
		    if (i_color < 0.0F)
		      i_color = 0.0F; 
		    return i_color;
		  }
	  public static int darker(int color, float factor) {
		    int r = (int)((color >> 16 & 0xFF) * factor);
		    int g = (int)((color >> 8 & 0xFF) * factor);
		    int b = (int)((color & 0xFF) * factor);
		    int a = color >> 24 & 0xFF;
		    return (r & 0xFF) << 16 | (g & 0xFF) << 8 | b & 0xFF | (a & 0xFF) << 24;
		  }
}
