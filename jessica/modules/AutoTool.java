package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;

public class AutoTool extends Module{
	public int prevSelectedSlot = -2;
	
	public AutoTool() {
		super("AutoTool", Category.PLAYER);
	}
	
	@Override
    public void onEnable() {
        this.prevSelectedSlot = -2;
        super.onEnable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (!Wrapper.mc().gameSettings.keyBindAttack.isKeyDown() || KillAura.target != null) {
            if (this.prevSelectedSlot != -2) {
                entityPlayerSP.inventory.currentItem = this.prevSelectedSlot;
                this.prevSelectedSlot = -2;
            }
            return;
        }
        RayTraceResult rayTraceResult = Wrapper.mc().objectMouseOver;
        if (rayTraceResult == null || rayTraceResult.typeOfHit != RayTraceResult.Type.BLOCK) {
            return;
        }
        IBlockState iBlockState = Wrapper.world().getBlockState(rayTraceResult.getBlockPos());
        if (iBlockState.getBlock() instanceof BlockAir) {
            return;
        }
        float f2 = 1.0f;
        int n2 = -2;
        if (this.prevSelectedSlot == -2) {
            this.prevSelectedSlot = entityPlayerSP.inventory.currentItem;
        }
        for (int i2 = 0; i2 < 9; ++i2) {
            float f3;
            ItemStack itemStack = entityPlayerSP.inventory.getStackInSlot(i2);
            if (EntityUtils.isNullOrEmptyStack(itemStack) || !((f3 = itemStack.getStrVsBlock(iBlockState)) > f2)) continue;
            f2 = f3;
            n2 = i2;
        }
        if (n2 != -2) {
            entityPlayerSP.inventory.currentItem = n2;
        }
        super.onClientTick(clientTickEvent);
    }	
}
