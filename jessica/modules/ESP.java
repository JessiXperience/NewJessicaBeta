package jessica.modules;

import java.awt.Color;

import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueColor;
import jessica.value.ValueMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class ESP extends Module{
	ValueMode mode = new ValueMode("Mode", "2D", new String[] {"WatchDogs", "2D", "3D"});
	static ValueColor color = new ValueColor("Color", new Color(5, 171, 240, 255).getRGB());
	
	public ESP() {
		super("ESP", Category.RENDER);
		addValue(mode);
		addValue(color);
	}
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent, Object object) {
        if (object instanceof EntityLivingBase) {
            EntityLivingBase entityLivingBase = (EntityLivingBase)object;
            Profiler.renderProfiler((Entity)entityLivingBase, renderWorldLastEvent.partialTicks, this.mode.getValue().equalsIgnoreCase("WatchDogs"), this.mode.getValue().equalsIgnoreCase("2D"), this.mode.getValue().equalsIgnoreCase("3D"), false, false, false, false, false, false);
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }
}
