package jessica.modules;

import java.awt.Color;

import jessica.Wrapper;
import jessica.events.RenderGameOverlayEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.RenderUtils;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class TotemCounter extends Module{

	public TotemCounter() {
		super("TotemCounter", Category.RENDER);
	}
	
	@Override
	public void onRenderGameOverlay(float partialTicks) {
		ScaledResolution sr = new ScaledResolution(Wrapper.mc());
		int count = 0;
		ItemStack itemStack = null;
		for (int i = 0; i <= 44; ++i) {
			ItemStack itemStack2 = Wrapper.player().inventory.getStackInSlot(i);
			if (itemStack2.getItem() == Items.field_190929_cY) {
				++count;
				itemStack = itemStack2;
			}
		}
		if (itemStack == null || count == 0) {
			return;
		}

		int x = sr.getScaledWidth() / 2 - 23;
		int y = sr.getScaledHeight() / 2 + 31;
		RenderUtils.renderItem(itemStack, x, y);
		Wrapper.mc().fontRendererObj.drawStringWithShadow("" + count, x + 15, y - 8, new Color(200, 200, 200, 255).getRGB());
		super.onRenderGameOverlay(partialTicks);
	}
}
