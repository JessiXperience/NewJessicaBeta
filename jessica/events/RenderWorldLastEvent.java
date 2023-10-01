package jessica.events;

import com.darkmagician6.eventapi.events.Event;

import net.minecraft.client.renderer.RenderGlobal;

public class RenderWorldLastEvent implements Event {
	public final RenderGlobal context;
    public final float partialTicks;
    public RenderWorldLastEvent(RenderGlobal context, float partialTicks)
    {
        this.context = context;
        this.partialTicks = partialTicks;
    }
}
