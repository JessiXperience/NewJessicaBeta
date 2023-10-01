package jessica.events;

import java.lang.reflect.InvocationTargetException;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;

public abstract class Event<T> {
	private Entity targetEntity;
	public boolean cancelled;
	public EventType type;
	public EventDirection direction;
	public boolean isCancelled() {
		return cancelled;
	}
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}
	public EventType getType() {
		return type;
	}
	public void setType(EventType type) {
		this.type = type;
	}
	public EventDirection getDirection() {
		return direction;
	}
	public void setDirection(EventDirection direction) {
		this.direction = direction;
	}
	
	public Event call() {
        this.cancelled = false;
        call(this);
        return this;
    }
	
	public boolean isPre() {
		if(type == null)
			return false;
		
		return type == EventType.PRE;
	}
	public boolean isPost() {
		if(type == null)
			return false;	
		
		return type == EventType.POST;
	
	}
	public boolean isIncoming() {
		if(direction == null) 
			return false;
		return direction == EventDirection.INCOMING;
	}
	public boolean isOutging() {
		if(direction == null) 
			return false;
		return direction == EventDirection.OUTGOING;
	}
    public void setPitch(float pitch) {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.rotationPitch = pitch;
    }
    public void setYaw(float yaw) {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.rotationYawHead = yaw;
        Minecraft.getMinecraft();
        Minecraft.getMinecraft().player.renderYawOffset = yaw;
    }

    public void EventAttackPacket(Entity targetEntity) {
        this.targetEntity = targetEntity;
    }

    public Entity getTargetEntity() {
        return this.targetEntity;
    }
    
    private static final void call(final Event event) {

        final ArrayHelper<Data> dataList = EventManager.get(event.getClass());

        if (dataList != null) {
            for (final Data data : dataList) {

                try {
                    data.target.invoke(data.source, event);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}
