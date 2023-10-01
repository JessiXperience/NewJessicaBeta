package jessica.utils;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import net.minecraft.client.gui.ScaledResolution;

import java.io.File;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;

public class MiscUtils {

	 private static ExecutorService service = createExecutor();
	 private static Random rng = new Random();
	 static ScaledResolution sr = new ScaledResolution(Wrapper.mc());
	
	 public static ExecutorService createExecutor() {
		    return Executors.newCachedThreadPool();
		  }
	 
	  public static void runThreadExecutor(Runnable task) {
		    service.execute(task);
		  }
	
	 public static void showSelectFileDialog(String baseDir, Function<File, Object> callback, boolean saveDialog, boolean onlyDirectories) {
		    Runnable task = () -> {
		        JFrame frame = new JFrame("Dialog");
		        frame.setAlwaysOnTop(true);
		        JFileChooser chooser = new JFileChooser(baseDir);
		        if (onlyDirectories) {
		          chooser.setFileSelectionMode(1);
		        } else {
		          chooser.setFileSelectionMode(0);
		        } 
		        int result = 1;
		        if (saveDialog) {
		          result = chooser.showSaveDialog(frame);
		        } else {
		          result = chooser.showOpenDialog(frame);
		        } 
		        if (result == 0) {
		          callback.apply(chooser.getSelectedFile());
		        } else {
		          callback.apply(null);
		        } 
		        frame.dispose();
		      };
		    runThreadExecutor(task);
		  }
	  public static void createDir(String path) {
		    File dir = new File(path);
		    if (!dir.exists())
		      dir.mkdir(); 
		  }

	  public static int getScreenWidth() {
		    return sr.getScaledWidth() * 5;
		  }
		  
		  public static int getScreenHeight() {
		    return sr.getScaledHeight() * 5;
		  }
		  
		  public static boolean ifExist(String name) {
			    if ((new File(name)).exists())
			      return true; 
			    return false;
			  }

		  public static int rgb2int(float r, float g, float b, float a) {
			    int i_color = 0;
			    i_color |= (int)(ColorUtils.clampFloat(r) * 255.0F) << 16;
			    i_color |= (int)(ColorUtils.clampFloat(g) * 255.0F) << 8;
			    i_color |= (int)(ColorUtils.clampFloat(b) * 255.0F);
			    i_color |= (int)(ColorUtils.clampFloat(a) * 255.0F) << 24;
			    return i_color;
			  }
		  
		  public static int random(int min, int max) {
			    int max2 = max - min + 1;
			    if (max2 < 0)
			      max2 = min + 1; 
			    return rng.nextInt(max2) + min;
			  }
}
