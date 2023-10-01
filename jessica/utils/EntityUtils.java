package jessica.utils;

import java.util.ArrayList;

import jessica.Wrapper;
import jessica.modules.Targets;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.MovementInput;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;

public class EntityUtils {
	public static boolean nullCheck() {
		return (Wrapper.player() == null || Wrapper.world() == null);
	}
	
	public static boolean isValidEntity(Entity entity) {
        if (Targets.players.getValue() && entity instanceof EntityPlayer && !entity.isInvisible()) {
            return true;
        }
        if(Targets.mobs.getValue() && entity instanceof EntityLiving && !entity.isInvisible()) {
        	return true;
        }
   //     if(entity.isInvisible()) {
   //     	return isInvisible((EntityLivingBase)entity);
   //     }
        return false;
    }
	
	public static boolean isInvisible(EntityLivingBase entity) {
        if(Targets.invisibles.getValue() && entity.isInvisible()) {
        	return true;
        }
        return false;
    }


	public static String getPlayerName(EntityPlayer entityPlayer) {
        return entityPlayer.getGameProfile() != null ? entityPlayer.getGameProfile().getName() : "null";
    }
	
	public static int getArmorEntityAndItemInHand(EntityPlayer entityPlayer) {
        ItemStack[] itemStackArray;
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        InventoryPlayer inventoryPlayer = entityPlayer.inventory;
        ItemStack itemStack = entityPlayer.getHeldItemMainhand();
        ItemStack itemStack2 = inventoryPlayer.armorItemInSlot(0);
        ItemStack itemStack3 = inventoryPlayer.armorItemInSlot(1);
        ItemStack itemStack4 = inventoryPlayer.armorItemInSlot(2);
        ItemStack itemStack5 = inventoryPlayer.armorItemInSlot(3);
        for (ItemStack itemStack6 : itemStackArray = new ItemStack[]{itemStack, itemStack5, itemStack4, itemStack3, itemStack2}) {
            if (itemStack6 == null || itemStack6.func_190926_b()) continue;
            arrayList.add(itemStack6);
        }
        return arrayList.size();
    }
	
	public static boolean isInsideBlock(EntityLivingBase entityLivingBase, Block block) {
		for (int x = MathHelper.floor(entityLivingBase.getEntityBoundingBox().minX); x < MathHelper.floor(entityLivingBase.getEntityBoundingBox().maxX) + 1; ++x) {
            for (int y = MathHelper.floor(entityLivingBase.getEntityBoundingBox().minY); y < MathHelper.floor((double)entityLivingBase.getEntityBoundingBox().maxY) + 1; ++y) {
                for (int z = MathHelper.floor(entityLivingBase.getEntityBoundingBox().minZ); z < MathHelper.floor((double)entityLivingBase.getEntityBoundingBox().maxZ) + 1; ++z) {
                    BlockPos blockPos = new BlockPos(x, y, z);
                    IBlockState iBlockState = Wrapper.world().getBlockState(blockPos);
                    Block block2 = Wrapper.world().getBlockState(blockPos).getBlock();
                    if (!(block == null ? block2 != null && !(block2 instanceof BlockAir) && block2.getCollisionBoundingBox(Wrapper.world().getBlockState(new BlockPos(x, y, z)), (IBlockAccess)Wrapper.world(), new BlockPos(x, y, z)) != null && entityLivingBase.isInsideOfMaterial(iBlockState.getMaterial()) : block2 != null && block2 == block)) continue;
                    return true;
                }
            }
        }
        return false;
    }
	
	public static boolean f() {
        ItemStack itemStack = Wrapper.player().getHeldItemOffhand();
        return !isNullOrEmptyStack(itemStack) && itemStack.getItem() == Items.SHIELD;
    }
	
	public static boolean isNullOrEmptyStack(ItemStack stack) {
        return stack == null || stack.func_190926_b();
    }
	
	public static boolean e() {
        ItemStack itemStack = Wrapper.player().getHeldItemMainhand();
        return !isNullOrEmptyStack(itemStack) && (itemStack.getItem() instanceof ItemSword || itemStack.getItem() instanceof ItemAxe);
    }
	
	public static boolean isDead() {
        return Wrapper.player().getHealth() == 0.0f || Wrapper.player().isDead;
    }
	
	public static int getAxe() {
        int n2 = -2;
        for (int i2 = 0; i2 <= 8; ++i2) {
            if (!(Wrapper.player().inventory.getStackInSlot(i2).getItem() instanceof ItemAxe)) continue;
            n2 = i2;
        }
        return n2;
    }
	
	public static boolean b(EntityPlayer entityPlayer) {
        ItemStack itemStack = entityPlayer.getHeldItemOffhand();
        ItemStack itemStack2 = entityPlayer.getHeldItemMainhand();
        return !isNullOrEmptyStack(itemStack) && itemStack.getItem() == Items.SHIELD && entityPlayer.getItemInUseCount() > 0 || !isNullOrEmptyStack(itemStack2) && itemStack2.getItem() == Items.SHIELD && entityPlayer.getItemInUseCount() > 0;
    }
	
	public static boolean isClosest(EntityLivingBase entityLivingBase, EntityLivingBase entityPriority) {
        return entityPriority == null || Wrapper.player().getDistanceToEntity(entityLivingBase) < Wrapper.player().getDistanceToEntity(entityPriority);
    }
	
	public static EntityLivingBase getClosestPlayer() {
        EntityLivingBase closestEntity = null;
        for (final Entity o : Wrapper.world().loadedEntityList) {
            if (o instanceof EntityPlayer) {
                final EntityLivingBase e = (EntityLivingBase)o;
                if (e instanceof EntityPlayerSP || (closestEntity != null && Wrapper.player().getDistanceToEntity(e) >= Wrapper.player().getDistanceToEntity(closestEntity))) {
                    continue;
                }
                closestEntity = e;
            }
        }
        return closestEntity;
    }
	
	public static boolean isLowHealth(EntityLivingBase entityLivingBase, EntityLivingBase entityPriority) {
        return entityPriority == null || entityLivingBase.getHealth() < entityPriority.getHealth();
    }
	
	public static boolean getMostArmored(EntityLivingBase entityLivingBase, EntityLivingBase entityLivingBase2) {
        return entityLivingBase2 == null || !(entityLivingBase instanceof EntityPlayer) || !(entityLivingBase2 instanceof EntityPlayer) || getArmorEntityAndItemInHand((EntityPlayer)entityLivingBase) > getArmorEntityAndItemInHand((EntityPlayer)entityLivingBase2);
    }
	
	public static void setEntityBoundingBoxSize(Entity entity, float width, float height) {
		EntitySizeUtils size = EntitySizeUtils.getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d2 = (double)width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d2, entity.posY, entity.posZ - d2, entity.posX + d2, entity.posY + (double)height, entity.posZ + d2));
    }
	
	public static void setEntityBoundingBoxSize(Entity entity) {
		EntitySizeUtils size = EntitySizeUtils.getEntitySize(entity);
        entity.width = size.width;
        entity.height = size.height;
        double d2 = (double)entity.width / 2.0;
        entity.setEntityBoundingBox(new AxisAlignedBB(entity.posX - d2, entity.posY, entity.posZ - d2, entity.posX + d2, entity.posY + (double)entity.height, entity.posZ + d2));
    }
	
	public static boolean isInFOV(Entity entity, double angle) {
        return isInFOV(Wrapper.player(), entity, angle);
    }
	
	public static boolean isInFOV(Entity player, Entity entity, double angle) {
        double angleDiff = getAngleDifference(player.rotationYaw, getLookNeeded(player, entity.posX, entity.posY, entity.posZ)[0]);
        return angleDiff > 0.0 && angleDiff < (angle *= 0.5) || -angle < angleDiff && angleDiff < 0.0;
    }
	
	public static float getAngleDifference(float direction, float rotationYaw) {
        float phi = Math.abs(rotationYaw - direction) % 360.0f;
        float distance = phi > 180.0f ? 360.0f - phi : phi;
        return distance;
    }
	
	public static float[] getLookNeeded(Entity entity, double x, double y, double z) {
		double posX = x + 0.5D - entity.posX;
	    double posY = y - entity.posY;
	    double posZ = z + 0.5D - entity.posZ;
	    double sqrt = Math.sqrt(posX * posX + posZ * posZ);
	    float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D) - 90.0F;
	    float pitch = (float)-(Math.atan2(posY, sqrt) * 180.0D / 3.141592653589793D);
	    return new float[] { yaw, pitch };
	}
}
