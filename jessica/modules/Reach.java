package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.RaytraceUtils;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;

public class Reach extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Packet"});
	ValueNumber distance = new ValueNumber("Distance", 4.5, 0.1, 6.0, 0.1);
	ValueNumber packetDistance = new ValueNumber("Packet distance", 8.0, 3.0, 100.0, 1.0);
	
	public Reach() {
		super("Reach", Category.COMBAT);
		addValue(mode);
		addValue(distance);
		addValue(packetDistance);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        KeyBinding keyBinding = Wrapper.mc().gameSettings.keyBindAttack;
        if (!keyBinding.isKeyDown()) {
            return;
        }
        Entity entity = RaytraceUtils.b(setReach());
        if (entity == null || entity != null && this.a(entity)) {
            return;
        }
        if (this.mode.getValue().equalsIgnoreCase("Packet")) {
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            double d2 = entity.posX - 3.5 * Math.cos(Math.toRadians(RaytraceUtils.getYaw(entity) + 90.0f));
            double d3 = entity.posZ - 3.5 * Math.sin(Math.toRadians(RaytraceUtils.getYaw(entity) + 90.0f));
            Wrapper.sendPacket((Packet)new CPacketPlayer.PositionRotation(d2, entity.posY, d3, RaytraceUtils.getYaw(entity), RaytraceUtils.b(entity), entityPlayerSP.onGround));
            Wrapper.sendPacket((Packet)new CPacketUseEntity(entity));
            Wrapper.sendPacket((Packet)new CPacketPlayer.Position(entityPlayerSP.posX, entityPlayerSP.posY, entityPlayerSP.posZ, entityPlayerSP.onGround));
        } else {
        	Wrapper.mc().playerController.attackEntity(Wrapper.player(), entity);
        }
        Wrapper.player().swingArm(EnumHand.MAIN_HAND);
        KeyBinding.setKeyBindState(keyBinding.getKeyCode(), false);
        super.onClientTick(clientTickEvent);
    }

    public boolean a(Entity entity) {
        return entity.getDistanceToEntity(Wrapper.player()) <= Wrapper.mc().playerController.getBlockReachDistance() - 1.5;
    }
    
    public double setReach() {
        double d2 = Wrapper.mc().playerController.getBlockReachDistance() - 1.5;
        if (this.isToggled()) {
            d2 = this.mode.getValue().equalsIgnoreCase("Packet") ? this.packetDistance.getDoubleValue() : this.distance.getDoubleValue();
        }
        return d2;
    }
}
