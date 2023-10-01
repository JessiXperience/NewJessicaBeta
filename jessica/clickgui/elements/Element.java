package jessica.clickgui.elements;

import net.minecraft.client.*;
import jessica.clickgui.panel.*;

public abstract class Element
{
    protected static final Minecraft MC;
    private final Panel panel;
    private int x;
    private int y;
    private float finishedX;
    private float finishedY;
    private float offsetX;
    private float offsetY;
    private final int width;
    protected final int height;
    
    static {
        MC = Minecraft.getMinecraft();
    }
    
    public Element(final Panel panel, final int x, final int y, final int offsetY, final int width, final int height) {
        this.panel = panel;
        this.x = x;
        this.y = y;
        this.offsetY = (float)offsetY;
        this.width = width;
        this.height = height;
        this.finishedX = x + this.offsetX;
        this.finishedY = (float)(y + offsetY);
    }
    
    public Panel getPanel() {
        return this.panel;
    }
    
    public void moved(final int posX, final int posY) {
        this.setX(posX);
        this.setY(posY);
        this.setFinishedX(this.getX() + this.getOffsetX());
        this.setFinishedY(this.getY() + this.getOffsetY());
    }
    
    public void onDraw(final int mouseX, final int mouseY) {
    }
    
    public void onMouseClick(final int mouseX, final int mouseY, final int mouseButton) {
    }
    
    public void onMouseRelease(final int mouseX, final int mouseY, final int state) {
    }
    
    public void onKeyPress(final int typedChar, final int keyCode) {
    }
    
    public void onGuiClose() {
    }
    
    public final boolean isMouseOver(final int mouseX, final int mouseY) {
        final int x = this.panel.getX() + this.x;
        final int y = this.panel.getY() + this.y;
        return mouseX > x && mouseX <= x + this.width && mouseY > y && mouseY <= y + this.height;
    }
    
    public float getOffsetX() {
        return this.offsetX;
    }
    
    public void setOffsetX(final float offsetX) {
        this.offsetX = offsetX;
    }
    
    public float getOffsetY() {
        return this.offsetY;
    }
    
    public void setOffsetY(final float offsetY) {
        this.offsetY = offsetY;
    }
    
    public double getOffset() {
        return 0.0;
    }
    
    public int getX() {
        return this.x;
    }
    
    public void setX(final int x) {
        this.x = x;
    }
    
    public int getWidth() {
        return this.width;
    }
    
    public void setY(final int y) {
        this.y = y;
    }
    
    public int getY() {
        return this.y;
    }
    
    public float getFinishedX() {
        return this.finishedX;
    }
    
    public void setFinishedX(final float finishedX) {
        this.finishedX = finishedX;
    }
    
    public float getFinishedY() {
        return this.finishedY;
    }
    
    public void setFinishedY(final float finishedY) {
        this.finishedY = finishedY;
    }
    
    public int getHeight() {
        return this.height;
    }
    
    public boolean isHidden() {
        return false;
    }
}
