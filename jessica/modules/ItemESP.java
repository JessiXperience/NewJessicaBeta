package jessica.modules;

import java.awt.Color;

import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueColor;
import jessica.value.ValueMode;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.projectile.EntityArrow;

public class ItemESP extends Module{
	ValueMode mode = new ValueMode("Mode", "2D", new String[] {"WatchDogs", "2D", "3D"});
	
	public ItemESP() {
		super("ItemESP", Category.RENDER);
		addValue(mode);
	}
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent, Object object) {
        if (object instanceof EntityItem || object instanceof EntityArrow) {
            Entity entity = (Entity)object;
            Profiler.renderProfiler(entity, renderWorldLastEvent.partialTicks, this.mode.getValue().equalsIgnoreCase("WatchDogs"), this.mode.getValue().equalsIgnoreCase("2D"), this.mode.getValue().equalsIgnoreCase("3D"), false, false, false, false, false, false);
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }
}
