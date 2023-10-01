package jessica;

import java.awt.FontFormatException;
import java.io.IOException;

import jessica.commands.ChatCommands;
import jessica.events.EventManager;
import jessica.font.FontManager;
import jessica.managers.FileManager;
import jessica.managers.ModuleManager;
import jessica.utils.HackPack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.util.text.TextComponentTranslation;
import viamcp.ViaMCP;

public class Wrapper {
	private static ModuleManager modulemanager;
	public static FontManager FONT_MANAGER = new FontManager();
	private static FileManager filemanager;
	private static ChatCommands chathandler;
	private static HackPack hackpack;
	public static EventManager eventManager;
    private static String clientname = "NewJessica 5.0.2 beta"; // Server Resolver
	
	public Wrapper() throws FontFormatException, IOException {
		modulemanager = new ModuleManager();
		filemanager = new FileManager();
		chathandler = new ChatCommands();
		hackpack = new HackPack();
		FONT_MANAGER.loadFonts();
		eventManager = new EventManager();
		try {
            Wrapper.filemanager.init();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
		try
		{
		  ViaMCP.getInstance().start();
		}
		catch (Exception e)
		{
		  e.printStackTrace();
		}
	}
	
	public static FileManager getFiles() {
        return Wrapper.filemanager;
    }
	
	public static ChatCommands getChatHandler() {
        return Wrapper.chathandler;
    }
	
	public static Minecraft mc() {
        return Minecraft.getMinecraft();
    }
    
    public static WorldClient world() {
        return mc().world;
    }
    
    public static EntityPlayerSP player() {
        return mc().player;
    }
    
    public static void sendPacket(Packet p) {
        player().connection.sendPacket(p);
    }
    
    public static void msg(String s, final boolean prefix) {
        s = String.valueOf(prefix ? new StringBuilder("&f&l[&4&l").append(getClientName()).append("&f&l] &r").toString() : "") + s;
        player().addChatMessage(new TextComponentTranslation(s.replace("&", "§"), new Object[0]));
    }
	
    public static String getClientName() {
        return Wrapper.clientname;
    }
    public static HackPack getHackPack() {
    	return hackpack;
    }
}
