package jessica.clickgui.elements;

import jessica.value.*;
import jessica.clickgui.panel.*;
import net.minecraft.client.gui.*;
import jessica.module.*;
import jessica.modules.UserInterface;
import jessica.clickgui.*;
import jessica.*;

public final class BooleanElement extends Element
{
    private final ValueBoolean value;
    
    public BooleanElement(final ValueBoolean value, final Panel panel, final int x, final int y, final int offsetY, final int width, final int height) {
        super(panel, x, y, offsetY, width, height);
        this.value = value;
    }
    
    @Override
    public void moved(final int posX, final int posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void onDraw(final int mouseX, final int mouseY) {
        final Panel parent = this.getPanel();
        final int x = parent.getX() + this.getX();
        final int y = parent.getY() + this.getY();
        Gui.drawRect(x + 2, y, x + this.getWidth(), y + this.getHeight(), -1879048192);
        Gui.drawRect(x + 3, y + 2, x + 11, y + 10, -1);
        if (this.value.getValue()) {
        	Gui.drawRect(x + 4, y + 3, x + 10, y + 9, !UserInterface.rgb.getValue() ? UserInterface.color.getValue() : ClickGui.rainbowEffect(8.0f, 1.0f).getRGB());
        }
        Wrapper.mc().fontRendererObj.drawString(this.value.getName(), (int)(x + 14.0f), (int)(y + this.getHeight() / 2.0f - 4.0f), -1);
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.value.setValue(!this.value.getValue());
            Wrapper.getFiles().saveValues();
        }
    }
}
