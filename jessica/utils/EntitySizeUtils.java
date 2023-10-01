package jessica.utils;

import net.minecraft.entity.Entity;

public class EntitySizeUtils {
	public float width;
    public float height;

    public EntitySizeUtils(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public static EntitySizeUtils getEntitySize(Entity entity) {
    	EntitySizeUtils hitbox = new EntitySizeUtils(0.6f, 1.8f);
        return hitbox;
    }
}
