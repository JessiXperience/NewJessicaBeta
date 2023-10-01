package jessica.modules;

import java.awt.Color;

import jessica.Wrapper;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.RenderUtils;
import jessica.value.ValueColor;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.gui.ScaledResolution;

public class Crosshair extends Module{
	ValueMode mode = new ValueMode("Crosshair type", "Default", new String[] {"Radial", "Default"});
	ValueNumber size = new ValueNumber("Size", 5.0, 2.0, 30.0, 1.0);
	ValueNumber width = new ValueNumber("Width", 1.0, 0.5, 5.0, 0.1);
	ValueColor color = new ValueColor("Color", new Color(5, 171, 240, 255).getRGB());
	public static boolean renderCrosshairs = true;
	
	public Crosshair() {
		super("Crosshair", Category.RENDER);
		addValue(mode);
		addValue(size);
		addValue(width);
		addValue(color);
	}
	
	@Override
    public void onDisable() {
        renderCrosshairs = true;
        super.onDisable();
    }

    @Override
    public void onRenderGameOverlay(float f2) {
        ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc());
        renderCrosshairs = false;
        int n2 = scaledResolution.getScaledWidth() / 2;
        int n3 = scaledResolution.getScaledHeight() / 2;
        if (this.mode.getValue().equalsIgnoreCase("Radial")) {
            float f3 = Wrapper.player().getCooledAttackStrength(f2);
            RenderUtils.drawRadialCrosshair((float)n2, (float)n3, 5.0f, f3, false, new Color(31, 31, 31, 168).getRGB(), (int)this.color.getValue());
        } else if (this.mode.getValue().equalsIgnoreCase("Default")) {
            float f4 = (float)this.size.getDoubleValue();
            float f5 = (float)this.width.getDoubleValue();
            RenderUtils.drawRect((double)n2, (double)((float)n3 - (f4 - f5)), (double)((float)n2 + f5), (double)((float)n3 + f4), (int)this.color.getValue());
            RenderUtils.drawRect((float)n2 - (f4 - f5), (double)n3, (double)((float)n2 + f4), (double)((float)n3 + f5), (int)this.color.getValue());
        }
        super.onRenderGameOverlay(f2);
    }
}
