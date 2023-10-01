package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketChatMessage;

public class AutoRespawn extends Module{	
	ValueBoolean instant = new ValueBoolean("instant", false);
	ValueBoolean autoHome = new ValueBoolean("AutoHome", false);
	TimerUtils timer = new TimerUtils();
	public boolean readyTeleport;
	
	public AutoRespawn() {
		super("AutoRespawn", Category.PLAYER);
		addValue(instant);
		addValue(autoHome);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.instant.getValue() ? entityPlayerSP.getHealth() != 0.0f && !entityPlayerSP.isDead : !(Wrapper.mc().currentScreen instanceof GuiGameOver)) {
            return;
        }
        entityPlayerSP.respawnPlayer();
        Wrapper.mc().displayGuiScreen(null);
        if (this.autoHome.getValue() && !this.readyTeleport) {
            Wrapper.sendPacket((Packet)new CPacketChatMessage("/home home"));
            this.readyTeleport = true;
        }
        if (this.readyTeleport && this.timer.check(200L)) {
            this.readyTeleport = false;
            this.timer.reset();
        }
        super.onClientTick(clientTickEvent);
    }
	
}
