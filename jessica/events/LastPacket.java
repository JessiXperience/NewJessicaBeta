package jessica.events;

import net.minecraft.network.Packet;

public class LastPacket {
  private Packet lastSentPacket;
  
  private long sent;
  
  private Packet lastReceivedPacket;
  
  private long received;
  
  public void setSentValue(Packet packet) {
    this.lastSentPacket = packet;
    this.sent = System.currentTimeMillis();
  }
  
  public int getLastMs() {
    int ms = (int)(getReceived() - this.sent);
    return (ms < 0) ? -ms : ms;
  }
  
  public Packet getLastReceivedPacket() {
    return this.lastReceivedPacket;
  }
  
  public void setLastReceivedPacket(Packet lastReceivedPacket) {
    this.lastReceivedPacket = lastReceivedPacket;
  }
  
  public long getReceived() {
    return this.received;
  }
  
  public void setReceived(long received) {
    this.received = received;
  }
}
