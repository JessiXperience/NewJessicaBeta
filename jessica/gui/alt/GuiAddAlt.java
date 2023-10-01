package jessica.gui.alt;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import com.mojang.realmsclient.gui.ChatFormatting;
import java.io.IOException;
import java.net.Proxy;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import org.lwjgl.input.Keyboard;

public class GuiAddAlt extends GuiScreen {
  private final GuiAltManager manager;
  
  private PasswordField password;
  
  private String status = ChatFormatting.GRAY + "Idle...";
  
  private GuiTextField username;
  
  public GuiAddAlt(GuiAltManager manager) {
    this.manager = manager;
  }
  
  protected void actionPerformed(GuiButton button) {
    AddAltThread login;
    switch (button.id) {
      case 0:
        login = new AddAltThread(this.username.getText(), this.password.getText());
        login.start();
        break;
      case 1:
        this.mc.displayGuiScreen(this.manager);
        break;
    } 
  }
  
  public void drawScreen(int i2, int j2, float f2) {
    drawDefaultBackground();
    this.username.drawTextBox();
    this.password.drawTextBox();
    drawCenteredString(this.fontRendererObj, "Add Alt", width / 2, 20, -1);
    if (this.username.getText().isEmpty())
      drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368); 
    if (this.password.getText().isEmpty())
      drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368); 
    drawCenteredString(this.fontRendererObj, this.status, width / 2, 30, -1);
    super.drawScreen(i2, j2, f2);
  }
  
  public void initGui() {
    Keyboard.enableRepeatEvents(true);
    this.buttonList.clear();
    this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
    this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
    this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
    this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
  }
  
  protected void keyTyped(char par1, int par2) {
    this.username.textboxKeyTyped(par1, par2);
    this.password.textboxKeyTyped(par1, par2);
    if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
      this.username.setFocused(!this.username.isFocused());
      this.password.setFocused(!this.password.isFocused());
    } 
    if (par1 == '\r')
      actionPerformed(this.buttonList.get(0)); 
  }
  
  protected void mouseClicked(int par1, int par2, int par3) {
    try {
      super.mouseClicked(par1, par2, par3);
    } catch (IOException e) {
      e.printStackTrace();
    } 
    this.username.mouseClicked(par1, par2, par3);
    this.password.mouseClicked(par1, par2, par3);
  }
  
  private class AddAltThread extends Thread {
    private final String password;
    
    private final String username;
    
    public AddAltThread(String username, String password) {
      this.username = username;
      this.password = password;
      GuiAddAlt.this.status = ChatFormatting.GRAY + "Idle...";
    }
    
    private final void checkAndAddAlt(String username, String password) throws IOException {
      YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
      YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication)service.createUserAuthentication(Agent.MINECRAFT);
      auth.setUsername(username);
      auth.setPassword(password);
      try {
        auth.logIn();
        AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
        GuiAddAlt.this.status = "Alt added. (" + username + ")";
      } catch (AuthenticationException e) {
        GuiAddAlt.this.status = ChatFormatting.RED + "Alt failed!";
        e.printStackTrace();
      } 
    }
    
    public void run() {
      if (this.password.equals("")) {
        AltManager.registry.add(new Alt(this.username, ""));
        GuiAddAlt.this.status = ChatFormatting.GREEN + "Alt added. (" + this.username + " - offline name)";
        return;
      } 
      GuiAddAlt.this.status = ChatFormatting.YELLOW + "Trying alt...";
      try {
        checkAndAddAlt(this.username, this.password);
      } catch (IOException e) {
        e.printStackTrace();
      } 
    }
  }
}
