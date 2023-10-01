package jessica.modules;

import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import net.minecraft.network.play.client.CPacketCloseWindow;

public class XCarry extends Module{

	public XCarry() {
		super("XCarry", Category.EXPLOIT);
	}
	
	@Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        if (object instanceof CPacketCloseWindow) {
        	CPacketCloseWindow cPacketCloseWindow = (CPacketCloseWindow)object;
        	int n2 = cPacketCloseWindow.windowId;
        	if (n2 == 0) {
        		return false;
        	}
        }
        return super.onPacket(object, side);
	}
	
}
