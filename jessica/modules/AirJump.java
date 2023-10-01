package jessica.modules;

import jessica.Wrapper;
import jessica.events.InputUpdateEvent;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.MotionUtils;
import jessica.value.ValueMode;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class AirJump extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Matrix"});
	
	public AirJump() {
		super("AirJump", Category.MOVEMENT);
		addValue(mode);
	}
	
	@Override
    public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {
        if (this.mode.getValue().equalsIgnoreCase("Default") && Wrapper.mc().gameSettings.keyBindJump.isKeyDown()) {
            Wrapper.player().onGround = true;
        }
        super.onInputUpdate(inputUpdateEvent);
    }
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        if (this.mode.getValue().equalsIgnoreCase("Matrix")) {
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            if (playerTickEvent.phase == PlayerTickEvent.Phase.END) {
                entityPlayerSP.setSprinting(false);
                float f2 = 1.0f;
                if ((Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX - (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ - (double)f2)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX + (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ + (double)f2)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX - (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ + (double)f2)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX + (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ - (double)f2)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX + (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX - (double)f2, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ + (double)f2)).getBlock() != Blocks.AIR
                		|| Wrapper.world().getBlockState(new BlockPos(entityPlayerSP.posX, entityPlayerSP.posY - (double)f2, entityPlayerSP.posZ - (double)f2)).getBlock() != Blocks.AIR)
                		&& !entityPlayerSP.isCollidedHorizontally && MotionUtils.isWalking()) {
                    entityPlayerSP.onGround = true;
                    entityPlayerSP.jump();
                }
            }
        }
        super.onPlayerTick(playerTickEvent);
    }	
}
