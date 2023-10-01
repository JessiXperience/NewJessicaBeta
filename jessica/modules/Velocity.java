package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.server.SPacketEntityVelocity;

public class Velocity extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "AAC", "Matrix"});
	
	public Velocity() {
		super("Velocity", Category.COMBAT);
		addValue(mode);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.mode.getValue().equalsIgnoreCase("AAC")) {
            if (entityPlayerSP.hurtTime > 0 && entityPlayerSP.hurtTime <= 7) {
                entityPlayerSP.motionX *= 0.5;
                entityPlayerSP.motionZ *= 0.5;
            }
            if (entityPlayerSP.hurtTime > 0 && entityPlayerSP.hurtTime < 6) {
                entityPlayerSP.motionX = 0.0;
                entityPlayerSP.motionZ = 0.0;
            }
            if (entityPlayerSP.hurtTime > 0 && entityPlayerSP.hurtTime < 1) {
                entityPlayerSP.motionY = -0.4;
            }
        } else if (this.mode.getValue().equalsIgnoreCase("Matrix") && entityPlayerSP.hurtTime >= 9) {
            entityPlayerSP.motionX = 0.0;
            entityPlayerSP.motionY /= 2.0;
            entityPlayerSP.motionZ = 0.0;
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        SPacketEntityVelocity sPacketEntityVelocity;
        return !(object instanceof SPacketEntityVelocity)
        		|| !this.mode.getValue().equalsIgnoreCase("Default");
        		//|| (sPacketEntityVelocity = (SPacketEntityVelocity)object).getEntityID() != Wrapper.player().getEntityId();
    }	
}
