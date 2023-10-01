package jessica.utils;

import net.minecraft.block.material.MapColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class Utils {

    public static ScaledResolution getScaledResolution() {
        return new ScaledResolution(Minecraft.getMinecraft());
    }

    public static int[] getMapData(byte[] data) {
        if (data == null) return null;
        int[] arr = new int[data.length];
        int[] colors = new int[arr.length];
        for (int a = 0; a < 128; ++a) {
            for (int b = 0; b < 128; ++b) {
                colors[a + b * 128] = data[a + b * 128];
            }
        }
        for (int a = 0; a < 16384; ++a) {
            int index = colors[a] & 255;
            if (index / 4 == 0) {
                arr[a] = (((a + a / 128 & 1) * 8) + 16) << 24;
            } else {
                arr[a] = MapColor.COLORS[index / 4].getMapColor(index & 3);
            }
            arr[a] |= (255 << 24); // alpha
        }
        return arr;
    }
}
