package jessica.modules;

import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueNumber;

public class CameraClip extends Module{
	public static ValueNumber fov = new ValueNumber("FOV", 90.0, 40.0, 160.0, 1.0);
	public static ValueNumber x = new ValueNumber("X", 0.1, 0.0, 5.0, 0.1);
	public static ValueNumber y = new ValueNumber("Y", 0.1, 0.0, 5.0, 0.1);
	
	public CameraClip() {
		super("CameraClip", Category.RENDER);
		this.addValue(fov);
		this.addValue(x);
		this.addValue(y);
	}

}
