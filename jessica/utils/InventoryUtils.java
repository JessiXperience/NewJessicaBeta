package jessica.utils;

import jessica.Wrapper;
import net.minecraft.item.Item;

public class InventoryUtils {
	public static int getHotbarItem(Item item) {
        int currentItem = -1;
        for (int slot = 0; slot <= 8; ++slot) {
            if (Wrapper.player().inventory.getStackInSlot(slot).getItem() != item) continue;
            currentItem = slot;
        }
        return currentItem;
    }
	
	public static int getInventoryItem(Item item, int startSlot, int endSlot) {
        int currentItem = -2;
        for (int slot = startSlot; slot <= endSlot; ++slot) {
            if (Wrapper.player().inventory.getStackInSlot(slot).getItem() != item) continue;
            currentItem = slot;
        }
        return currentItem;
    }
	
	public static int getOnlyInventoryItem() {
        for (int i2 = 9; i2 <= 44; ++i2) {
            if (!Wrapper.player().inventory.getStackInSlot(i2).func_190926_b()) continue;
            return i2;
        }
        return -2;
    }
}
