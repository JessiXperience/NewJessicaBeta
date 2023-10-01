package jessica.modules;

import jessica.Wrapper;
import jessica.events.PlayerTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import net.minecraft.client.entity.EntityPlayerSP;

public class NoVisualBlock extends Module{

	public NoVisualBlock() {
		super("NoVisualBlock", Category.RENDER);
	}
	
	@Override
    public void onPlayerTick(PlayerTickEvent playerTickEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (EntityUtils.isInsideBlock(entityPlayerSP, null)) {
            entityPlayerSP.noClip = true;
        }
    }
}
