package jessica.modules;

import org.lwjgl.input.Mouse;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.InventoryUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.util.EnumHand;

public class ClickPearl extends Module{
	ValueBoolean middle = new ValueBoolean("Middle", true);
	public TimerUtils timer = new TimerUtils();
	
	public ClickPearl() {
		super("ClickPearl", Category.COMBAT);
		addValue(middle);
	}
	
	@Override
    public void onEnable() {
        if (this.middle.getValue()) {
            return;
        }
        this.b();
        this.setToggled(false);
        super.onEnable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
    	if (!this.middle.getValue()) {
            return;
        }
        if (Mouse.isButtonDown((int)2) && this.timer.check(200L) && Wrapper.mc().currentScreen == null) {
            this.b();
            this.timer.reset();
        }
        super.onClientTick(clientTickEvent);
    }

    public void b() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        int pearlSlot = InventoryUtils.getHotbarItem(Items.ENDER_PEARL);
        if (pearlSlot != -1) {
            Wrapper.sendPacket((Packet)new CPacketHeldItemChange(pearlSlot));
            Wrapper.mc().playerController.processRightClick(entityPlayerSP, Wrapper.world(), EnumHand.MAIN_HAND);
            new Thread() {
            	@Override
            	public void run() {
            		try {
            			Thread.sleep(400L);
            			Wrapper.sendPacket((Packet)new CPacketHeldItemChange(entityPlayerSP.inventory.currentItem));
            		}
                    catch (Exception ex) {
                    	ex.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
