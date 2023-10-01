package jessica.modules;

import jessica.Wrapper;
import jessica.events.InputUpdateEvent;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.InventoryUtils;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.util.EnumHand;

public class Blackout extends Module{
	ValueMode mode = new ValueMode("Mode", "Matrix", new String[] {"Matrix", "SunRise", "SunRiseMove"});
	public static int c = -2;
	
	public Blackout() {
		super("Blackout", Category.EXPLOIT);
		this.addValue(mode);
	}
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (this.mode.getValue().equalsIgnoreCase("SunRise") && Wrapper.mc().currentScreen == null) {
            Wrapper.sendPacket((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        } else if (this.mode.getValue().equalsIgnoreCase("SunRiseMove") && Wrapper.mc().currentScreen == null) {
            Wrapper.sendPacket((Packet)new CPacketVehicleMove((Entity)Wrapper.player()));
        }
        super.onPlayerTick(playerTickEvent);
    }

    @Override
    public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (this.mode.getValue().equalsIgnoreCase("Matrix") && entityPlayerSP.ticksExisted % 4 == 0) {
            Wrapper.sendPacket((Packet)new CPacketEntityAction((Entity)entityPlayerSP, CPacketEntityAction.Action.START_FALL_FLYING));
            entityPlayerSP.onGround = false;
        }
        super.onInputUpdate(inputUpdateEvent);
    }

    public static void b() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        ItemStack itemStack = entityPlayerSP.inventory.armorItemInSlot(2);
        if (itemStack.getItem() != Items.ELYTRA) {
            int n2;
            int n3 = InventoryUtils.getInventoryItem(Items.ELYTRA, 0, 44);
            if (n3 == -2) {
                return;
            }
            if (!EntityUtils.isNullOrEmptyStack(itemStack) && (n2 = InventoryUtils.getOnlyInventoryItem()) != -2) {
            	Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
            	Wrapper.mc().playerController.windowClick(0, n2, 1, ClickType.PICKUP, Wrapper.player());
                c = n2;
            }
            Wrapper.mc().playerController.windowClick(0, n3, 1, ClickType.PICKUP, Wrapper.player());
            Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
        } else {
            int n4 = InventoryUtils.getOnlyInventoryItem();
            if (n4 != -2) {
            	Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
            	Wrapper.mc().playerController.windowClick(0, n4, 1, ClickType.PICKUP, Wrapper.player());
            }
            if (c != -2) {
            	Wrapper.mc().playerController.windowClick(0, c, 1, ClickType.PICKUP, Wrapper.player());
                Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
                c = -2;
            }
        }
    }

    public static void a(boolean bl) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        int n2 = -2;
        if (bl) {
            n2 = InventoryUtils.getInventoryItem(Items.ELYTRA, 0, 44);
            if (n2 == -2) {
                return;
            }
            Wrapper.mc().playerController.windowClick(0, n2, 1, ClickType.PICKUP, Wrapper.player());
            Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
        }
        Wrapper.sendPacket((Packet)new CPacketEntityAction((Entity)entityPlayerSP, CPacketEntityAction.Action.START_FALL_FLYING));
        Wrapper.sendPacket((Packet)new CPacketEntityAction((Entity)entityPlayerSP, CPacketEntityAction.Action.START_FALL_FLYING));
        if (bl) {
        	Wrapper.mc().playerController.windowClick(0, 6, 1, ClickType.PICKUP, Wrapper.player());
        	Wrapper.mc().playerController.windowClick(0, n2, 1, ClickType.PICKUP, Wrapper.player());
        }
    }
}
