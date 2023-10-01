package jessica.modules;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import jessica.events.LivingJumpEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.utils.JumpCircleHelper;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class JumpCircle extends Module{
	ValueNumber existedTime = new ValueNumber("Existed time", 120, 20, 120, 1);
	public List<JumpCircleHelper> b = new ArrayList<>();
	
	public JumpCircle() {
		super("JumpCircle", Category.RENDER);
		addValue(existedTime);
	}
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        double d2 = -(entityPlayerSP.lastTickPosX + (entityPlayerSP.posX - entityPlayerSP.lastTickPosX) * (double)renderWorldLastEvent.partialTicks);
        double d3 = -(entityPlayerSP.lastTickPosY + (entityPlayerSP.posY - entityPlayerSP.lastTickPosY) * (double)renderWorldLastEvent.partialTicks);
        double d4 = -(entityPlayerSP.lastTickPosZ + (entityPlayerSP.posZ - entityPlayerSP.lastTickPosZ) * (double)renderWorldLastEvent.partialTicks);
        GL11.glPushMatrix();
        GL11.glTranslated((double)d2, (double)d3, (double)d4);
        GL11.glDisable((int)2884);
        GL11.glEnable((int)3042);
        GL11.glDisable((int)3553);
        GL11.glDisable((int)3008);
        GL11.glDisable((int)2929);
        GL11.glBlendFunc((int)770, (int)771);
        GL11.glShadeModel((int)7425);
        Collections.reverse(this.b);
        GlStateManager.disableLighting();
        for (JumpCircleHelper d_03 : this.b) {
            float f2 = (float)d_03.c() / (float)this.existedTime.getDoubleValue();
            double d5 = d_03.a().xCoord;
            double d6 = d_03.a().yCoord - (double)f2 * 0.5;
            double d7 = d_03.a().zCoord;
            float f3 = f2;
            float f4 = f3 + 1.0f - f2;
            GL11.glBegin((int)8);
            for (int i2 = 0; i2 <= 360; i2 += 5) {
            	GlStateManager.color((float)d_03.b().xCoord, (float)d_03.b().yCoord, (float)d_03.b().zCoord, 0.4f * (1.0f - f2));
                GL11.glVertex3d((double)(d5 + Math.cos(Math.toRadians(i2 * 6)) * (double)f3), (double)d6, (double)(d7 + Math.sin(Math.toRadians(i2 * 6)) * (double)f3));
                GlStateManager.color(1.0f, 1.0f, 1.0f, 0.01f * (1.0f - f2));
                GL11.glVertex3d((double)(d5 + Math.cos(Math.toRadians(i2)) * (double)f4), (double)(d6 + Math.sin(f2 * 8.0f) * 0.5), (double)(d7 + Math.sin(Math.toRadians(i2) * (double)f4)));
            }
            GL11.glEnd();
        }
        GlStateManager.enableLighting();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        Collections.reverse(this.b);
        GL11.glEnable((int)3553);
        GL11.glDisable((int)3042);
        GL11.glShadeModel((int)7424);
        GL11.glEnable((int)2884);
        GL11.glEnable((int)2929);
        GL11.glEnable((int)3008);
        GL11.glPopMatrix();
        this.b.removeIf(JumpCircleHelper -> JumpCircleHelper.d());
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }

    @Override
    public void onJumpEvent(LivingJumpEvent livingJumpEvent) {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        if (livingJumpEvent.entityLiving == entityPlayerSP && entityPlayerSP.onGround && !entityPlayerSP.isInWater() && !entityPlayerSP.isInLava()) {
            this.a(livingJumpEvent.entityLiving);
        }
        super.onJumpEvent(livingJumpEvent);
    }

    public void a(Entity entity) {
        Color color = ColorUtils.pulse(10, 20L, 1.0f);
        float[] fArray = ColorUtils.getRGBA(color.getRGB());
        Vec3d vec3d = new Vec3d((double)fArray[0], (double)fArray[1], (double)fArray[2]);
        this.b.add(new JumpCircleHelper(entity.getPositionVector(), vec3d, (int)this.existedTime.getDoubleValue()));
    }
}
