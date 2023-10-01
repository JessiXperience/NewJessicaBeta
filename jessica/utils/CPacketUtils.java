package jessica.utils;

import jessica.Wrapper;
import net.minecraft.network.play.client.CPacketPlayer;

public class CPacketUtils {
	private static double x;
    private static double y;
    private static double z;
    public static boolean isMoving;
    private static double[] h;
    private static float yaw;
    private static float pitch;
    public static boolean rotate;
    private static float[] k;
    private static boolean setGround;
    public static boolean onGround;
    public static boolean fakeGround;

    public static boolean packet(Object object, ConnectionUtils.Side side) {
        if (object instanceof CPacketPlayer) {
            CPacketPlayer cPacketPlayer = (CPacketPlayer)object;
            try {
                if (isMoving) {
                	cPacketPlayer.x = x;
                	cPacketPlayer.y = y;
                	cPacketPlayer.z = z;
                	cPacketPlayer.moving = true;
                }
                if (rotate) {
                	cPacketPlayer.yaw = yaw;
                	cPacketPlayer.pitch = pitch;
                	cPacketPlayer.rotating = true;
                }
                if (onGround) {
                	cPacketPlayer.onGround = setGround;
                }
                fakeGround = cPacketPlayer.onGround;
                if (cPacketPlayer.moving) {
                    h = new double[]{cPacketPlayer.x, cPacketPlayer.y, cPacketPlayer.z};
                }
                if (cPacketPlayer.rotating) {
                    k = new float[]{cPacketPlayer.yaw, cPacketPlayer.pitch};
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return true;
    }

    public static double a() {
        return h[0];
    }

    public static double b() {
        return h[1];
    }

    public static double c() {
        return h[2];
    }

    public static float d() {
        return k[0];
    }

    public static float e() {
        return k[1];
    }

    public static float[] f() {
        return k;
    }

    public static double[] g() {
        return h;
    }

    public static boolean h() {
        return fakeGround;
    }

    public static void setGround(boolean state) {
    	setGround = state;
    	onGround = true;
    }

    public static void a(double d2, double d3, double d4, float f2, float f3) {
    	CPacketUtils.a(d2, d3, d4);
    	CPacketUtils.a(f2, f3);
    }

    public static void a(float[] fArray) {
    	CPacketUtils.a(fArray[0], fArray[1]);
    	Wrapper.player().renderYawOffset = CPacketUtils.d();
    	Wrapper.player().rotationYawHead = CPacketUtils.d();
    }

    public static void a(float f2, float f3) {
        if (Double.isNaN(f2) || Double.isNaN(f3)) {
            return;
        }
        yaw = f2;
        pitch = f3;
        rotate = true;
    }

    public static void a(double d2, double d3, double d4) {
        if (Double.isNaN(d2) || Double.isNaN(d3) || Double.isNaN(d4)) {
            return;
        }
        x = d2;
        y = d3;
        z = d4;
        isMoving = true;
    }

    public static void i() {
    	CPacketUtils.stopRotating();
    	CPacketUtils.stopMoving();
    	CPacketUtils.inAir();
    }

    public static void stopRotating() {
    	rotate = false;
        yaw = 0.0f;
        pitch = 0.0f;
    }

    public static void stopMoving() {
    	isMoving = false;
        x = 0.0;
        y = 0.0;
        z = 0.0;
    }

    public static void inAir() {
    	if(Wrapper.player() == null) {
    		return;
    	}
    	onGround = false;
        setGround = Wrapper.player().onGround;
    }

    static {
        h = new double[]{0.0, 0.0, 0.0};
        k = new float[]{0.0f, 0.0f};
    }
}
