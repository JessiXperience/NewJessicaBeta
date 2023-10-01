package jessica.clickgui.elements;

import jessica.value.*;
import jessica.clickgui.panel.*;
import jessica.managers.ModuleManager;
import net.minecraft.util.math.*;
import jessica.*;
import net.minecraft.client.gui.*;
import jessica.module.*;
import jessica.modules.UserInterface;
import jessica.utils.AnimationUtils;
import jessica.utils.RenderUtils;
import jessica.clickgui.*;
import java.math.*;

public  class NumberElement extends Element
{
    private ValueNumber value;
    private boolean dragging;
    private double currentValueAnimate;
    
    public NumberElement(ValueNumber value, Panel panel, int x, int y, int offsetY, int width, int height) {
        super(panel, x, y, offsetY, width, height);
        this.dragging = false;
        this.value = value;
    }
    
    @Override
    public void moved(int posX, int posY) {
        super.moved(posX, posY);
    }
    
    @Override
    public void onDraw(int mouseX, int mouseY) {
    	Panel parent = this.getPanel();
    	int x = parent.getX() + this.getX();
    	int y = parent.getY() + this.getY();
    	int width = this.getWidth();
    	int height = this.getHeight();
    	boolean hovered = this.isMouseOver(mouseX, mouseY);
    	
    	//double valueValue = this.round(value.getValue(), value.increment);
    	//double renderPerc = (width - 3) / (value.getMax() - value.getMin());
    	//double barWidth = renderPerc * valueValue - renderPerc * value.getMin();
  
    	float sliderPosition = (float) ((this.value.getDoubleValue() - this.value.getMin()) / (this.value.getMax() - this.value.getMin()));
		this.currentValueAnimate = AnimationUtils.animation(this.currentValueAnimate, sliderPosition, 1.0E-9f);
    	
        RenderUtils.drawRect(x + 2, y, x + this.getWidth(), y + this.getHeight(), -1879048192);
        RenderUtils.drawRect(x + 3, y + height - 2, x + width - 3, (double)(y + 3), -1436524448);
        RenderUtils.drawRect(x + 3, y + height - 2, x + 4 + (width - 7) * this.currentValueAnimate, y + 3, !UserInterface.rgb.getValue() ? UserInterface.color.getValue() : ClickGui.rainbowEffect(8.0f, 1.0f).getRGB());
        Wrapper.mc().fontRendererObj.drawStringWithShadow(String.valueOf(this.value.getDoubleValue()), (float)(x + width - Wrapper.mc().fontRendererObj.getStringWidth(String.valueOf(this.value.getDoubleValue())) - 3), y + height / 2.0f - 4.0f, -1);
        Wrapper.mc().fontRendererObj.drawStringWithShadow(value.getName(), x + 6.0f, y + height / 2.0f - 4.0f, -1);
        
        if (this.dragging) {
        	ModuleManager.onSliderChange(this);
    		double diff = this.value.getMax() - this.value.getMin();
    		double val = this.value.getMin() + MathHelper.clamp((mouseX - (float)x) / width, 0.0f, 1.0f) * diff;
    		double round = Math.round(val / this.value.increment) * this.value.increment;
    		double result2 = Math.round(round * 100.0D) / 100.0D;
            this.value.setValue(result2);
            Wrapper.getFiles().saveValues();
    	}
        else if (value.getValue() < value.getMin()) {
        	value.setValue(value.getMin());
        }
    }
    
    @Override
    public void onMouseClick(int mouseX, int mouseY, int mouseButton) {
        if (this.isMouseOver(mouseX, mouseY)) {
            this.dragging = true;
        }
    }
    
    @Override
    public void onMouseRelease(int mouseX, int mouseY, int mouseButton) {
        this.dragging = false;
    }
    
    @Override
    public void onGuiClose() {
        this.dragging = false;
    }
    /*
    private double round( double num,  double increment) {
        double v = Math.round(num / increment) * increment;
        BigDecimal bd = new BigDecimal(v);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    */
}
