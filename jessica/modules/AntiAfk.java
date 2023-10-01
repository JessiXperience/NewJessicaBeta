package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.RandomUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class AntiAfk extends Module{
	ValueMode mode = new ValueMode("Mode", "Command", new String[] {"Jump", "Command"});
	ValueNumber delay = new ValueNumber("Delay in second", 10.0, 1.0, 100.0, 1.0);
	public TimerUtils timer = new TimerUtils();
	
	public AntiAfk() {
		super("AntiAfk", Category.PLAYER);
		addValue(mode);
		addValue(delay);
	}
	
	@Override
	public void onEnable() {
		if (Wrapper.mc().isSingleplayer()) {
			Wrapper.msg(this.getName() + " only works in multiplayer!", shown);
			setToggled(false);
		}
		super.onEnable();
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (Wrapper.mc().isSingleplayer()) {
            return;
        }
        if (timer.check(1000L * (float)this.delay.getDoubleValue())) {
            if (this.mode.getValue().equalsIgnoreCase("Jump")) {
                Wrapper.player().jump();
            } else if (this.mode.getValue().equalsIgnoreCase("Command")) {
                Wrapper.sendPacket(new CPacketChatMessage("/" + RandomUtils.randomString(6)));
            }
            timer.reset();
        }
        super.onClientTick(clientTickEvent);
    }
	
}
