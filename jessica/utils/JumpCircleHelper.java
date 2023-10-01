package jessica.utils;

import net.minecraft.util.math.Vec3d;

public class JumpCircleHelper {
	public final Vec3d a;
    public final Vec3d b;
    public byte c;
    public byte d;

    public JumpCircleHelper(Vec3d vec3d, Vec3d vec3d2, int n2) {
        this.a = vec3d;
        this.b = vec3d2;
        this.d = (byte)n2;
    }

    public Vec3d a() {
        return this.a;
    }

    public Vec3d b() {
        return this.b;
    }

    public byte c() {
        return this.c;
    }

    public boolean d() {
        this.c = (byte)(this.c + 1);
        return this.c > this.d;
    }
}
