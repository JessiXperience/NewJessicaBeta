package jessica.clickgui.elements;

import java.awt.Color;

import jessica.Wrapper;
import jessica.clickgui.panel.Panel;
import jessica.utils.RenderUtils;
import jessica.value.ValueColor;

public class ColorSliderElement extends Element{
	ValueColor color;
	private boolean dragging;
	public float selectedHue = 0.0f;
	public float selectionY = 0.0f;
	
	public ColorSliderElement(ValueColor color, Panel panel, int x, int y, int offsetY, int width, int height) {
		super(panel, x, y, offsetY, width, height);
		this.dragging = false;
		this.color = color;
	}
	
	@Override
    public void onDraw(int mouseX, int mouseY) {
    	Panel parent = this.getPanel();
    	int x = parent.getX() + this.getX() + 3;
    	int y = parent.getY() + this.getY() + 3;
    	boolean hovered = this.isMouseOver(mouseX, mouseY);
    	//float[] fff = HSVtoRGB(this.selectedHue, 1.0f, 1.0f);
    	//Color color = new Color(fff[0], fff[1], fff[2]);
    	RenderUtils.drawRect(x - 1, y - 3, x + this.getWidth(), y + this.getHeight(), -1879048192);
        for(int pickerX = 0; pickerX < this.getWidth(); ++pickerX) {
            for(int pickerY = 0; pickerY < this.getHeight() / 2.0; ++pickerY) {
            	float[] floatcolor = HSVtoRGB(map((float)pickerX, 0.0f, (float)this.getWidth(), 0.0f, 360.0f), 1.0f, 1.0f);
            	Color c = new Color(floatcolor[0], floatcolor[1], floatcolor[2]);
                RenderUtils.drawRect(x + pickerX, y + pickerY, x + pickerX + 1.0f, y + pickerY + 1.0f, c.getRGB());
            }
        }
        RenderUtils.drawRect(x + this.selectionY, y, x + this.selectionY + 1, y + this.height / 2, new Color(16777215).getRGB());
        String s = this.color.getName() + " / " + (int) this.selectedHue;
        Wrapper.mc().fontRendererObj.drawStringWithShadow(s, x - 3 + this.getWidth() / 2 - (float)(Wrapper.mc().fontRendererObj.getStringWidth(s) / 2), y + height / 2.0f - 7.5f, -1);
        
        if (this.dragging) {
        	mouseX -= (int)x;
        	mouseY -= (int)y;
            this.selectionY = (float)mouseX;
            if (this.selectionY >= 80.0f) {
                this.selectionY = 80.0f;
                return;
            }
            if (this.selectionY <= -1.0f) {
                this.selectionY = 0.0f;
                return;
            }
            this.selectedHue = map((float)mouseX, 0.0f, (float)this.getWidth(), 0.0f, 360.0f);
            float[] hsv = HSVtoRGB(this.selectedHue, 1.0f, 1.0f);
            Color current = new Color(hsv[0], hsv[1], hsv[2]);
            this.color.setValue(current.getRGB());
            Wrapper.getFiles().saveValues();
        }
	}

	public static float map(float val, float minVal, float maxVal, float minTarget, float maxTarget) {
        return (val - minVal) / (maxVal - minVal) * (maxTarget - minTarget) + minTarget;
    }
	
	public static float[] HSVtoRGB(float hue, float saturation, float value) {
        float chroma = value * saturation;
        float hue2 = hue / 56.0f;
        float x = chroma * (1.0f - Math.abs(hue2 % 2.0f - 1.0f));
        float r1 = 0.0f;
        float g1 = 0.0f;
        float b1 = 0.0f;
        if (hue2 >= 0.0f && hue2 <= 1.0f) {
            r1 = chroma;
            g1 = x;
            b1 = 0.0f;
        }
        else if (hue2 >= 1.0f && hue2 <= 2.0f) {
            r1 = x;
            g1 = chroma;
            b1 = 0.0f;
        }
        else if (hue2 >= 2.0f && hue2 <= 3.0f) {
            r1 = 0.0f;
            g1 = chroma;
            b1 = x;
        }
        else if (hue2 >= 3.0f && hue2 <= 4.0f) {
            r1 = 0.0f;
            g1 = x;
            b1 = chroma;
        }
        else if (hue2 >= 4.0f && hue2 <= 5.0f) {
            r1 = x;
            g1 = 0.0f;
            b1 = chroma;
        }
        else if (hue2 >= 5.0f && hue2 <= 6.0f) {
            r1 = chroma;
            g1 = 0.0f;
            b1 = x;
        }
        float m = value - chroma;
        float r2 = r1 + m;
        float g2 = g1 + m;
        float b2 = b1 + m;
        return new float[] { r2, g2, b2 };
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
}
