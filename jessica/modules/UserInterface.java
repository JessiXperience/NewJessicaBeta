package jessica.modules;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import jessica.Wrapper;
import jessica.clickgui.ClickGui;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueBoolean;
import jessica.value.ValueColor;
import jessica.value.ValueNumber;

public class UserInterface extends Module{
	
	private ClickGui clickGUI;
    public static ValueBoolean rgb = new ValueBoolean("Rainbow", false);
    public static ValueColor color = new ValueColor("Color", Color.ORANGE.getRGB());
        
	public UserInterface() {
		super("UserInterface", Category.RENDER);
		this.setKeyBind(Keyboard.KEY_RSHIFT);
		this.setShow(false);
		addValue(color);
		addValue(rgb);
	}
	
	@Override
	public void onClientTick(ClientTickEvent clientTickEvent) {
		if (!(Wrapper.mc().currentScreen instanceof ClickGui)) {
			this.setToggled(false);
        }
	}
	
	@Override
    public void onEnable() {
		if (this.clickGUI == null) {
            (this.clickGUI = new ClickGui()).init();
        }
        Wrapper.mc().displayGuiScreen(this.clickGUI);
        super.onEnable();
    }
	
}
