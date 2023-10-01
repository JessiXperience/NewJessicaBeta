package jessica.utils;

import jessica.modules.Trails;
import net.minecraft.util.math.Vec3d;

public class TrailUtils {
	public int tick;
    public Vec3d b;
    public Vec3d c;

    public TrailUtils(Vec3d vec3d, Vec3d vec3d2) {
        this.b = vec3d;
        this.c = vec3d2;
        this.tick = 1;
    }

    public boolean hasTicked() {
        ++this.tick;
        if (this.tick == 0) {
            this.tick = 1;
        }
        return this.tick > Trails.ticks.getDoubleValue();
    }
}
