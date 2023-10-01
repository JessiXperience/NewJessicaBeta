package jessica.modules;

import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueBoolean;

public class Targets extends Module{
	
	public static ValueBoolean players = new ValueBoolean("Players", true);
	public static ValueBoolean mobs = new ValueBoolean("Mobs", false);
	public static ValueBoolean invisibles = new ValueBoolean("Invisibles", false);
	
	public Targets() {
		super("Targets", Category.OTHER);
		this.setShow(false);
		this.setToggled(true);
		addValue(players);
		addValue(mobs);
		addValue(invisibles);
	}
	
	@Override
    public void onDisable() {
        this.setToggled(true);
        super.onDisable();
    }	
}
