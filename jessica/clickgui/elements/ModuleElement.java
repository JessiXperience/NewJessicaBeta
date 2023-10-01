package jessica.clickgui.elements;

import jessica.clickgui.panel.*;
import jessica.value.*;
import java.util.*;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.*;
import jessica.clickgui.*;
import jessica.*;
import net.minecraft.client.renderer.*;
import jessica.module.*;
import jessica.module.Module;
import jessica.modules.UserInterface;
import jessica.utils.*;

public class ModuleElement extends Element
{
    public List<Element> components;
    private Module module;
    private List<Element> elementValues;
    private int opacity;
    private int elementHeight;
    private double myHeight;
    public boolean extended;
    private Panel.AnimateEnumFormat state;
    public boolean listening = false;
    
    public ModuleElement(Module module, Panel panel, int x, int y, int offsetY, int width, int height) {
        super(panel, x, y, offsetY, width, height);
        this.components = new ArrayList<Element>();
        this.elementValues = new ArrayList<Element>();
        this.opacity = 120;
        this.state = Panel.AnimateEnumFormat.STATIC;
        this.module = module;
        this.extended = false;
        int y2 = height;
        int yNumber = 13;
        if (!module.getValues().isEmpty()) {
            for (Value v : module.getValues()) {
                if (v instanceof ValueMode) {
                    this.elementValues.add(new ModeElement((ValueMode)v, this.getPanel(), x, y, y2, width, height));
                }
                else if (v instanceof ValueBoolean) {
                    this.elementValues.add(new BooleanElement((ValueBoolean)v, this.getPanel(), x, y, y2, width, height));
                }
                else if (v instanceof ValueNumber) {
                    this.elementValues.add(new NumberElement((ValueNumber)v, this.getPanel(), x, y, yNumber, width, height));
                }
                else if(v instanceof ValueColor) {
                	this.elementValues.add(new ColorSliderElement((ValueColor)v, this.getPanel(), x, y, yNumber, width, height));
                }
                y2 += height;
                yNumber += 5;
            }
            this.calcHeight();
        }
    }
    
    @Override
    public void moved(int posX, int posY) {
        super.moved(posX, posY);
        this.elementValues.forEach(component -> component.moved((int)this.getFinishedX(), (int)this.getFinishedY()));
    }
    
    @Override
    public double getOffset() {
        return this.myHeight;
    }
    
    private void drawMyElement(int mouseX, int mouseY) {
        int elementY = 12;
        List<Element> elementValues = this.elementValues;
        for (int elementButtonSize = elementValues.size(), i = 0; i < elementButtonSize; ++i) {
        	Element element = elementValues.get(i);
            if (!element.isHidden()) {
                element.setY(this.getY() + elementY);
                element.onDraw(mouseX, mouseY);
                elementY += 12;
            }
        }
    }
    
    private int calcHeight() {
        int height = 0;
        List<Element> elementValues = this.elementValues;
        for (int elementValuesSize = elementValues.size(), i = 0; i < elementValuesSize; ++i) {
        	Element component = elementValues.get(i);
            if (!component.isHidden()) {
                height += (int)(component.getHeight() + component.getOffset());
            }
        }
        return height;
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY) {
    	Panel parent = this.getPanel();
    	int x = parent.getX() + this.getX();
    	int y = parent.getY() + this.getY();
    	int height = this.getHeight();
        int width = this.getWidth();
        boolean hovered = this.isMouseOver(mouseX, mouseY);
        this.handleScissorBox();
        this.elementHeight = this.calcHeight();
        int opacity = this.opacity;
        RenderUtils.drawRect(x, y, x + width, y + height + this.getOffset(), -14474461);
        RenderUtils.drawRect(x, y, x + width, y + 0.7, -15527149);
        if (hovered) {
        	RenderUtils.drawRect(x, y, x + width, (double)(y + this.getHeight()), -1152167085);
        }
        int color = module.isToggled() ? UserInterface.color.getValue() : -7;
        Wrapper.mc().fontRendererObj.drawString(listening == false ? module.getName() : module.getName() + (module.getKeyBind() > -1 ? " (" + Keyboard.getKeyName(module.getKeyBind()) + ")" : " (?)"), (int)(x + 4.0f), (int)(y + height / 2.0f - 4.0f), color);
        if (!this.elementValues.isEmpty() && this.myHeight <= 0.0) {
            Wrapper.mc().fontRendererObj.drawString(">", x + this.getWidth() - 8, y + this.getHeight() / 2 - 3 - Wrapper.mc().fontRendererObj.getStringWidth(">") / 2, -1);
        }
        else if (!this.elementValues.isEmpty() && this.myHeight > 0.0) {
            GlStateManager.pushMatrix();
            GlStateManager.translate(x + this.getWidth() - 2.0f, y + this.getHeight() / 2.0f, 0.0f);
            GlStateManager.rotate(90.0f, 0.0f, 0.0f, 1.0f);
            Wrapper.mc().fontRendererObj.drawString(">", 0, 0, -1);
            GlStateManager.popMatrix();
        }
        if (this.myHeight > 0.0) {
            if (parent.state != Panel.AnimateEnumFormat.RETRACTING) {
                if (!UserInterface.rgb.getValue()) {
                    Gui.drawRect(x + 1, y + 12, x + 2, (int)(y + Math.min(this.myHeight, parent.myHeight) + height), UserInterface.color.getValue());
                }
                else {
                    Gui.drawRect(x + 1, y + 12, x + 2, (int)(y + Math.min(this.myHeight, parent.myHeight) + height), ClickGui.rainbowEffect(8.0f, 1.0f).getRGB());
                }
            }
            this.drawMyElement(mouseX, mouseY);
        }
    }
    
    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.myHeight > 0.0) {
            List<Element> elementButton = this.elementValues;
            for (int elementButtonSize = elementButton.size(), i = 0; i < elementButtonSize; ++i) {
                elementButton.get(i).onMouseClick(mouseX, mouseY, mouseButton);
            }
        }
        if (this.isMouseOver(mouseX, mouseY)) {
            if (mouseButton == 0) {
                this.module.toggle();
            }
            else if (mouseButton == 1 && !this.elementValues.isEmpty()) {
                this.extended = true;
                if (this.myHeight > 0.0 && (this.state == Panel.AnimateEnumFormat.EXPANDING || this.state == Panel.AnimateEnumFormat.STATIC)) {
                    this.state = Panel.AnimateEnumFormat.RETRACTING;
                }
                else if (this.myHeight < this.elementHeight && (this.state == Panel.AnimateEnumFormat.EXPANDING || this.state == Panel.AnimateEnumFormat.STATIC)) {
                    this.state = Panel.AnimateEnumFormat.EXPANDING;
                }
            } else if(mouseButton == 2) {
            	listening = true;
            }
        }
    }
    
    @Override
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        if (this.myHeight > 0.0) {
            List<Element> elementButton = this.elementValues;
            for (int elementButtonSize = elementButton.size(), i = 0; i < elementButtonSize; ++i) {
                elementButton.get(i).onMouseRelease(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    @Override
    public void onKeyPress(int typedChar, int keyCode) {
        if (this.myHeight > 0.0) {
            List<Element> elementButton = this.elementValues;
            for (int elementButtonSize = elementButton.size(), i = 0; i < elementButtonSize; ++i) {
                elementButton.get(i).onKeyPress(typedChar, keyCode);
            }
        }
        
        if (listening) {
			if (keyCode != Keyboard.KEY_ESCAPE) {
				module.setKeyBind(keyCode);
				Wrapper.getFiles().saveBinds();
			} else {
				module.setKeyBind(-1);
				Wrapper.getFiles().saveBinds();
			}
			listening = false;
		}
    }
    
    @Override
    public void onGuiClose() {
        if (this.myHeight > 0.0) {
            List<Element> elementButton = this.elementValues;
            for (int elementButtonSize = elementButton.size(), i = 0; i < elementButtonSize; ++i) {
                elementButton.get(i).onGuiClose();
            }
        }
    }
    
    private void handleScissorBox() {
        int elementHeight = this.elementHeight;
        switch (this.state) {
            case EXPANDING: {
                if (this.myHeight < elementHeight) {
                    this.myHeight = AnimationUtils.animate(elementHeight, this.myHeight, 0.34);
                }
                else if (this.myHeight >= elementHeight) {
                    this.state = Panel.AnimateEnumFormat.STATIC;
                }
                this.myHeight = this.clamp(this.myHeight, elementHeight);
                break;
            }
            case RETRACTING: {
                if (this.myHeight > 0.0) {
                    this.myHeight = AnimationUtils.animate(0.0, this.myHeight, 0.34);
                }
                else if (this.myHeight <= 0.0) {
                    this.state = Panel.AnimateEnumFormat.STATIC;
                }
                this.myHeight = this.clamp(this.myHeight, elementHeight);
                break;
            }
            case STATIC: {
                if (this.myHeight > 0.0 && this.myHeight != elementHeight) {
                    this.myHeight = AnimationUtils.animate(elementHeight, this.myHeight, 0.34);
                }
                this.myHeight = this.clamp(this.myHeight, elementHeight);
                break;
            }
        }
    }
    
    private double clamp(double a, double max) {
        if (a < 0.0) {
            return 0.0;
        }
        if (a > max) {
            return max;
        }
        return a;
    }
}
