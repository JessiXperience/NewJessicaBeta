package jessica.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLanguage;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiWorldSelection;
import net.minecraft.client.resources.I18n;

import org.lwjgl.input.Keyboard;

public class GuiContacts extends GuiScreen {
  private GuiScreen parentScreen;
  
  public GuiContacts(GuiScreen guiscreen) {
    this.parentScreen = guiscreen;
  }
  
  public void onGuiClosed() {
    Keyboard.enableRepeatEvents(false);
  }
  
  protected void actionPerformed(GuiButton guibutton) {
    if (!guibutton.enabled)
      return; 
    if (guibutton.id == 0) {
      this.mc.displayGuiScreen(this.parentScreen);
    }
    if (guibutton.id == 1)
    	try {
    	    Desktop.getDesktop().browse(new URL("https://github.com/JessiXperience").toURI());
    	} catch (Exception e) {}
    if (guibutton.id == 2)
    	try {
    	    Desktop.getDesktop().browse(new URL("https://youtube.com/There_will_be_the_NewJessica_youtube_channel").toURI());
    	} catch (Exception e) {}
      if (guibutton.id == 3)
    	  try {
      	    Desktop.getDesktop().browse(new URL("https://t.me/JessiXperience").toURI());
      	} catch (Exception e) {}
      if (guibutton.id == 4)
    	  try {
      	    Desktop.getDesktop().browse(new URL("mailto:JessiXperience@riseup.net").toURI());
      	} catch (Exception e) {}
  }
  
  protected void mouseClicked(int i, int j, int k) throws IOException {
    super.mouseClicked(i, j, k);
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 6 + 88, "Github"));
    this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 6 + 110, "YouTube"));
    
    this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 6 + 132, "Telegram"));
    this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 6 + 154, "Email"));
    
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 162, "Exit"));
  }
  
  public void drawScreen(int i, int j, float f) {
    drawDefaultBackground();
    drawCenteredString(this.fontRendererObj, "§lContacts", this.width / 2, this.height / 4 - 20 + 20, 16777215);
    super.drawScreen(i, j, f);
  }
  
}
