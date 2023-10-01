package jessica.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import jessica.Wrapper;
import jessica.clickgui.elements.NumberElement;
import jessica.events.ClientTickEvent;
import jessica.events.InputUpdateEvent;
import jessica.events.LivingJumpEvent;
import jessica.events.PlayerTickEvent;
import jessica.events.RenderGameOverlayEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.modules.AirJump;
import jessica.modules.Ambience;
import jessica.modules.AntiAfk;
import jessica.modules.AntiBadEffects;
import jessica.modules.AntiFall;
import jessica.modules.AntiWeb;
import jessica.modules.AutoArmor;
import jessica.modules.AutoRespawn;
import jessica.modules.AutoSprint;
import jessica.modules.AutoStep;
import jessica.modules.AutoTool;
import jessica.modules.AutoTotem;
import jessica.modules.BedFucker;
import jessica.modules.BedrockTeleport;
import jessica.modules.Blackout;
import jessica.modules.BowAimBot;
import jessica.modules.CameraClip;
import jessica.modules.ChestESP;
import jessica.modules.ChestStealer;
import jessica.modules.ChinaHat;
import jessica.modules.ClickPearl;
import jessica.modules.Criticals;
import jessica.modules.Crosshair;
import jessica.modules.CustomChat;
import jessica.modules.ESP;
import jessica.modules.FastLadder;
import jessica.modules.GuiWalk;
import jessica.modules.HUD;
import jessica.modules.HitBox;
import jessica.modules.ItemESP;
import jessica.modules.JumpCircle;
import jessica.modules.KillAura;
import jessica.modules.NightVision;
import jessica.modules.NoInteract;
import jessica.modules.NoJumpDelay;
import jessica.modules.NoSlow;
import jessica.modules.NoVisualBlock;
import jessica.modules.Optimization;
import jessica.modules.Parkour;
import jessica.modules.PlayerRadar;
import jessica.modules.Profiler;
import jessica.modules.Reach;
import jessica.modules.Strafe;
import jessica.modules.SwingAnimate;
import jessica.modules.TargetStrafe;
import jessica.modules.Targets;
import jessica.modules.Timer;
import jessica.modules.TotemCounter;
import jessica.modules.Tracers;
import jessica.modules.XCarry;
import jessica.modules.Zoom;
import jessica.utils.CPacketUtils;
import jessica.utils.ConnectionUtils;
import jessica.utils.EntityUtils;
import jessica.modules.Trails;
import jessica.modules.Trajectories;
import jessica.modules.UserInterface;
import jessica.modules.Velocity;
import jessica.modules.ViewModel;
import jessica.modules.WaterSpeed;

public class ModuleManager {
	public static TreeMap<String, Module> modules = new TreeMap<String, Module>();
	
	public ModuleManager() {
		addMod(new ChinaHat());
		addMod(new HUD());
		addMod(new Trails());
		addMod(new UserInterface());
		addMod(new XCarry());
		addMod(new Targets());
		addMod(new Tracers());
		addMod(new NoJumpDelay());
		addMod(new Parkour());
		addMod(new AutoStep());
		addMod(new AntiWeb());
		addMod(new AutoSprint());
		addMod(new WaterSpeed());
		addMod(new FastLadder());
		addMod(new AutoRespawn());
		addMod(new Timer());
		addMod(new Criticals());
		addMod(new KillAura());
		addMod(new AirJump());
		addMod(new HitBox());
		addMod(new Velocity());
		addMod(new GuiWalk());
		addMod(new AutoArmor());
		addMod(new AutoTool());
		addMod(new NoInteract());
		addMod(new ViewModel());
		addMod(new Strafe());
		addMod(new CustomChat());
		addMod(new AntiBadEffects());
		addMod(new NoVisualBlock());
		addMod(new Zoom());
		addMod(new AntiAfk());
		addMod(new ChestStealer());
		addMod(new Reach());
		addMod(new ClickPearl());
		addMod(new Optimization());
		addMod(new Profiler());
		addMod(new ESP());
		addMod(new ItemESP());
		addMod(new Ambience());
		addMod(new PlayerRadar());
		addMod(new ChestESP());
		addMod(new NightVision());
		addMod(new BedFucker());
		addMod(new Blackout());
		addMod(new AntiFall());
		addMod(new CameraClip());
		//addMod(new Trajectories());
		addMod(new AutoTotem());
		addMod(new NoSlow());
		addMod(new JumpCircle());
		addMod(new TotemCounter());
		addMod(new TargetStrafe());
		addMod(new SwingAnimate());
		addMod(new BedrockTeleport());
		addMod(new BowAimBot());
		addMod(new Crosshair());
	}
	
	public static List<Module> getByCategory(Category category) {
		List<Module> list = new ArrayList<>();
		getModules().forEach((k, m) -> {
			if (m.getCategory() == category)
				list.add(m);
		});
		return list;
	}
	
	private static void addMod(final Module m) {
        if (modules.get(m.getAlias()) == null) {
            modules.put(m.getAlias(), m);
        }
    }
	
	public static TreeMap<String, Module> getModules() {
        return modules;
    }
	
	public static Module getModule(String alias) {
        return getModules().get(alias.toLowerCase().replace(" ", ""));
    }
	
	public static void keyPress(int key) {
		if (Wrapper.mc().currentScreen != null) {
            return;
        }
        for (Module m : getModules().values()) {
            if (m.getKeyBind() == key) {
                m.toggle();
            	//Wrapper.player().sendChatMessage(".toggle " + m.getName());
            }
        }
    }
	
	public static void onRenderGameOverlay(float partilTicks) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onRenderGameOverlay(partilTicks);
			}
		}
	}
	
	public static void onRenderWorldLastEvent(RenderWorldLastEvent event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onRenderWorldLastEvent(event);
				for (Object e : Wrapper.world().getLoadedEntityList()) {
	                m.onRenderWorldLastEvent(event, e);
	            }
			}
		}
	}
	
	public static void onPlayerTick(PlayerTickEvent event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onPlayerTick(event);
			}
		}
	}
	
	public static void onClientTick(ClientTickEvent event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onClientTick(event);
			}
		}
	}
	
	public static void onInputUpdate(InputUpdateEvent event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onInputUpdate(event);
			}
		}
	}
	
	public static void onLivingJump(LivingJumpEvent event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			if (m.isToggled()) {
				m.onJumpEvent(event);
			}
		}
	}
	
	public static boolean sendPacketBypass(Object object, ConnectionUtils.Side side) {
        boolean suc = true;

        CPacketUtils.packet(object, side);
        for (Module m : getModules().values()) {
            if (!m.isToggled()) continue;
            suc &= m.onPacket(object, side);
        }
        return suc;
    }
	
	public static void onSliderChange(NumberElement event) {
		if(EntityUtils.nullCheck()) {
			return;
		}
		for (Module m : getModules().values()) {
			m.onSliderChange(event);
		}
	}
}
