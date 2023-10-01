package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiButton extends Gui {
  protected static final ResourceLocation BUTTON_TEXTURES = new ResourceLocation("textures/gui/widgets.png");
  
  protected int width;
  
  protected int height;
  
  public int xPosition;
  
  public int yPosition;
  
  public String displayString;
  
  public int id;
  
  public boolean enabled;
  
  public boolean visible;
  
  protected boolean hovered;
  
  public GuiButton(int buttonId, int x, int y, String buttonText) {
    this(buttonId, x, y, 200, 20, buttonText);
  }
  
  public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
    this.width = 200;
    this.height = 20;
    this.enabled = true;
    this.visible = true;
    this.id = buttonId;
    this.xPosition = x;
    this.yPosition = y;
    this.width = widthIn;
    this.height = heightIn;
    this.displayString = buttonText;
  }
  
  protected int getHoverState(boolean mouseOver) {
    int i = 1;
    if (!this.enabled) {
      i = 0;
    } else if (mouseOver) {
      i = 2;
    } 
    return i;
  }
  
  public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
    if (this.visible) {
      FontRenderer fontrenderer = p_191745_1_.fontRendererObj;
      p_191745_1_.getTextureManager().bindTexture(BUTTON_TEXTURES);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
      int i = getHoverState(this.hovered);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      if (flag) {
        drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1, 191919, -1879048192);
        drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 65280);
      } else {
        drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1, 11111111, 1610612736);
        drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16764108);
      } 
      mouseDragged(p_191745_1_, p_191745_2_, p_191745_3_);
    } 
  }
  
  public void drawBorderedRect(int x, int y, int x1, int y1, int size, int borderC, int insideC) {
    drawRect(x + size, y + size, x1 - size, y1 - size, insideC);
    drawRect(x + size, y + size, x1, y, borderC);
    drawRect(x, y, x + size, y1 - 1, borderC);
    drawRect(x1, y1 - 1, x1 - size, y + size, borderC);
    drawRect(x, y1 - size, x1, y1, borderC);
  }
  
  protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
  
  public void mouseReleased(int mouseX, int mouseY) {}
  
  public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
    return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
  }
  
  public boolean isMouseOver() {
    return this.hovered;
  }
  
  public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
  
  public void playPressSound(SoundHandler soundHandlerIn) {
    soundHandlerIn.playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
  }
  
  public int getButtonWidth() {
    return this.width;
  }
  
  public void setWidth(int width) {
    this.width = width;
  }
}
