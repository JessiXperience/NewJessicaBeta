package jessica.gui;

import java.awt.Color;

import jessica.Wrapper;
import jessica.font.CFontRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class GuiCustomButton extends GuiButton {

  public GuiCustomButton(int buttonId, int x, int y, int i, int j, String buttonText) {
	  super(buttonId, x, y, i, j, buttonText);
  }

  @Override
  public void func_191745_a(Minecraft p_191745_1_, int p_191745_2_, int p_191745_3_, float p_191745_4_) {
  if (this.visible) {
      CFontRenderer fontrenderer = Wrapper.FONT_MANAGER.JetBrLight;
      p_191745_1_.getTextureManager().bindTexture(BUTTON_TEXTURES);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      boolean flag = (p_191745_2_ >= this.xPosition && p_191745_3_ >= this.yPosition && p_191745_2_ < this.xPosition + this.width && p_191745_3_ < this.yPosition + this.height);
      int i = getHoverState(this.hovered);
      GlStateManager.enableBlend();
      GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
      GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
      if (flag) {
        drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1, 191919, -1879048192);
        drawCenteredStringWithFont(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, new Color(255, 128, 0).getRGB());
      } else {
        drawBorderedRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 1, 11111111, 1610612736);
        drawCenteredStringWithFont(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, 16764108);
      } 
      mouseDragged(p_191745_1_, p_191745_2_, p_191745_3_);
    } 
  }

}
