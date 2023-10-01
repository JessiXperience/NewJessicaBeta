package jessica.gui;

import jessica.utils.MiscUtils;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;

public class MainMenu extends GuiScreen {
	public static boolean menuAnimation = true;
	public static boolean renderMatrix = true;
	public static MatrixEffect matrix;
	
	public MainMenu() {
		int w = MiscUtils.getScreenWidth();
        int h = MiscUtils.getScreenHeight();
        if (matrix == null) {
            if (menuAnimation) {
                matrix = new MatrixEffect(w, h, 100);
            } else {
                matrix = new MatrixEffect(w, h, 40);
            }
        }
	}
	public void drawScreen() {
    	drawDefaultBackground();
		this.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, this.width, this.height, this.width, this.height);	
        int w = MiscUtils.getScreenWidth();
        int h = MiscUtils.getScreenHeight();
        if (matrix != null && (matrix.scw != w || matrix.sch != h)) {
            if (menuAnimation) {
                matrix = new MatrixEffect(w, h, 0);
            } else {
                matrix = new MatrixEffect(w, h, 0);
            }
        }
    }
	public void postDrawScreen() {
        if (matrix != null && renderMatrix) {
            matrix.render();
        }
    }
}
