package jessica.modules;

import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.CPacketUseEntity;

public class NoInteract extends Module{
	public static Block[] blocks;
	
	public NoInteract() {
		super("NoInteract", Category.WORLD);
		blocks = new Block[]{Blocks.CHEST, Blocks.ANVIL, Blocks.ENDER_CHEST, Blocks.CRAFTING_TABLE, Blocks.FURNACE, Blocks.ENCHANTING_TABLE, Blocks.DROPPER, Blocks.DISPENSER, Blocks.NOTEBLOCK, Blocks.OAK_DOOR, Blocks.OAK_FENCE_GATE};
	}

    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        CPacketUseEntity cPacketUseEntity;
        if (side != ConnectionUtils.Side.OUT) {
            return true;
        }
        if (object instanceof CPacketUseEntity && ((cPacketUseEntity = (CPacketUseEntity)object).getAction() == CPacketUseEntity.Action.INTERACT || cPacketUseEntity.getAction() == CPacketUseEntity.Action.INTERACT_AT)) {
            return false;
        }
        return super.onPacket(object, side);
    }
}
