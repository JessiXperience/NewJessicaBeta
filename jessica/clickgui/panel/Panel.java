package jessica.clickgui.panel;

import jessica.*;
import jessica.clickgui.elements.*;
import jessica.managers.ModuleManager;

import java.util.*;
import java.util.List;

import net.minecraft.client.*;
import jessica.clickgui.*;
import jessica.module.*;
import jessica.module.Module;
import jessica.modules.UserInterface;

import java.awt.*;
import net.minecraft.client.gui.*;
import org.lwjgl.opengl.*;
import jessica.utils.*;

public final class Panel
{
    public static final int HEADER_SIZE = 20;
    public static final int HEADER_OFFSET = 2;
    private final Category category;
    private final List<Element> elements;
    private final int width;
    public double myHeight;
    public boolean extended;
    private int x;
    private int lastX;
    private int y;
    private int lastY;
    private int height;
    public AnimateEnumFormat state;
    private boolean dragging;
    
    public Panel(final Category category, final int x, final int y) {
        this.elements = new ArrayList<Element>();
        this.state = AnimateEnumFormat.STATIC;
        this.category = category;
        this.x = x;
        this.y = y;
        this.width = 85;
        this.dragging = false;
        int elementY = 22;
        for (final Module mod : ModuleManager.getModules().values()) {
            if (mod.getCategory().equals(this.category)) {
                final ModuleElement element = new ModuleElement(mod, this, 0, 0, elementY, this.width, 12);
                this.elements.add(element);
                elementY += 32;
            }
            this.height = elementY - 20;
        }
    }
    
    public int getX() {
        return this.x;
    }
    
    public int getY() {
        return this.y;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public void setHeight(final int height) {
        this.height = height;
    }
    
    private void updateComponentHeight() {
        int componentY = 16;
        final List<Element> componentList = this.elements;
        for (int componentListSize = componentList.size(), i = 0; i < componentListSize; ++i) {
            final Element component = componentList.get(i);
            component.setY(componentY);
            componentY += (int)(component.getHeight() + component.getOffset());
        }
        this.height = componentY - 16;
    }
    
    public final void onDraw(final int mouseX, final int mouseY) {
        final ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
        final int x = this.x;
        final int y = this.y;
        final int width = this.width;
        if (this.dragging) {
            this.x = this.lastX + mouseX;
            this.y = this.lastY + mouseY;
        }
        this.updateComponentHeight();
        this.handleScissorBox();
        final double myHeight = this.myHeight;
        final int backgroundColor = UserInterface.color.getValue();
        if (!UserInterface.rgb.getValue()) {
            Gui.drawRect(x, y, x + width, y + 16, new Color(UserInterface.color.getValue()).getRGB());
        }
        else {
            Gui.drawRect(x, y, x + width, y + 16, ClickGui.rainbowEffect(8.0f, 1.0f).getRGB());
        }
        Wrapper.mc().fontRendererObj.drawStringWithShadow(this.category.toString(), (float)(x + width / 2 - Wrapper.mc().fontRendererObj.getStringWidth(this.category.toString()) / 2), (float)(int)(y + 10.0f - 5.0f), -1);
        if (this.extended) {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            List<Element> elements = this.elements;
            for (int i = 0; i < elements.size(); ++i) {
            	AnimationUtils.prepareScissorBox((float)x, (float)(y + 16), (float)(x + width), (float)(y + 16 + myHeight));
                elements.get(i).onDraw(mouseX, mouseY);
            }
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
    }
    
    public void onMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
    	int x = this.x;
    	int y = this.y;
    	int width = this.width;
    	double myHeight = this.myHeight;
    	if (mouseX >= x - 2 && mouseX <= x + width + 2 && mouseY >= y && mouseY <= y + 16) {
    		if (mouseButton == 0 && !this.dragging) {
                this.lastX = x - mouseX;
                this.lastY = y - mouseY;
                this.dragging = true;
            }
            if (mouseButton == 1) {
                this.extended = true;
                if (myHeight > 0.0 && (this.state == AnimateEnumFormat.EXPANDING || this.state == AnimateEnumFormat.STATIC)) {
                    this.state = AnimateEnumFormat.RETRACTING;
                }
                else if (myHeight < this.height + 2 && (this.state == AnimateEnumFormat.EXPANDING || this.state == AnimateEnumFormat.STATIC)) {
                    this.state = AnimateEnumFormat.EXPANDING;
                }
            }
        }
    	List<Element> elements = this.elements;
        for (int elementsSize = elements.size(), i = 0; i < elementsSize; ++i) {
            final Element component = elements.get(i);
            final int componentY = component.getY();
            if (componentY < myHeight + 16.0) {
                component.onMouseClick(mouseX, mouseY, mouseButton);
            }
        }
    }
    
    public final void onMouseRelease(final int mouseX, final int mouseY, final int state) {
        if (state == 0) {
            this.dragging = false;
        }
        if (this.myHeight > 0.0) {
            final List<Element> elements = this.elements;
            for (int elementsSize = elements.size(), i = 0; i < elementsSize; ++i) {
                elements.get(i).onMouseRelease(mouseX, mouseY, state);
            }
        }
    }
    
    public void onKeyPress(final char typedChar, final int keyCode) {
        if (this.myHeight > 0.0) {
            final List<Element> elements = this.elements;
            for (int elementsSize = elements.size(), i = 0; i < elementsSize; ++i) {
                elements.get(i).onKeyPress(typedChar, keyCode);
            }
        }
    }
    
    public void onGuiClose() {
        if (this.myHeight > 0.0) {
            final List<Element> elements = this.elements;
            for (int i = 0; i < elements.size(); ++i) {
                elements.get(i).onGuiClose();
            }
        }
    }
    
    private void handleScissorBox() {
        int height = this.height;
        switch (this.state) {
            case EXPANDING: {
                if (this.myHeight < height + 2) {
                    this.myHeight = AnimationUtils.animate(height + 2, this.myHeight, 0.5);
                    break;
                }
                if (this.myHeight < height + 2) {
                    break;
                }
                this.state = AnimateEnumFormat.STATIC;
                break;
            }
            case RETRACTING: {
                if (this.myHeight > 0.0) {
                    this.myHeight = AnimationUtils.animate(0.0, this.myHeight, 0.5);
                    break;
                }
                if (this.myHeight > 0.0) {
                    break;
                }
                this.state = AnimateEnumFormat.STATIC;
                break;
            }
            case STATIC: {
                if (this.myHeight > 0.0 && this.myHeight != height + 2) {
                    this.myHeight = AnimationUtils.animate(height + 2, this.myHeight, 0.5);
                }
                this.myHeight = this.clamp(this.myHeight, height + 2);
                break;
            }
        }
    }
    
    private double clamp(final double a, final double max) {
        if (a < 0.0) {
            return 0.0;
        }
        if (a > max) {
            return max;
        }
        return a;
    }
    
    public enum AnimateEnumFormat
    {
        RETRACTING("RETRACTING", 0), 
        EXPANDING("EXPANDING", 1), 
        STATIC("STATIC", 2);
        
        private AnimateEnumFormat(final String s, final int n) {
        }
    }
}
