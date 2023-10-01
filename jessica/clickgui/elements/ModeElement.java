package jessica.clickgui.elements;

import jessica.value.*;
import jessica.clickgui.panel.*;
import jessica.module.*;
import jessica.modules.UserInterface;
import jessica.clickgui.*;
import net.minecraft.client.gui.*;
import jessica.*;

public final class ModeElement extends Element
{
    private final ValueMode value;
    
    public ModeElement(final ValueMode value, final Panel panel, final int x, final int y, final int offsetY, final int width, final int height) {
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
        if (!UserInterface.rgb.getValue()) {
            Gui.drawRect(x + 3, y + 1, x + this.getWidth() - 2, y + this.getHeight(), UserInterface.color.getValue());
        }
        else {
            Gui.drawRect(x + 3, y + 1, x + this.getWidth() - 2, y + this.getHeight(), ClickGui.rainbowEffect(8.0f, 1.0f).getRGB());
        }
        final boolean hovered = this.isMouseOver(mouseX, mouseY);
        if (hovered) {
            Gui.drawRect(x + 3, y + 1, x + this.getWidth() - 2, y + this.getHeight(), 536870912);
        }
        final String s = String.format("%s: %s", this.value.getName(), this.value.getValue());
        Wrapper.mc().fontRendererObj.drawStringWithShadow(s, x + this.getWidth() / 2 - (float)(Wrapper.mc().fontRendererObj.getStringWidth(s) / 2), y + this.getHeight() / 2.0f - 4.0f, -1);
    }
    
    @Override
    public void onMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.value.increment();
            }
            else if (mouseButton == 1) {
                this.value.decrement();
            }
        }
    }
}
