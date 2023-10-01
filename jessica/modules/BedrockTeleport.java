package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import net.minecraft.network.play.client.CPacketVehicleMove;

public class BedrockTeleport extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Bypass"});
	ValueBoolean onlyDamage = new ValueBoolean("Only damage", false);
	
	public BedrockTeleport() {
		super("BedrockClip", Category.EXPLOIT);
		addValue(mode);
		addValue(onlyDamage);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        if (this.mode.getValue().equalsIgnoreCase("Bypass") /*&& bB.a()*/) {
            Wrapper.sendPacket(new CPacketVehicleMove());
            if (Wrapper.player().hurtTime > 0) {
                this.toggle();
            }
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
    	if (onlyDamage.getValue() && mode.getValue().equalsIgnoreCase("Default")) {     
    		if (Wrapper.player().hurtTime > 0) {
    			Wrapper.player().setPosition(Wrapper.player().posX, -2.0, Wrapper.player().posZ);
    			if (Wrapper.player().hurtTime < 2) {
    				this.setToggled(false);
    			}
    		}
    	}
    	super.onPlayerTick(playerTickEvent);
    }

    @Override
    public void onEnable() {
        if (!this.onlyDamage.getValue() && this.mode.getValue().equalsIgnoreCase("Default")) {
        	Wrapper.player().setPosition(Wrapper.player().posX, -2.0, Wrapper.player().posZ);
        	this.setToggled(false);
        }
        super.onEnable();
    }
}
