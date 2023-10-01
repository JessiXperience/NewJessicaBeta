package jessica.gui.alt;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

public class AltHelper {
  private final Minecraft mc = Minecraft.getMinecraft();
  
  public AltHelper(Minecraft mc) {}
  
  public static void setusername(String username) {
    (Minecraft.getMinecraft()).session = new Session(username, "", "", "mojang");
  }
}
