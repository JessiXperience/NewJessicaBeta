package jessica.modules;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.CPacketUtils;
import jessica.utils.TimerUtils;
import jessica.value.ValueNumber;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.init.Blocks;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

public class BedFucker extends Module{	
	public BedFucker() {
		super("BedFucker", Category.WORLD);
	}
	
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
		for (int x = -4; x < 4; ++x) {
            for (int y = 4; y > -4; --y) {
                for (int z = -4; z < 4; ++z) {
                    BlockPos blockPos = new BlockPos((int)Wrapper.player().posX + x, (int)Wrapper.player().posY + y, (int)Wrapper.player().posZ + z);
                    Block block = Wrapper.world().getBlockState(blockPos).getBlock();
                    if (block instanceof BlockBed) {
                        Wrapper.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, Wrapper.mc().objectMouseOver.sideHit));
                        Wrapper.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, Wrapper.mc().objectMouseOver.sideHit));
                        Wrapper.player().swingArm(EnumHand.MAIN_HAND);
                    }
                }
            }
        }
		super.onClientTick(clientTickEvent);
	}
	/*
	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
        int n2 = (int)this.distance.getDoubleValue();
        Stream stream = blockPosStream(n2, n2, n2);
        if (stream == null) {
            return;
        }
        stream.forEach(blockPos -> {
            int nn2 = Block.getIdFromBlock(Wrapper.world().getBlockState((BlockPos)blockPos).getBlock());
            if (nn2 == Block.getIdFromBlock((Block)Blocks.BED)) {
                float[] fArray = rotateToBlock((BlockPos)blockPos);
                CPacketUtils.a(fArray);
                if (this.d.check(1L)) {
                	Wrapper.player().rotationYaw = (float)((double)Wrapper.player().rotationYaw + 1.0E-4);
                }
                if (this.d.check(2L)) {
                    Wrapper.player().rotationYaw = (float)((double)Wrapper.player().rotationYaw - 2.0E-4);
                    this.d.reset();
                }
                if (this.c.check(33L)) {
                    if (this.b.check(1000L)) {
                        Wrapper.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, (BlockPos)blockPos, Wrapper.mc().objectMouseOver.sideHit));
                    }
                    if (this.b.check(2000L)) {
                        Wrapper.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, (BlockPos)blockPos, Wrapper.mc().objectMouseOver.sideHit));
                        this.b.reset();
                    }
                    Wrapper.player().swingArm(EnumHand.MAIN_HAND);
                    this.c.reset();
                }
            }
        });
        super.onClientTick(clientTickEvent);
    }
	
	public static Stream blockPosStream(int n2, int n3, int n4) {
        if (Wrapper.player() == null || Wrapper.world() == null) {
            return null;
        }
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        BlockPos blockPos = new BlockPos(entityPlayerSP.posX - (double)n2, entityPlayerSP.posY - (double)n3, entityPlayerSP.posZ - (double)n4);
        BlockPos blockPos2 = new BlockPos(entityPlayerSP.posX + (double)n2, entityPlayerSP.posY + (double)n3, entityPlayerSP.posZ + (double)n4);
        return StreamSupport.stream(BlockPos.getAllInBox((BlockPos)blockPos, (BlockPos)blockPos2).spliterator(), true);
    }
	
	public static float[] rotateToBlock(BlockPos blockPos) {
        double d2 = (double)blockPos.getX() + 0.5 - Wrapper.player().posX;
        double d3 = (double)blockPos.getY() + 0.5 - (Wrapper.player().getEntityBoundingBox().minY + (double)Wrapper.player().getEyeHeight());
        double d4 = (double)blockPos.getZ() + 0.5 - Wrapper.player().posZ;
        double d5 = Math.sqrt(d2 * d2 + d4 * d4);
        float f2 = (float)(Math.atan2(d4, d2) * 180.0 / Math.PI) - 90.0f;
        float f3 = (float)(-(Math.atan2(d3, d5) * 180.0 / Math.PI));
        float f4 = Wrapper.mc().gameSettings.mouseSensitivity * 0.6f + 0.2f;
        float f5 = f4 * f4 * f4 * 1.2f;
        f2 -= f2 % f5;
        f3 -= f3 % f5;
        return new float[]{f2, f3};
    }
    */
}
