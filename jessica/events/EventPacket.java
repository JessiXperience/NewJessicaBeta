package jessica.events;

import com.darkmagician6.eventapi.events.callables.EventCancellable;
import net.minecraft.network.Packet;

public class EventPacket extends EventCancellable {
  private Packet packet;
  
  public EventPacket(Packet packet) {
    this.packet = packet;
  }
  
  public Packet getPacket() {
    return this.packet;
  }
  
  public void setPacket(Packet packet) {
    this.packet = packet;
  }
}