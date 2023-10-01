package jessica.modules;

import jessica.Wrapper;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import net.minecraft.entity.Entity;

public class Optimization extends Module{

	public Optimization() {
		super("Optimization", Category.OTHER);
	}
	
	public static boolean canSeeEntity(Entity entity) {
        float f2 = 170.0f;
        if (Wrapper.player().rotationPitch > 50.0f) {
            f2 *= Wrapper.player().rotationPitch / 40.0f;
        }
        return EntityUtils.isInFOV(entity, (double)f2);
    }
	
}
