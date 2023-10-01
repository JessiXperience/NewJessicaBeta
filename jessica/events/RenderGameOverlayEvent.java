package jessica.events;

import com.darkmagician6.eventapi.events.Event;

public class RenderGameOverlayEvent implements Event{
	private int width;
    private int height;
    private float partialTicks;
    
    public RenderGameOverlayEvent(float partialTicks) {
     //   this.width = width;
     //   this.height = height;
        this.partialTicks = partialTicks;
    }
    
    public int getWidth() {
    	return width;
    }
    
    public int getHeight() {
    	return height;
    }
    
    public float getPartialTicks() {
    	return partialTicks;
    }
}
