package jessica.utils;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.math.MathHelper;

public class AnimationUtils {
	public static long tAnimate;
    public static double uAnimate;
    
    public double a;
    public double b;
    public double c;

    public AnimationUtils() {
        this.a = 0.1;
    }

    public AnimationUtils(double d2) {
        this.a = d2;
    }

    public AnimationUtils(double d2, double d3) {
        this.a = d2;
        this.b = d3;
        this.c = d3;
    }

    public void a() {
        this.b = 0.0;
        this.c = 0.0;
    }

    public void a(boolean bl) {
        this.c = MathHelper.clamp((double)(this.c + (bl ? this.a : -this.a) * (double)Wrapper.mc().getRenderPartialTicks() * (double)1.8f), (double)0.0, (double)1.0);
        this.b = AnimationUtils.a(this.c);
    }

    public double b() {
        return this.b;
    }

    private static /* synthetic */ double a(double d2) {
        return 1.0 - Math.pow(1.0 - d2, 3.0);
    }
	
	public static void prepareScissorBox(final float x, final float y, final float x2, final float y2) {
        final ScaledResolution scale = new ScaledResolution(Minecraft.getMinecraft());
        final int factor = scale.getScaleFactor();
        GL11.glScissor((int)(x * factor), (int)((scale.getScaledHeight() - y2) * factor), (int)((x2 - x) * factor), (int)((y2 - y) * factor));
    }
	
	public static double animate(double target, double current, double speed) {
        boolean larger = (target > current);
        if (speed < 0.0) {
            speed = 0.0;
        }
        else if (speed > 1.0) {
            speed = 1.0;
        }
        double dif = Math.max(target, current) - Math.min(target, current);
        double factor = dif * speed;
        if (factor < 0.1) {
            factor = 0.1;
        }
        current = larger ? (current + factor) : (current - factor);
        return current;
    }
	
	public static double animation(double animation, double target, double speedTarget) {
        double da = (target - animation) / Minecraft.getDebugFPS() * 15.0f;
        if (da > 0.0f) {
            da = Math.max(speedTarget, da);
            da = Math.min(target - animation, da);
        } else if (da < 0.0f) {
            da = Math.min(-speedTarget, da);
            da = Math.max(target - animation, da);
        }
        return animation + da;
    }
}
