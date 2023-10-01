package jessica.modules;

import java.awt.Color;

import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueColor;

public class Ambience extends Module{
	public static ValueColor color = new ValueColor("Color", Color.LIGHT_GRAY.getRGB());
	
	public Ambience() {
		super("Ambience", Category.RENDER);
		this.addValue(color);
	}
	
}
