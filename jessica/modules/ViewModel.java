package jessica.modules;

import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueNumber;

public class ViewModel extends Module {
	public static ValueNumber leftx = new ValueNumber("Left-X", 1.0, 0.0, 2.0, 0.1);
	public static ValueNumber lefty = new ValueNumber("Left-Y", 1.0, 0.0, 2.0, 0.1);
	public static ValueNumber leftz = new ValueNumber("Left-Z", 1.0, 0.0, 2.0, 0.1);
	public static ValueNumber rightx = new ValueNumber("Right-X", 1.0, 0.0, 2.0, 0.1);
	public static ValueNumber righty = new ValueNumber("Right-Y", 1.0, 0.0, 2.0, 0.1);
	public static ValueNumber rightz = new ValueNumber("Right-Z", 1.0, 0.0, 2.0, 0.1);
	
	public ViewModel() {
		super("ViewModel", Category.RENDER);
		addValue(leftx);
		addValue(lefty);
		addValue(leftz);
		addValue(rightx);
		addValue(righty);
		addValue(rightz);
	}

}
