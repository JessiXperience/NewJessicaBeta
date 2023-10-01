package jessica.modules;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import jessica.utils.RandomUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiShulkerBox;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.network.play.server.SPacketWindowItems;

public class ChestStealer extends Module{
	ValueNumber minDelay = new ValueNumber("Min delay", 50, 10, 350, 10);
	ValueNumber maxDelay = new ValueNumber("Max delay", 100, 10, 350, 10);
	ValueBoolean close = new ValueBoolean("Close", true);
	public SPacketWindowItems packet;
	public double randomDelay;
	public TimerUtils timer = new TimerUtils();
	
	public ChestStealer() {
		super("ChestStealer", Category.WORLD);
		addValue(minDelay);
		addValue(maxDelay);
		addValue(close);
	}
	
	@Override
    public void onEnable() {
        this.randomDelay = RandomUtils.randomDouble(this.minDelay.getDoubleValue(), this.maxDelay.getDoubleValue());
        super.onEnable();
    }

    @Override
    public void onDisable() {
        this.packet = null;
    }

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        if (side == ConnectionUtils.Side.IN && object instanceof SPacketWindowItems) {
            this.packet = (SPacketWindowItems)object;
        }
        return true;
    }

    @Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.packet != null && entityPlayerSP.openContainer.windowId == this.packet.getWindowId() && this.b()) {
            if (!this.isContainerEmpty(entityPlayerSP.openContainer)) {
                for (int i = 0; i < entityPlayerSP.openContainer.inventorySlots.size() - 36; ++i) {
                    Slot slot = entityPlayerSP.openContainer.getSlot(i);
                    if (slot.getHasStack() && slot.getStack() != null && this.timer.check((long)randomDelay)) {
                    	this.quickItem(entityPlayerSP.openContainer.windowId, i);
                    }
                }
            } else {
                if (this.close.getValue()) {
                	Wrapper.player().closeScreen();
                }
                if (this.packet != null) {
                    this.packet = null;
                }
            }
        }
        super.onPlayerTick(playerTickEvent);
    }

    public boolean b() {
        return Wrapper.mc().currentScreen instanceof GuiShulkerBox || Wrapper.mc().currentScreen instanceof GuiChest;
    }

    public void quickItem(int windowId, int slot) {
    	Wrapper.mc().playerController.windowClick(windowId, slot, 1, ClickType.QUICK_MOVE, Wrapper.player());
        this.timer.reset();
        this.randomDelay = RandomUtils.randomDouble(this.minDelay.getDoubleValue(), this.maxDelay.getDoubleValue());
    }

    public boolean isContainerEmpty(Container container) {
        boolean temp = true;
        for(int i = 0, slotAmount = container.inventorySlots.size() == 90 ? 54 : 35; i < slotAmount; i++) {
            if(container.getSlot(i).getHasStack()) {
            	temp = false;
            }
        }
        return temp;
    }
}
