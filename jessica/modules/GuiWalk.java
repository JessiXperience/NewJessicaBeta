package jessica.modules;

import org.lwjgl.input.Keyboard;

import jessica.Wrapper;
import jessica.clickgui.ClickGui;
import jessica.events.InputUpdateEvent;
import jessica.module.Category;
import jessica.module.Module;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.MovementInput;

public class GuiWalk extends Module{

	public GuiWalk() {
		super("GuiWalk", Category.PLAYER);
	}
	
	@Override
	public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {
		if (!(Wrapper.mc().currentScreen instanceof GuiContainer) && !(Wrapper.mc().currentScreen instanceof ClickGui)) {
			return;
		}

		MovementInput movementInput = inputUpdateEvent.getMovementInput();
		movementInput.moveStrafe = 0.0f;
		movementInput.field_192832_b = 0.0f;
		if (Keyboard.isKeyDown(Wrapper.mc().gameSettings.keyBindForward.getKeyCode())) {
			movementInput.field_192832_b += 1.0f;
			movementInput.forwardKeyDown = true;
		} else {
			movementInput.forwardKeyDown = false;
		}
		if (Keyboard.isKeyDown(Wrapper.mc().gameSettings.keyBindBack.getKeyCode())) {
			movementInput.field_192832_b -= 1.0f;
			movementInput.backKeyDown = true;
		} else {
			movementInput.backKeyDown = false;
		}
		if (Keyboard.isKeyDown(Wrapper.mc().gameSettings.keyBindLeft.getKeyCode())) {
			movementInput.moveStrafe += 1.0f;
			movementInput.leftKeyDown = true;
		} else {
			movementInput.leftKeyDown = false;
		}
		if (Keyboard.isKeyDown(Wrapper.mc().gameSettings.keyBindRight.getKeyCode())) {
			movementInput.moveStrafe -= 1.0f;
			movementInput.rightKeyDown = true;
		} else {
			movementInput.rightKeyDown = false;
		}
		movementInput.jump = Keyboard.isKeyDown(Wrapper.mc().gameSettings.keyBindJump.getKeyCode());
		super.onInputUpdate(inputUpdateEvent);
	}
}
