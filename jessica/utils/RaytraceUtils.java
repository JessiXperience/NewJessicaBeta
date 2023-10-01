package jessica.utils;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;

import jessica.Wrapper;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import optifine.Reflector;

public class RaytraceUtils {
	public static Entity getEntityLook(double range, float partialTicks) {
        Entity entity = Wrapper.mc().getRenderViewEntity();
        Vec3d vec3d = entity.getPositionEyes(partialTicks);
        Vec3d vec3d2 = entity.getLook(partialTicks);
        Vec3d vec3d3 = vec3d.addVector(vec3d2.xCoord * range, vec3d2.yCoord * range, vec3d2.zCoord * range);
        return RaytraceUtils.rayTrace(entity, range, vec3d, vec3d2, vec3d3);
    }
	
	public static Entity rayTrace(Entity entity, double d2, Vec3d vec3d, Vec3d vec3d2, Vec3d vec3d3) {
		Entity entity2 = null;
		List list = Wrapper.world().getEntitiesInAABBexcluding(entity, entity.getEntityBoundingBox().expand(vec3d2.xCoord * d2, vec3d2.yCoord * d2, vec3d2.zCoord * d2).expand(1.0, 1.0, 1.0), Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>()
		{
			@Override
			public boolean apply(@Nullable Entity p_apply_1_)
            {
                return p_apply_1_ != null && p_apply_1_.canBeCollidedWith();
            }
		}));
		for (int i2 = 0; i2 < list.size(); ++i2) {
			double d3;
			Entity entity3 = (Entity)list.get(i2);
			AxisAlignedBB axisAlignedBB = entity3.getEntityBoundingBox().expandXyz((double)entity3.getCollisionBorderSize());
			RayTraceResult rayTraceResult = axisAlignedBB.calculateIntercept(vec3d, vec3d3);
			if (axisAlignedBB.isVecInside(vec3d)) {
				if (!(d2 >= 0.0)) continue;
				entity2 = entity3;
				d2 = 0.0;
				continue;
			}
			if (rayTraceResult == null || !((d3 = vec3d.distanceTo(rayTraceResult.hitVec)) < d2) && d2 != 0.0) continue;
			if (entity3.getLowestRidingEntity() == entity.getLowestRidingEntity() /*&& !Reflector.ForgeEntity_canRiderInteract.exists()*/) {
				if (d2 != 0.0) continue;
				entity2 = entity3;
				continue;
			}
			entity2 = entity3;
		}
		return entity2;
	}
	
	public static Entity b(double d2, float yaw, float pitch) {
        return RaytraceUtils.b(d2, yaw, pitch, Wrapper.mc().getRenderPartialTicks());
    }
	
	public static Entity b(double d2) {
        return RaytraceUtils.b(d2, Wrapper.mc().getRenderPartialTicks());
    }
	
	public static Entity b(double d2, float f2) {
        Entity entity = Wrapper.mc().getRenderViewEntity();
        Vec3d vec3d = entity.getPositionEyes(f2);
        Vec3d vec3d2 = entity.getLook(f2);
        Vec3d vec3d3 = vec3d.addVector(vec3d2.xCoord * d2, vec3d2.yCoord * d2, vec3d2.zCoord * d2);
        return rayTrace(entity, d2, vec3d, vec3d2, vec3d3);
    }
    
    public static Entity b(double d2, float yaw, float pitch, float f4) {
        Entity entity = Wrapper.mc().getRenderViewEntity();
        Vec3d vec3d = entity.getPositionEyes(f4);
        Vec3d vec3d2 = getVectorForRotation(pitch, yaw);
        Vec3d vec3d3 = vec3d.addVector(vec3d2.xCoord * d2, vec3d2.yCoord * d2, vec3d2.zCoord * d2);
        return rayTrace(entity, d2, vec3d, vec3d2, vec3d3);
    }
    
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
      float f1 = MathHelper.cos(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
      float f2 = MathHelper.sin(-yaw * ((float)Math.PI / 180) - (float)Math.PI);
      float f3 = -MathHelper.cos(-pitch * ((float)Math.PI / 180));
      float f4 = MathHelper.sin(-pitch * ((float)Math.PI / 180));
      return new Vec3d(f2 * f3, f4, f1 * f3);
    }

    public static float getYaw(Entity entity) {
        double x = entity.posX - Wrapper.player().posX;
        double z = entity.posZ - Wrapper.player().posZ;
        double yaw = Math.atan2(x, z) * 57.29577951308232;
        yaw = -yaw;
        return (float)yaw;
    }
    
    public static float b(Entity entity) {
        double d2 = entity.posY - Wrapper.player().posY;
        double d3 = Math.asin(d2 /= (double)Wrapper.player().getDistanceToEntity(entity)) * 57.29577951308232;
        d3 = -d3;
        return (float)d3;
    }
    
    public static float updateRotation(float f2, float f3, float f4) {
        float f5 = MathHelper.wrapDegrees(f3 - f2);
        if (f5 > f4) {
            f5 = f4;
        }
        if (f5 < -f4) {
            f5 = -f4;
        }
        return f2 + f5;
    }
    
    public static void faceBow(Entity target, boolean silent, boolean predict, float predictSize) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        double posX = target.posX + (predict ? (target.posX - target.prevPosX) * (double)predictSize : 0.0) - (entityPlayerSP.posX + (predict ? entityPlayerSP.posX - entityPlayerSP.prevPosX : 0.0));
        double posY = target.getEntityBoundingBox().minY + (predict ? (target.getEntityBoundingBox().minY - target.prevPosY) * (double)predictSize : 0.0) + (double)target.getEyeHeight() - 0.15 - (entityPlayerSP.getEntityBoundingBox().minY + (predict ? entityPlayerSP.posY - entityPlayerSP.prevPosY : 0.0)) - (double)entityPlayerSP.getEyeHeight();
        double posZ = target.posZ + (predict ? (target.posZ - target.prevPosZ) * (double)predictSize : 0.0) - (entityPlayerSP.posZ + (predict ? entityPlayerSP.posZ - entityPlayerSP.prevPosZ : 0.0));
        double sqrt = Math.sqrt(posX * posX + posZ * posZ);
        float velocity = (float)entityPlayerSP.getItemInUseCount() / 20.0f;
        if ((velocity = (velocity * velocity + velocity * 2.0f) / 3.0f) > 1.0f) {
        	velocity = 1.0f;
        }
        float yaw = (float)(Math.atan2(posZ, posX) * 180.0 / Math.PI) - 90.0f;
        float pitch = (float)(-Math.toDegrees(Math.atan(((double)(velocity * velocity) - Math.sqrt((double)(velocity * velocity * velocity * velocity) - (double)0.006f * ((double)0.006f * (sqrt * sqrt) + 2.0 * posY * (double)(velocity * velocity)))) / ((double)0.006f * sqrt))));
        if (velocity < 0.1f) {
        	float[] rotations = getNeededRotations(getCenter(target.getEntityBoundingBox()), true);
            yaw = rotations[0];
            pitch = rotations[1];
        }
        if (silent) {
        	CPacketUtils.a(yaw, pitch);
            Wrapper.player().renderYawOffset = CPacketUtils.d();
            Wrapper.player().rotationYawHead = CPacketUtils.d();
            return;
        }
        float[] rotations = limitAngleChange(new float[]{entityPlayerSP.rotationYaw, entityPlayerSP.rotationPitch}, new float[]{yaw, pitch}, (float)(10 + RandomUtils.getRandom().nextInt(6)));
        if (rotations == null) {
            return;
        }
        entityPlayerSP.rotationYaw = rotations[0];
        entityPlayerSP.rotationPitch = rotations[1];
    }
    
    public static Vec3d getCenter(AxisAlignedBB axisAlignedBB) {
        return new Vec3d(axisAlignedBB.minX + (axisAlignedBB.maxX - axisAlignedBB.minX) * 0.5, axisAlignedBB.minY + (axisAlignedBB.maxY - axisAlignedBB.minY) * 0.5, axisAlignedBB.minZ + (axisAlignedBB.maxZ - axisAlignedBB.minZ) * 0.5);
    }
    
    public static float[] getNeededRotations(Vec3d vec3d, boolean predict) {
        Vec3d vec3d2 = getEyesPos();
        if (predict) {
            vec3d2.addVector(Wrapper.player().motionX, Wrapper.player().motionY, Wrapper.player().motionZ);
        }
        double d2 = vec3d.xCoord - vec3d2.xCoord;
        double d3 = vec3d.yCoord - vec3d2.yCoord;
        double d4 = vec3d.zCoord - vec3d2.zCoord;
        double d5 = Math.sqrt(d2 * d2 + d4 * d4);
        float f2 = (float)Math.toDegrees(Math.atan2(d4, d2)) - 90.0f;
        float f3 = (float)(-Math.toDegrees(Math.atan2(d3, d5)));
        return new float[]{MathHelper.wrapDegrees((float)f2), MathHelper.wrapDegrees((float)f3)};
    }
    
    public static Vec3d getEyesPos() {
        return new Vec3d(Wrapper.player().posX, Wrapper.player().posY + (double)Wrapper.player().getEyeHeight(), Wrapper.player().posZ);
    }
    
    public static float[] limitAngleChange(float[] current, float[] target, float turnSpeed) {
        double yawDifference = angleDifference(target[0], current[0]);
        double pitchDifference = angleDifference(target[1], current[1]);
        current[0] = current[0] + (float)(yawDifference > (double)turnSpeed ? (double)turnSpeed : (yawDifference < (double)(-turnSpeed) ? (double)(-turnSpeed) : yawDifference));
        current[1] = current[1] + (float)(pitchDifference > (double)turnSpeed ? (double)turnSpeed : (pitchDifference < (double)(-turnSpeed) ? (double)(-turnSpeed) : pitchDifference));
        return current;
    }
    
    public static float angleDifference(float a, float b) {
        return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
    }
}
