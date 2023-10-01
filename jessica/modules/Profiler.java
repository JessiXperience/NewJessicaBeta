package jessica.modules;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import jessica.events.RenderWorldLastEvent;
import jessica.managers.FriendManager;
import jessica.managers.ModuleManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ColorUtils;
import jessica.utils.EntityUtils;
import jessica.utils.RenderUtils;
import jessica.value.ValueBoolean;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityTippedArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class Profiler extends Module{
	ValueBoolean armor = new ValueBoolean("Armor", true);
	ValueBoolean effects = new ValueBoolean("Effects", true);
	ValueBoolean healthBar = new ValueBoolean("Health bar", false);
	ValueBoolean items = new ValueBoolean("Items", true);
	ValueBoolean healthTag = new ValueBoolean("Health tag", true);
	static ValueBoolean personView = new ValueBoolean("Person view", false);
	
	public Profiler() {
		super("Profiler", Category.RENDER);
		addValue(armor);
		addValue(healthBar);
		addValue(items);
		addValue(effects);
		addValue(healthTag);
		addValue(personView);
	}
	
	@Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent, Object object) {
        if (object instanceof EntityLivingBase || this.items.getValue() && object instanceof EntityItem) {
            Entity entity = (Entity)object;
            renderProfiler(entity, renderWorldLastEvent.partialTicks, false, false, false, this.armor.getValue(), true, this.healthBar.getValue(), false, this.effects.getValue(), this.healthTag.getValue());
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent, object);
    }
	
	@Override
    public void onDisable() {
    	RenderLivingBase.NAME_TAG_RANGE = 64.0f;
        RenderLivingBase.NAME_TAG_RANGE_SNEAK = 32.0f;
        super.onDisable();
    }
	
	public static void renderProfiler(Entity entity, float partialTicks, boolean espWatchDogs, boolean esp2D, boolean esp3D, boolean renderArmor, boolean bl5, boolean renderHealthBar, boolean tracers, boolean renderEffects, boolean healthTag) {
		if (entity instanceof EntityArmorStand || entity instanceof EntityTippedArrow || 
				!(entity instanceof EntityItem) && !EntityUtils.isValidEntity(entity) && 
				!EntityUtils.isInvisible((EntityLivingBase)entity) || entity == Wrapper.player() && 
				(Wrapper.mc().gameSettings.thirdPersonView == 0 || !personView.getValue())) {
		    return;
		}
        if (ModuleManager.getModule("Optimization").isToggled() && !tracers && Wrapper.mc().gameSettings.thirdPersonView == 0 && !Optimization.canSeeEntity(entity)) {
            return;
        }
        RenderManager renderManager = Wrapper.mc().getRenderManager();
        double d2 = renderManager.viewerPosX;
        double d3 = renderManager.viewerPosY;
        double d4 = renderManager.viewerPosZ;
        double d5 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - d2;
        double d6 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - d3;
        double d7 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - d4;
     //   cU cU2 = cU.e;
        int n2 = new Color(200, 200, 200, 255).getRGB();
        if (esp2D || esp3D || espWatchDogs) {
            n2 = ESP.color.getValue();
        }
        if (tracers) {
            n2 = Tracers.color.getValue();
        }
        if (entity instanceof EntityPlayer && FriendManager.isFriend(entity.getName())) {
            n2 = new Color(0, 255, 0, 255).getRGB();
        }
        if (entity.isInvisible()) {
            n2 = new Color(0, 0, 0, 255).getRGB();
        }
        if (entity instanceof EntityLivingBase) {
        	EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            if (entityLivingBase.hurtTime > 0 || entityLivingBase == BowAimBot.target) {
        //        cU2 = cU.a;
                n2 = new Color(255, 0, 0, 255).getRGB();
            }
        }
        String name = entity.getDisplayName().getFormattedText();
        if (entity instanceof EntityItem) {
        	EntityItem entityItem = (EntityItem)entity;
        	ItemStack itemStack = entityItem.getEntityItem();
        	if(!EntityUtils.isNullOrEmptyStack(itemStack)) {
        		name = itemStack.getItem().getItemStackDisplayName(itemStack);	
        	}
        }
        drawNameTags(entity, name, d5, d6, d7, renderArmor, bl5, renderHealthBar, renderEffects, healthTag);
        if (tracers) {
        	RenderUtils.drawTracer(entity, n2, partialTicks);
        }
        if (espWatchDogs) {
            RenderUtils.drawESPWatchdogs(entity, n2, partialTicks);
        }
        if (esp2D) {
            RenderUtils.drawESP2D(entity, n2, partialTicks);
        }
        if (esp3D) {
        	RenderUtils.drawESP3D(entity, n2, partialTicks);
        }
    }
	
	public static String decimalFormat(Number number, int n2) {
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(n2);
        return decimalFormat.format(number);
    }
	
	public static int setColor2(int paramInt, long paramLong, float paramFloat)
    {
      Color localColor = ColorUtils.pulse(paramInt, paramLong, paramFloat);
      return new Color(localColor.getRed(), 0, localColor.getBlue(), localColor.getAlpha()).getRGB();
    }
	
	public static int setColor1(int paramInt, long paramLong, float paramFloat)
    {
      Color localColor = ColorUtils.pulse(paramInt, paramLong, paramFloat);
      return new Color(0, localColor.getGreen(), localColor.getBlue(), localColor.getAlpha()).getRGB();
    }
	
	public static void render1(String string, int n2, int n3, int n4, int n5, int n6) {
        RenderUtils.drawRect((double)n2, (double)n3, (double)(n2 + n6 + 1), (double)(n3 + Wrapper.mc().fontRendererObj.FONT_HEIGHT), n5);
        Wrapper.mc().fontRendererObj.drawString(string, n2 + 1, n3 + 1, n4);
    }
	
	public static int getHealthColor(EntityLivingBase entityLivingBase) {
        int n2 = (int)entityLivingBase.getHealth();
        if ((double)n2 <= (double)entityLivingBase.getMaxHealth() * 0.25) {
            return setColor2(13, 4L, 0.5f);
        }
        if ((double)n2 <= (double)entityLivingBase.getMaxHealth() * 0.5) {
            return setColor2(13, 4L, 0.5f);
        }
        if ((double)n2 <= (double)entityLivingBase.getMaxHealth() * 0.75) {
            return setColor1(13, 4L, 0.3f);
        }
        return setColor1(13, 4L, 0.3f);
    }
	
	public static void drawNameTags(Entity entity, String string, double posX, double posY, double posZ, boolean renderArmor, boolean bl2, boolean renderHealthBar, boolean renderEffects, boolean healthTag) {
        try {
            float f2;
            float f3;
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            FontRenderer fontRenderer = Wrapper.mc().fontRendererObj;
            RenderManager renderManager = Wrapper.mc().getRenderManager();
            if (renderManager == null || renderManager.options == null) {
                return;
            }
            int n2 = new Color(155, 155, 155, 125).getRGB();
            int n3 = new Color(200, 200, 200, 255).getRGB();

            if (entity instanceof EntityItem) {
            	EntityItem entityItem = (EntityItem)entity;
            	ItemStack itemStack = entityItem.getEntityItem();
            	if(!EntityUtils.isNullOrEmptyStack(itemStack) && itemStack.isItemEnchanted()) {
            		n3 = setColor1(13, 0L, 0.3f);
            	}
            }
            if ((f3 = (f2 = entityPlayerSP.getDistanceToEntity(entity)) / 4.0f) < 1.6f) {
                f3 = 1.6f;
            }
            float f4 = f3 / 1.2f + 4.2f;
            f4 = f4 / 30.0f * 0.2f;
            float f5 = (float)(posY + (double)entity.height + (double)0.3f + (double)(f3 / 6.0f));
            boolean bl6 = renderManager.options.thirdPersonView == 2;
            GlStateManager.pushMatrix();
            GlStateManager.translate((double)posX, (double)f5, (double)posZ);
            GlStateManager.glNormal3f((float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-renderManager.playerViewY), (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((float)(bl6 ? -1 : 1) * renderManager.playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.scale((float)(-f4), (float)(-f4), (float)f4);
            GlStateManager.disableLighting();
            GlStateManager.depthMask((boolean)false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
            GlStateManager.disableTexture2D();
            if (healthTag && bl2 && entity instanceof EntityLivingBase) {
            	EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            	if(entityLivingBase.getHealth() > 0.0) {
            		String string2 = "4";
            		if (entityLivingBase.getHealth() > entityLivingBase.getMaxHealth() / 4.0f) {
            			string2 = "6";
            			if (entityLivingBase.getHealth() > entityLivingBase.getMaxHealth() / 2.0f) {
            				string2 = "e";
            				if (entityLivingBase.getHealth() == entityLivingBase.getMaxHealth()) {
            					string2 = "a";
            				}
            			}
            		}
            		String string3 = String.format("%s [%s]", "§" + string2, decimalFormat(Float.valueOf(entityLivingBase.getHealth()), 1));
            		string = string + string3;
            	}
            }
            int n4 = -11;
            int n5 = -11;
            int n6 = fontRenderer.getStringWidth(string);
            int n7 = fontRenderer.FONT_HEIGHT + 8;
            int n8 = 12;
            int n9 = n4 + n8 - n6 / 2;
            if (bl2) {
            	RenderLivingBase.NAME_TAG_RANGE = 0.0f;
                RenderLivingBase.NAME_TAG_RANGE_SNEAK = 0.0f;
                int n10 = n5 + 10;
                render1(string, n9, n10, n3, new Color(0.0f, 0.0f, 0.0f, 0.25f).getRGB(), n6);
                if (entity instanceof EntityLivingBase && renderHealthBar) {
                	EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
                    if (entityLivingBase.getHealth() < entityLivingBase.getMaxHealth()) {
                        RenderUtils.drawRect((double)(n9 - 2), (double)(n5 + 9), (double)(n9 + n6 + 2), (double)(n5 + (n7 - 12) + 1), n2);
                    }
                    if ((double)entityLivingBase.getHealth() > 0.0) {
                        double d5 = (double)entityLivingBase.getHealth() / (double)entityLivingBase.getMaxHealth();
                        int n11 = (int)((double)n6 * d5);
                        if (n11 > n6) {
                            n11 = n6;
                        }
                        RenderUtils.drawRect((double)(n9 - 2), (double)(n5 + 9), (double)(n9 + n11 + 2), (double)(n5 + (n7 - 12) + 1), getHealthColor(entityLivingBase));
                    }
                }
            }
            if (entity instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer)entity;
                if (renderArmor || renderEffects) {
                    if (!renderHealthBar) {
                        n5 += 5;
                    }
           //         if (renderArmor) {
           //             object = RenderUtils.sizeArmorPlusItemsInHands(entityPlayer, n4 - 14, n5 - 14, 3);
         //               if (object[0] < 1) {
          //                  n5 = 0;
          //              }
          //              if (renderEffects) {
          //                  RenderUtils.a(entityPlayer, n9, n5 - 14, n6, n7, object[1]);
          //              }
            //        } else if (renderEffects) {
            //            RenderUtils.a(entityPlayer, n9, 0, n6, n7, 0);
             //       }
                }
            }
            GlStateManager.enableDepth();
            GlStateManager.enableTexture2D();
            GlStateManager.depthMask((boolean)true);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.popMatrix();
        }
        catch (Exception exception) {
        //    ChatUtils.exception("drawProfiler", exception);
        }
    }
}
