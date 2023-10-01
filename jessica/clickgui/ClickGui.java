package jessica.clickgui;

import net.minecraft.client.gui.*;
import jessica.*;
import java.util.*;
import java.util.List;

import jessica.clickgui.panel.*;
import jessica.clickgui.panel.Panel;

import com.google.common.collect.*;
import net.minecraft.util.*;
import java.io.*;
import java.awt.*;
import jessica.module.*;
import jessica.modules.UserInterface;

public final class ClickGui extends GuiScreen
{
    private Category categ;
    private final List<Panel> panels;
    
    public ClickGui() {
        this.panels = Lists.newArrayList();
    }
    
    public void init() {
        final Category[] categories = Category.values();
        for (int i = categories.length - 1; i >= 0; --i) {
            this.panels.add(new Panel(categories[i], 5 + 87 * i, 15));
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).onDraw(mouseX, mouseY);
        }
        if (!this.mc.gameSettings.ofFastRender && !this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/antialias.json"));
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).onMouseClick(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void mouseReleased(final int mouseX, final int mouseY, final int state) {
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).onMouseRelease(mouseX, mouseY, state);
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) throws IOException {
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).onKeyPress(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }
    
    @Override
    public void onGuiClosed() {
        if (this.mc.entityRenderer.isShaderActive()) {
            this.mc.entityRenderer.stopUseShader();
        }
        for (int i = 0; i < this.panels.size(); ++i) {
            this.panels.get(i).onGuiClose();
        }
        super.onGuiClosed();
    }
    
    @Override
    public boolean doesGuiPauseGame()
    {
        return false;
    }
    
    public static Color rainbowEffect(final float f, final float fade) {
        final float hue = (System.nanoTime() + f) / 1.0E10f % 1.0f;
        final long color = Long.parseLong(Integer.toHexString(Color.HSBtoRGB(hue, 1.0f, 1.0f)), 16);
        final Color c = new Color((int)color);
        return new Color(c.getRed() / 255.0f * fade, c.getGreen() / 255.0f * fade, c.getBlue() / 255.0f * fade, c.getAlpha() / 255.0f);
    }
}
