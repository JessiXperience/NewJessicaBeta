package jessica.managers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.lwjgl.input.Keyboard;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jessica.Wrapper;
import jessica.module.Module;
import jessica.modules.HUD;
import jessica.utils.JsonUtils;
import jessica.value.Value;
import jessica.value.ValueBoolean;
import jessica.value.ValueColor;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.gui.GuiIngame;

public class FileManager {
	public File dir;
    public File modules;
    public File binds;
    public File friends;
    public File values;
    
    public FileManager() {
        this.dir = new File(Wrapper.mc().mcDataDir, "Jessica");
        this.modules = new File(this.dir, "Modules.json");
        this.binds = new File(this.dir, "Binds.json");
        this.friends = new File(this.dir, "Friends.json");
        this.values = new File(this.dir, "Values.json");
        if (!this.dir.exists()) {
            this.dir.mkdir();
        }
    }
    
    public void init() throws Exception {
        this.loadModules();
        this.saveModules();
        this.loadBinds();
        this.saveBinds();
        this.loadFriends();
        this.saveFriends();
        this.loadValues();
        this.saveValues();
    }
    
    public void saveModules() {
        try {
            JsonObject json = new JsonObject();
            for (Module mod : ModuleManager.getModules().values()) {
                JsonObject jsonMod = new JsonObject();
                jsonMod.addProperty("Toggled", Boolean.valueOf(mod.isToggled()));
                json.add(mod.getName(), (JsonElement)jsonMod);
            }
            final PrintWriter save = new PrintWriter(new FileWriter(this.modules));
            save.println(JsonUtils.prettyGson.toJson((JsonElement)json));
            save.close();
        }
        catch (Exception ex) {}
    }
    
    public void loadModules() {
        try {
            if (!this.modules.exists()) {
                this.modules.createNewFile();
            }
            BufferedReader load = new BufferedReader(new FileReader(this.modules));
            JsonObject json = (JsonObject)JsonUtils.jsonParser.parse((Reader)load);
            load.close();
            for (Map.Entry<String, JsonElement> entry : json.entrySet()) {
                Module module = ModuleManager.getModule(entry.getKey());
                if (module != null) {
                    JsonObject jsonModule = (JsonObject)entry.getValue();
                    boolean enabled = jsonModule.get("Toggled").getAsBoolean();
                    module.setToggled(enabled);
                }
            }
        }
        catch (Exception ex) {}
    }
   /*
    public void saveBinds()
    {
    	try
    	{
    		JsonObject json = new JsonObject();
    		for (Module hack : ModuleManager.getModules().values())
    		{
    			if(hack.hasBind()) {
    				JsonObject jsonHack = new JsonObject();
    				jsonHack.addProperty("name", hack.getName());
    				json.add(String.valueOf(hack.getKeyBind()), jsonHack);
    			}
    		}
    		PrintWriter saveJson = new PrintWriter(new FileWriter(this.binds));
    		saveJson.println(JsonUtils.prettyGson.toJson(json));
    		saveJson.close();
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    	}
    }
    */
   
    public void saveBinds() {
        try {
            JsonObject json = new JsonObject();
            for(Module module : ModuleManager.getModules().values()) {
            	JsonObject jsonMod = new JsonObject();
            	jsonMod.addProperty("key", Integer.valueOf(module.getKeyBind()));
            	json.add(module.getName(), (JsonElement)jsonMod);
            }
            PrintWriter save = new PrintWriter(new FileWriter(this.binds));
            save.println(JsonUtils.prettyGson.toJson((JsonElement)json));
            save.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
   
    public void loadBinds() {
        try {
            if(!this.binds.exists()) {
                this.binds.createNewFile();
            }
            BufferedReader load = new BufferedReader(new FileReader(this.binds));
            JsonObject json = (JsonObject)JsonUtils.jsonParser.parse((Reader)load);
            load.close();
            for(Map.Entry<String, JsonElement> entry : json.entrySet()) {
            	Module hack = ModuleManager.getModule(entry.getKey());
            	if(hack != null) {
            		JsonObject jsonModule = (JsonObject)entry.getValue();
            		hack.setKeyBind(jsonModule.get("key").getAsInt());
            	}
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void loadFriends() {
        try {
            if (!this.friends.exists()) {
                this.friends.createNewFile();
            }
            BufferedReader load = new BufferedReader(new FileReader(this.friends));
            ArrayList<String> friends = (ArrayList<String>)JsonUtils.gson.fromJson((Reader)load, (Class)ArrayList.class);
            if (friends != null) {
                FriendManager.setFriends(friends);
            }
            load.close();
        }
        catch (Exception ex) {}
    }
    
    public void saveFriends() {
        try {
            PrintWriter save = new PrintWriter(new FileWriter(this.friends));
            save.println(JsonUtils.prettyGson.toJson((Object)FriendManager.getFriends()));
            save.close();
        }
        catch (Exception ex) {}
    }
    
    public static void write(File file, String text) {
        try {
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            else if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file, true);
            fw.write(String.valueOf(String.valueOf(text)) + System.getProperty("line.separator"));
            fw.close();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void saveValues() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter(this.values));
     //       writer.print("TotalTime:" + GuiIngame.tick + "\n");
            for (final Module m : ModuleManager.getModules().values()) {
                for (final Value v : m.getValues()) {
                    writer.println(String.valueOf(m.getName()) + ":" + v.getName() + ":" + v.getValue());
                    writer.flush();
                }
            }
            writer.close();
        }
        catch (IOException ex) {}
    }
    
    public void loadValues() {
        try {
            if (!this.values.exists()) {
                try {
                    this.values.createNewFile();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            BufferedReader load = new BufferedReader(new FileReader(this.values));
            String str = "";
            try {
                while ((str = load.readLine()) != null) {
                    if (str.startsWith("#")) {
                        break;
                    }
                    String name = str.split(":")[0];
                    String value = str.split(":")[1];
     //               if (name.equalsIgnoreCase("TotalTime")) {
     //               	GuiIngame.tick = Integer.parseInt(value);
     //               }
                    for (Module m : ModuleManager.getModules().values()) {
                        if (m.getName().equalsIgnoreCase(name)) {
                            for (Value v : m.getValues()) {
                                if (v.getName().equalsIgnoreCase(value)) {
                                    if (v instanceof ValueBoolean) {
                                        ValueBoolean vB = (ValueBoolean)v;
                                        vB.setValue(Boolean.parseBoolean(str.split(":")[2]));
                                    }
                                    if (v instanceof ValueNumber) {
                                    	ValueNumber vD = (ValueNumber)v;
                                        vD.setValue(Double.parseDouble(str.split(":")[2]));
                                    }
                                    if(v instanceof ValueMode) {
                                    	ValueMode vM = (ValueMode)v;
                                    	vM.setValue(str.split(":")[2]);
                                    }
                                    if(v instanceof ValueColor) {
                                    	ValueColor vC = (ValueColor)v;
                                    	vC.setValue(Integer.parseInt(str.split(":")[2]));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            catch (IOException ex) {}
        }
        catch (FileNotFoundException ex2) {}
    }
    
    public static String getClientDir() {
        String dir = Wrapper.mc().mcDataDir + "//Jessica//";
        File filedir = new File(dir);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        return dir;
    }
}
