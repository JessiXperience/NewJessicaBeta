package jessica.modules;

import java.awt.Color;

import jessica.Wrapper;
import jessica.events.RenderGameOverlayEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.utils.EntityUtils;
import jessica.utils.RenderUtils;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class PlayerRadar extends Module{
	ValueNumber radarSize = new ValueNumber("Radar size", 100, 50, 140, 10);
	
	public PlayerRadar() {
		super("PlayerRadar", Category.RENDER);
		this.addValue(radarSize);
	}
	
	@Override
    public void onRenderGameOverlay(float partialTicks) {
		ScaledResolution sr = new ScaledResolution(Wrapper.mc());
        this.drawRadar(sr.getScaledWidth() - (int)this.radarSize.getDoubleValue() + 1, sr.getScaledHeight() - (int)this.radarSize.getDoubleValue() - 31, (int)this.radarSize.getDoubleValue(), partialTicks);
        super.onRenderGameOverlay(partialTicks);
    }
	
	public void drawRadar(int x, int y, int radarSize, float f2) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        int n5 = radarSize / 2;
        float f3 = (float)((double)x + 2.5);
        float f4 = (float)((double)y + 2.5);
        float f5 = (float)((double)(x + radarSize) - 2.5);
        float f6 = (float)((double)(y + radarSize) - 2.5);
        int n6 = ColorUtils.setColor(new Color(31, 31, 31, 168).getRGB(), 0.5f);
        RenderUtils.drawRect(f3, (double)f4, (double)f5, (double)f6, n6);
        
        for (Entity entity : Wrapper.world().getLoadedEntityList()) {
        	if(!EntityUtils.isValidEntity(entity)) continue;
            if (entity.isInvisible()) continue;
            int color = !(entity instanceof EntityPlayer) ? new Color(0, 255, 0, 255).getRGB() : (entityPlayerSP.canEntityBeSeen(entity) ? new Color(255, 0, 0, 255).getRGB() : new Color(5, 171, 240, 255).getRGB());
            float f7 = (float)(entity.posX + (entity.posX - entity.lastTickPosX) * (double)f2 - entityPlayerSP.posX) * 2.0f;
            float f8 = (float)(entity.posZ + (entity.posZ - entity.lastTickPosZ) * (double)f2 - entityPlayerSP.posZ) * 2.0f;
            float f9 = (float)Math.cos((double)entityPlayerSP.rotationYaw * (Math.PI / 180));
            float f10 = (float)Math.sin((double)entityPlayerSP.rotationYaw * (Math.PI / 180));
            
            float f11 = -(f8 * f9 - f7 * f10);
            float f12 = -(f7 * f9 + f8 * f10);
            
            float dotSize = 2.0f;
            
            if (f11 > (float)n5 - 5.0f) {
                f11 = (float)n5 - 5.0f;
            } else if (f11 < -((float)n5 - 5.0f)) {
                f11 = -((float)n5 - 5.0f);
            }
            
            if (f12 > (float)n5 - 5.0f) {
                f12 = (float)n5 - 5.0f;
            } else if (f12 < -((float)n5 - 5.0f)) {
                f12 = -((float)n5 - 5.0f);
            }
            
            if (entity == entityPlayerSP) {
            	color = new Color(200, 200, 200, 255).getRGB();
            	dotSize = 3.0f;
            }
            
            float f14 = (float)(x + radarSize / 2) + f12 - dotSize;
            float f15 = (float)(y + n5) + f11 - dotSize;
            float f16 = (float)(x + n5) + f12 + dotSize;
            float f17 = (float)(y + n5) + f11 + dotSize;
            RenderUtils.drawRect(f14, (double)f15, (double)f16, (double)f17, color);
        }
    }
}
