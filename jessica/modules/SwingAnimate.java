package jessica.modules;

import jessica.Wrapper;
import jessica.clickgui.elements.NumberElement;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.AnimationUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class SwingAnimate extends Module{
	public static ValueMode mode = new ValueMode("Mode", "Block", new String[] {"Block", "Swing", "Break", "Tap", "Slap"});
	static ValueNumber speed = new ValueNumber("Speed", 0.05, 0.02, 0.1, 0.01);
	static ValueBoolean leftHand = new ValueBoolean("Left hand", false);
	static ValueBoolean rightHand = new ValueBoolean("Right hand", false);
	static ValueBoolean onlyWithKillAura = new ValueBoolean("Only with KillAura", false);
	public static AnimationUtils g;
	
	public SwingAnimate() {
		super("SwingAnimate", Category.RENDER);
		addValue(mode);
		addValue(speed);
		addValue(leftHand);
		addValue(rightHand);
		addValue(onlyWithKillAura);
		g = new AnimationUtils(speed.getDoubleValue());
	}
	
	@Override
	public void onSliderChange(NumberElement e) {
		g = new AnimationUtils(speed.getDoubleValue());
	}
	
    public static boolean a(EnumHandSide enumHandSide) {
        return (enumHandSide == EnumHandSide.RIGHT && rightHand.getValue() || enumHandSide == EnumHandSide.LEFT && leftHand.getValue()) && (!onlyWithKillAura.getValue() || onlyWithKillAura.getValue() && KillAura.target != null);
    }

    public static void blockAnimation(EnumHandSide enumHandSide, float f2) {
        int n2 = enumHandSide == EnumHandSide.RIGHT ? 1 : -1;
        float f3 = MathHelper.sin((float)(f2 * f2 * (float)Math.PI));
        GlStateManager.rotate((float)((float)n2 * (45.0f + f3 * -20.0f)), (float)0.0f, (float)1.0f, (float)0.0f);
        float f4 = MathHelper.sin((float)(MathHelper.sqrt((float)f2) * (float)Math.PI));
        GlStateManager.rotate((float)-120.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)(-60.0f * f2), (float)1.0f, (float)0.0f, (float)0.0f);
    }

    public static void swingAnimation(float f2) {
        float f3 = MathHelper.sin((float)(f2 * f2 * (float)Math.PI));
        float f4 = MathHelper.sin((float)(MathHelper.sqrt((float)f2) * (float)Math.PI));
        GlStateManager.translate((float)0.46f, (float)-0.3f, (float)-0.71999997f);
        GlStateManager.rotate((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(f3 * -20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(f4 * -20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)(f4 * -40.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.translate((float)-0.5f, (float)0.2f, (float)0.0f);
        GlStateManager.rotate((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
    }

    public static void breakAnimation(float f2) {
        float f3 = MathHelper.sin((float)(MathHelper.sqrt((float)f2) * (float)Math.PI));
        GlStateManager.translate((float)0.46f, (float)-0.3f, (float)-0.71999997f);
        GlStateManager.rotate((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(f3 * -40.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.scale((float)0.5f, (float)0.5f, (float)0.5f);
        GlStateManager.translate((float)-0.5f, (float)0.2f, (float)0.0f);
        GlStateManager.rotate((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
    }

    public static void tapAnimation(float f2) {
        float f3 = f2 * 0.8f - f2 * f2 * 0.8f;
        GlStateManager.translate((float)0.56f, (float)-0.52f, (float)-0.71999997f);
        GlStateManager.translate((float)0.0f, (float)(f2 * -0.15f), (float)0.0f);
        GlStateManager.rotate((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(f3 * -90.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.scale((float)0.37f, (float)0.37f, (float)0.37f);
        GlStateManager.translate((float)-0.5f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
    }

    public static void slapAnimation(float f2) {
        GlStateManager.translate((float)0.96f, (float)-0.02f, (float)-0.71999997f);
        GlStateManager.translate((float)0.0f, (float)-0.0f, (float)0.0f);
        GlStateManager.rotate((float)45.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        float f3 = MathHelper.sin((float)0.0f);
        float f4 = MathHelper.sin((float)(MathHelper.sqrt((float)0.0f) * (float)Math.PI));
        GlStateManager.rotate((float)(f3 * -20.0f), (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)(f4 * -20.0f), (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)(f4 * -80.0f), (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.translate((float)-0.5f, (float)0.2f, (float)0.0f);
        GlStateManager.rotate((float)30.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)-80.0f, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)60.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        int n2 = (int)Math.min(255L, System.currentTimeMillis() % 255L > 127L ? Math.abs(Math.abs(System.currentTimeMillis()) % 255L - 255L) : System.currentTimeMillis() % 255L);
        float f5 = (double)f2 > 0.5 ? 1.0f - f2 : f2;
        GlStateManager.translate((float)0.3f, (float)-0.0f, (float)0.4f);
        GlStateManager.rotate((float)0.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.translate((float)0.0f, (float)0.5f, (float)0.0f);
        GlStateManager.rotate((float)90.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
        GlStateManager.translate((float)0.6f, (float)0.5f, (float)0.0f);
        GlStateManager.rotate((float)-90.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
        GlStateManager.rotate((float)-10.0f, (float)1.0f, (float)0.0f, (float)-1.0f);
        GlStateManager.rotate((float)(-f5 * 10.0f), (float)10.0f, (float)10.0f, (float)-9.0f);
        GlStateManager.rotate((float)10.0f, (float)-1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.translate((double)0.0, (double)0.0, (double)-0.5);
        GlStateManager.rotate((float)(Wrapper.player().isSwingInProgress ? (float)(-n2) / 2.0f : 0.0f), (float)1.0f, (float)-0.0f, (float)1.0f);
        GlStateManager.translate((double)0.0, (double)0.0, (double)0.5);
    }
}
