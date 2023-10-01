package net.minecraft.network.handshake.client;

import java.io.IOException;
import jessica.Wrapper;
import jessica.utils.HackPack;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.INetHandlerHandshakeServer;

public class C00Handshake implements Packet<INetHandlerHandshakeServer> {
  private int protocolVersion;
  
  private String ip;
  
  private int port;
  
  private EnumConnectionState requestedState;
  
  public C00Handshake() {}
  
  public C00Handshake(String p_i47613_1_, int p_i47613_2_, EnumConnectionState p_i47613_3_) {
    this.protocolVersion = 340;
    Wrapper.getHackPack();
    if (!HackPack.getFakeIp().isEmpty()) {
      Wrapper.getHackPack();
      Wrapper.getHackPack();
      this.ip = String.valueOf(p_i47613_1_) + "\000" + HackPack.getFakeIp() + "\000" + HackPack.getFakeUUID().replace("-", "");
    } else {
      this.ip = p_i47613_1_;
    } 
    this.port = p_i47613_2_;
    this.requestedState = p_i47613_3_;
  }
  
  public void readPacketData(PacketBuffer buf) throws IOException {
    this.protocolVersion = buf.readVarIntFromBuffer();
    this.ip = buf.readStringFromBuffer(255);
    this.port = buf.readUnsignedShort();
    this.requestedState = EnumConnectionState.getById(buf.readVarIntFromBuffer());
  }
  
  public void writePacketData(PacketBuffer buf) throws IOException {
    buf.writeVarIntToBuffer(this.protocolVersion);
    buf.writeString(this.ip);
    buf.writeShort(this.port);
    buf.writeVarIntToBuffer(this.requestedState.getId());
  }
  
  public void processPacket(INetHandlerHandshakeServer handler) {
    handler.processHandshake(this);
  }
  
  public EnumConnectionState getRequestedState() {
    return this.requestedState;
  }
  
  public int getProtocolVersion() {
    return this.protocolVersion;
  }
}
