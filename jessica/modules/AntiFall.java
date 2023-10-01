package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.CPacketUtils;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;

public class AntiFall extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Matrix", "Matrix2"});
	public boolean falling;
	
	public AntiFall() {
		super("AntiFall", Category.PLAYER);
		this.addValue(mode);
	}
	
	@Override
    public void onDisable() {
        if (this.mode.getValue().equalsIgnoreCase("Matrix") && this.falling) {
            this.falling = false;
        }
        super.onDisable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.mode.getValue().equalsIgnoreCase("Default")) {
            if (entityPlayerSP.fallDistance > 2.5f) {
                Wrapper.sendPacket((Packet)new CPacketPlayer(true));
            }
        } else if (this.mode.getValue().equalsIgnoreCase("Matrix2")) {
            if (entityPlayerSP.fallDistance > 2.5f && entityPlayerSP.fallDistance < 10.0f) {
                CPacketUtils.setGround(true);
            } else if (entityPlayerSP.onGround) {
            	CPacketUtils.inAir();
            }
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (!this.mode.getValue().equalsIgnoreCase("Matrix")) {
            return;
        }
        if (!Wrapper.player().onGround && Wrapper.player().fallDistance > 2.0f && Wrapper.world().getBlockState(Wrapper.player().getPosition().down()).getBlock() == Blocks.AIR) {
            this.falling = true;
        }
        if (this.falling) {
            Wrapper.player().onGround = false;
        }
        super.onPlayerTick(playerTickEvent);
    }
}
