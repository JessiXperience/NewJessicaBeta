package jessica.gui.alt;

import com.mojang.realmsclient.gui.ChatFormatting;

import jessica.utils.RenderUtils;

import java.io.IOException;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiAltManager extends GuiScreen {
  private GuiButton login;
  
  private GuiButton remove;
  
  private GuiButton rename;
  
  private AltLoginThread loginThread;
  
  private int offset;
  
  public Alt selectedAlt = null;
  
  private String status = ChatFormatting.GRAY + "No alts selected";
  
  private final GuiScreen parentScreen;
  
  public GuiAltManager(GuiScreen parentScreen) {
    this.parentScreen = parentScreen;
  }
  
  public void actionPerformed(GuiButton button) throws IOException {
    String user;
    AltManager altManager;
    String pass;
    switch (button.id) {
      case 0:
        if (this.loginThread == null) {
          this.mc.displayGuiScreen(this.parentScreen);
          break;
        } 
        if (!this.loginThread.getStatus().equals(ChatFormatting.YELLOW + "Attempting to log in") && !this.loginThread.getStatus().equals(ChatFormatting.RED + "Do not hit back!" + ChatFormatting.YELLOW + " Logging in...")) {
          this.mc.displayGuiScreen(this.parentScreen);
          break;
        } 
        this.loginThread.setStatus(ChatFormatting.RED + "Failed to login! Please try again!" + ChatFormatting.YELLOW + " Logging in...");
        break;
      case 1:
        user = this.selectedAlt.getUsername();
        pass = this.selectedAlt.getPassword();
        this.loginThread = new AltLoginThread(user, pass);
        this.loginThread.start();
        break;
      case 2:
        if (this.loginThread != null)
          this.loginThread = null; 
        AltManager.registry.remove(this.selectedAlt);
        this.status = "Removed.";
        this.selectedAlt = null;
        break;
      case 3:
        this.mc.displayGuiScreen(new GuiAddAlt(this));
        break;
      case 4:
        this.mc.displayGuiScreen(new GuiAltLogin(this));
        break;
      case 6:
        this.mc.displayGuiScreen(new GuiRenameAlt(this));
        break;
    } 
  }
  
  public void drawScreen(int par1, int par2, float par3) {
    if (Mouse.hasWheel()) {
      int wheel = Mouse.getDWheel();
      if (wheel < 0) {
        this.offset += 26;
        if (this.offset < 0)
          this.offset = 0; 
      } else if (wheel > 0) {
        this.offset -= 26;
        if (this.offset < 0)
          this.offset = 0; 
      } 
    } 
    drawDefaultBackground();
    drawString(this.fontRendererObj, this.mc.session.getUsername(), 10, 10, -7829368);
    FontRenderer fontRendererObj = this.fontRendererObj;
    StringBuilder sb2 = new StringBuilder("Account Manager - ");
    drawCenteredString(fontRendererObj, sb2.append(AltManager.registry.size()).append(" alts").toString(), width / 2, 10, -1);
    drawCenteredString(this.fontRendererObj, (this.loginThread == null) ? this.status : this.loginThread.getStatus(), width / 2, 20, -1);
    RenderUtils.drawBorderedRect(50.0D, 33.0D, (width - 50), (height - 50), 1.0D, -16777216, -2147483648);
    GL11.glPushMatrix();
    prepareScissorBox(0.0F, 33.0F, width, (height - 50));
    GL11.glEnable(3089);
    int y2 = 38;
    for (Alt alt2 : AltManager.registry) {
      if (!isAltInArea(y2))
        continue; 
      String name = alt2.getMask().equals("") ? alt2.getUsername() : alt2.getMask();
      String pass = alt2.getPassword().equals("") ? "Cracked": alt2.getPassword().replaceAll(".", "*");
      if (alt2 == this.selectedAlt) {
        if (isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
          RenderUtils.drawBorderedRect(52.0D, (y2 - this.offset - 4), (width - 52), (y2 - this.offset + 20), 1.0D, -16777216, -2142943931);
        } else if (isMouseOverAlt(par1, par2, y2 - this.offset)) {
          RenderUtils.drawBorderedRect(52.0D, (y2 - this.offset - 4), (width - 52), (y2 - this.offset + 20), 1.0D, -16777216, -2142088622);
        } else {
          RenderUtils.drawBorderedRect(52.0D, (y2 - this.offset - 4), (width - 52), (y2 - this.offset + 20), 1.0D, -16777216, -2144259791);
        } 
      } else if (isMouseOverAlt(par1, par2, y2 - this.offset) && Mouse.isButtonDown(0)) {
    	  RenderUtils.drawBorderedRect(52.0D, (y2 - this.offset - 4), (width - 52), (y2 - this.offset + 20), 1.0D, -16777216, -2146101995);
      } else if (isMouseOverAlt(par1, par2, y2 - this.offset)) {
    	  RenderUtils.drawBorderedRect(52.0D, (y2 - this.offset - 4), (width - 52), (y2 - this.offset + 20), 1.0D, -16777216, -2145180893);
      } 
      drawCenteredString(this.fontRendererObj, name, width / 2, y2 - this.offset, -1);
      drawCenteredString(this.fontRendererObj, pass, width / 2, y2 - this.offset + 10, 5592405);
      y2 += 26;
    } 
    GL11.glDisable(3089);
    GL11.glPopMatrix();
    super.drawScreen(par1, par2, par3);
    if (this.selectedAlt == null) {
      this.login.enabled = false;
      this.remove.enabled = false;
      this.rename.enabled = false;
    } else {
      this.login.enabled = true;
      this.remove.enabled = true;
      this.rename.enabled = true;
    } 
    if (Keyboard.isKeyDown(200)) {
      this.offset -= 26;
      if (this.offset < 0)
        this.offset = 0; 
    } else if (Keyboard.isKeyDown(208)) {
      this.offset += 26;
      if (this.offset < 0)
        this.offset = 0; 
    } 
  }
  
  public void initGui() {
    this.buttonList.add(new GuiButton(0, width / 2 + 4 + 50, height - 24, 100, 20, "Cancel"));
    this.login = new GuiButton(1, width / 2 - 154, height - 48, 100, 20, "Login");
    this.buttonList.add(this.login);
    this.remove = new GuiButton(2, width / 2 - 154, height - 24, 100, 20, "Remove");
    this.buttonList.add(this.remove);
    this.buttonList.add(new GuiButton(3, width / 2 + 4 + 50, height - 48, 100, 20, "Add"));
    this.buttonList.add(new GuiButton(4, width / 2 - 50, height - 48, 100, 20, "Direct Login"));
    this.rename = new GuiButton(6, width / 2 - 50, height - 24, 100, 20, "Edit");
    this.buttonList.add(this.rename);
    this.login.enabled = false;
    this.remove.enabled = false;
    this.rename.enabled = false;
  }
  
  private boolean isAltInArea(int y2) {
    return (y2 - this.offset <= height - 50);
  }
  
  private boolean isMouseOverAlt(int x2, int y2, int y1) {
    return (x2 >= 52 && y2 >= y1 - 4 && x2 <= width - 52 && y2 <= y1 + 20 && x2 >= 0 && y2 >= 33 && x2 <= width && y2 <= height - 50);
  }
  
  protected void mouseClicked(int par1, int par2, int par3) throws IOException {
    if (this.offset < 0)
      this.offset = 0; 
    int y2 = 38 - this.offset;
    for (Alt alt2 : AltManager.registry) {
      if (isMouseOverAlt(par1, par2, y2)) {
        if (alt2 == this.selectedAlt) {
          actionPerformed(this.buttonList.get(1));
          return;
        } 
        this.selectedAlt = alt2;
      } 
      y2 += 26;
    } 
    try {
      super.mouseClicked(par1, par2, par3);
    } catch (IOException e) {
      e.printStackTrace();
    } 
  }
  
  public void prepareScissorBox(float x2, float y2, float x22, float y22) {
    ScaledResolution scale = new ScaledResolution(this.mc);
    int factor = scale.getScaleFactor();
    GL11.glScissor((int)(x2 * factor), (int)((scale.getScaledHeight() - y22) * factor), (int)((x22 - x2) * factor), (int)((y22 - y2) * factor));
  }
}
