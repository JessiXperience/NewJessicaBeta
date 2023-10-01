package jessica.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

import jessica.gui.alt.GuiAltManager;

public class GuiHackTools extends GuiScreen {
  private GuiScreen parentScreen;
  
  public GuiHackTools(GuiScreen guiscreen) {
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
    } else if (guibutton.id == 1) {
      this.mc.displayGuiScreen(new GuiProxy(this.parentScreen));
    } else if (guibutton.id == 2) {
      this.mc.displayGuiScreen(new GuiAltManager(this.parentScreen));
    } else if (guibutton.id == 3) {
      this.mc.displayGuiScreen(new GuiBungeeIpNick(this.parentScreen));
    } else if (guibutton.id == 4) {
      GuiHttpPostBrute ps2 = new GuiHttpPostBrute();
      ps2.setVisible(true);
    } else if (guibutton.id == 7) {
      this.mc.displayGuiScreen(new GuiSpoofUUID(this.parentScreen));
    } else if (guibutton.id == 6) {
      GuiSubdomainBrute ps = new GuiSubdomainBrute();
      ps.setVisible(true);
    } else if (guibutton.id == 8) {
      GuiServerFinder ps4 = new GuiServerFinder();
      ps4.setVisible(true);
    } else if (guibutton.id == 9) {
      GuiPortScanner ps5 = new GuiPortScanner();
      ps5.setVisible(true);
    } else if (guibutton.id == 10) {
      GuiHttpGetBrute ps6 = new GuiHttpGetBrute();
      ps6.setVisible(true);
    } else if (guibutton.id == 4) {
        MD5_Brute ps4 = new MD5_Brute();
        ps4.setVisible(true);
    }
  }
  
  protected void mouseClicked(int i, int j, int k) throws IOException {
    super.mouseClicked(i, j, k);
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(8, this.width / 2 - 100, this.height / 4 - 24 + 12, "Server Finder"));
    this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 - 0 + 12, "Subdomain Brute"));
    this.buttonList.add(new GuiButton(9, this.width / 2 - 100, this.height / 4 + 24 + 12, "PortScanner"));
    this.buttonList.add(new GuiButton(7, this.width / 2 - 100, this.height / 4 + 48 + 12, "Spoof UUID"));
    this.buttonList.add(new GuiButton(10, this.width / 2 - 100, this.height / 4 + 72 + 12, "HTTP/HTTPS GET Method Brute"));
    this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 96 + 12, "HTTP/HTTPS POST Method Accounts Brute"));
    this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 120 + 12, "Bungee Offline UUID Spoof"));
    this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 144 + 12, "AltManager"));
    this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 168 + 12, "Proxy"));
    this.buttonList.add(new GuiButton(11, this.width / 2 - 100, this.height / 4 - 48 + 12, "MD5 Hash Brute"));
    this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 192 + 12, "Cancel"));
  }
  
  public void drawScreen(int i, int j, float f) {
    drawDefaultBackground();
    super.drawScreen(i, j, f);
  }
}
