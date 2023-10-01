package jessica.modules;

import java.awt.Color;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import com.darkmagician6.eventapi.EventTarget;

import jessica.Wrapper;
import jessica.events.RenderGameOverlayEvent;
import jessica.events.RenderWorldLastEvent;
import jessica.managers.ModuleManager;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.AnimationUtils;
import jessica.utils.ColorUtils;
import jessica.utils.EntityUtils;
import jessica.utils.LastPacket;
import jessica.utils.RaytraceUtils;
import jessica.utils.RenderUtils;
import jessica.utils.TimerUtils;
import jessica.utils.WaitTimer;
import jessica.value.Value;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class HUD extends Module{
	
	public static ValueBoolean coordinfo = new ValueBoolean("Coord info", true);
    public static ValueBoolean fpsinfo = new ValueBoolean("FPS info", true);
    public static ValueBoolean bpsinfo = new ValueBoolean("BPS info", true);
    public static ValueBoolean serverinfo = new ValueBoolean("Server info", true);
    ValueBoolean astolfocolor = new ValueBoolean("Astolfo color", true);
    ValueNumber astolfointensity = new ValueNumber("Astolfo intensity", 0.5, 0.1, 1.0, 0.1);
    ValueBoolean armorhud = new ValueBoolean("Armor HUD", true);
    ValueBoolean targethud = new ValueBoolean("Target HUD", true);
    public static boolean playerIsEquipped;
    AnimationUtils animation = new AnimationUtils();
    public static int tick = 0;
    private TimerUtils counter = new TimerUtils();
    public static TimerUtils time;
    private static WaitTimer tpsTimer = new WaitTimer();
    LastPacket lastPacket = new LastPacket();
    public static double lastTps = 20.0D;
    
	public HUD() {
		super("HUD", Category.RENDER);
		this.setToggled(true);
		this.setShow(false);
		addValue(fpsinfo);
		addValue(coordinfo);
		addValue(serverinfo);
		addValue(bpsinfo);
		addValue(astolfocolor);
		addValue(astolfointensity);
		addValue(armorhud);
		addValue(targethud);
	}
	
	
	
	@Override
    public void onRenderGameOverlay(float partialTicks) {
		ScaledResolution sr = new ScaledResolution(Wrapper.mc());
		renderLogo();
		renderArrayList();
		if(armorhud.getValue()) {
			renderArmorHUD(Wrapper.player());
		} else {
			playerIsEquipped = false;
		}
		if(targethud.getValue()) {
			renderTargetHUD(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2 + 50, partialTicks);
		}
	    int lasttime = tick;
	    if (this.counter.get() > 1000L) {
	      tick++;
	      this.counter.reset();
	      Wrapper.getFiles().saveValues();
	    } 
	   
	    Display.setTitle(String.valueOf(String.valueOf(Wrapper.getClientName())));
	    
		super.onRenderGameOverlay(partialTicks);
	}
	
	public static int rainbow(final int delay) {
        double rainbowState = Math.ceil((System.currentTimeMillis() + delay) / 10L);
        rainbowState %= 360.0;
        return Color.getHSBColor((float) (rainbowState / 360.0), 0.2F, 2F).getRGB();
    }
	
	public void renderLogo() {
		int infoX;
        int infoY;
        int infoWidth;
        int infoHeigth = 12;
        String string;
        ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc());
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        int offsetX = 1;
        int logoWidth = 133;
        int logoHeigth = 15;
        int logoX = scaledResolution.getScaledWidth() - logoWidth - offsetX - 18;
        int offsetY = offsetX;
        if (Wrapper.player().getActivePotionEffects().size() > 0) {
        	offsetY += 26;
        }
        int ping;
        int color = ColorUtils.setColor(new Color(23, 23, 23).getRGB(), 0.5f);
        String s = Wrapper.getClientName().toUpperCase();
        RenderUtils.drawTestRect(logoX, offsetY, (logoX + logoWidth + 20), (offsetY + logoHeigth), color);
        Wrapper.FONT_MANAGER.JetBr.drawString(s, logoX + 2, offsetY + 2, rainbow(1));
        
        infoY = logoHeigth + offsetY;
        
        if (coordinfo.getValue()) {
            string = String.format("x: %s y: %s z: %s", decimalFormat(Float.valueOf((float)entityPlayerSP.posX), 1), decimalFormat(Float.valueOf((float)entityPlayerSP.posY), 1), decimalFormat(Float.valueOf((float)entityPlayerSP.posZ), 1));
            infoX = scaledResolution.getScaledWidth() - Wrapper.FONT_MANAGER.Tahoma.getStringWidth(string) - 4 - offsetX;
            infoWidth = scaledResolution.getScaledWidth() - offsetX;
            RenderUtils.drawTestRect(infoX, infoY, infoWidth, (infoY + infoHeigth), color);
            Wrapper.FONT_MANAGER.Tahoma.drawStringWithShadow(string, infoX + 2, infoY + 3, new Color(229, 229, 229, 255).getRGB());
            infoY = infoY + infoHeigth;
        }
        if (serverinfo.getValue()) {
            string = "server: " + (Wrapper.mc().isSingleplayer() ? "singleplayer" : Wrapper.mc().getCurrentServerData().serverIP.split(":")[0]);
            infoX = scaledResolution.getScaledWidth() - Wrapper.FONT_MANAGER.Tahoma.getStringWidth(string) - 4 - offsetX;
            infoWidth = scaledResolution.getScaledWidth() - offsetX;
            RenderUtils.drawTestRect(infoX, infoY, infoWidth, (infoY + infoHeigth), color);
            Wrapper.FONT_MANAGER.Tahoma.drawStringWithShadow(string, infoX + 2, infoY + 3, new Color(229, 229, 229, 255).getRGB());
            infoY = infoY + infoHeigth;
        }
        if (fpsinfo.getValue()) {
            string = "fps: " + Wrapper.mc().getDebugFPS();
            infoX = scaledResolution.getScaledWidth() - Wrapper.FONT_MANAGER.Tahoma.getStringWidth(string) - 4 - offsetX;
            infoWidth = scaledResolution.getScaledWidth() - offsetX;
            RenderUtils.drawTestRect(infoX, infoY, infoWidth, (infoY + infoHeigth), color);
            Wrapper.FONT_MANAGER.Tahoma.drawStringWithShadow(string, infoX + 2, infoY + 3, new Color(229, 229, 229, 255).getRGB());
            infoY = infoY + infoHeigth;
        }
        if (bpsinfo.getValue()) {
            double posX = entityPlayerSP.posX - entityPlayerSP.prevPosX;
            double posY = entityPlayerSP.posZ - entityPlayerSP.prevPosZ;
            float f2 = (float)Math.sqrt(posX * posX + posY * posY);
            int resultbps = Math.round(f2 * 15.5f);
            String string3 = "bps: " + resultbps;
            int n14 = scaledResolution.getScaledWidth() - Wrapper.FONT_MANAGER.Tahoma.getStringWidth(string3) - 4 - offsetX;
            int n15 = scaledResolution.getScaledWidth() - offsetX;
            RenderUtils.drawTestRect(n14, infoY, n15, (infoY + infoHeigth), color);
            Wrapper.FONT_MANAGER.Tahoma.drawStringWithShadow(string3, n14 + 2, infoY + 3, new Color(229, 229, 229, 255).getRGB());
            infoY = infoY + infoHeigth;
        }
    }
	
	public void renderArrayList() {
		ArrayList<Module> enable = new ArrayList<Module>();
		for (final Module m : ModuleManager.getModules().values()) {
			if (m.isToggled() && m.isShown()) {
				enable.add(m);
			}
		}
		final Comparator<Module> cp = new Comparator<Module>() {
            @Override
            public int compare(final Module b1, final Module b2) {
                return Integer.compare(Wrapper.FONT_MANAGER.ChakraPetchRegular.getStringWidth(b1.getName()), Wrapper.FONT_MANAGER.ChakraPetchRegular.getStringWidth(b2.getName()));
            }
        };
        enable.sort(cp.reversed());
        int startY = 10;
        int height = 0;
        int arrSize = 0;
        int offsetX = 1;
        int color = new Color(255, 0, 0, 255).getRGB();
        if (astolfocolor.getValue()) {
            for (int i = 0; i < enable.size(); ++i) {
            	arrSize += 14;
            }
        }
        long l2 = System.currentTimeMillis();
        for (Module hack : enable) {
            String string = hack.getName();
            if (astolfocolor.getValue()) {
            	color = ColorUtils.setRainbow(height, arrSize, (float)astolfointensity.getDoubleValue());
            }
            RenderUtils.drawRect(offsetX, offsetX + height, offsetX + Wrapper.FONT_MANAGER.ChakraPetchRegular.getStringWidth(string) + 3, offsetX + height + 10.0, ColorUtils.setColor(new Color(31, 31, 31, 168).getRGB(), 0.5f));
            Wrapper.FONT_MANAGER.ChakraPetchRegular.drawString(string, offsetX + 1.0f, offsetX + height + 2.0f, color);
            height = height + startY;
        }
    }
    
	public void renderArmorHUD(EntityPlayer entityPlayer) {
        ScaledResolution scaledResolution = new ScaledResolution(Wrapper.mc());
        int armorIconSize = 22;
        int startX = scaledResolution.getScaledWidth() - 126;
        int StartY = scaledResolution.getScaledHeight() - 2;
        int armorCount = 0;
        int color = ColorUtils.setColor(new Color(31, 31, 31).getRGB(), 0.5f);
        if (Wrapper.mc().currentScreen instanceof GuiChat) {
        	StartY -= 14;
        }
        for (ItemStack itemStack : entityPlayer.inventory.armorInventory) {
            if (itemStack == null || itemStack.func_190926_b() || itemStack.getItem() == Items.field_190931_a) continue;
            int n7 = startX - 90 + (9 - ++armorCount) * 24;
            float damage = ((float)itemStack.getMaxDamage() - (float)itemStack.getItemDamage()) / (float)itemStack.getMaxDamage();
            float f3 = 1.0f - damage;
            int percent = 100 - (int)(f3 * 100.0f);
            String string = "" + percent;
            if (percent < 100) {
                string = string + "%";
            }
            int armorStrength = (int)(armorIconSize * damage);
            RenderUtils.drawBorderedRect(n7, StartY, n7 + armorIconSize, StartY - armorIconSize - 9, 1.0f, new Color(0, 0, 0, 55).getRGB(), color);
            if (itemStack.getMaxDamage() > 0) {
                RenderUtils.drawRect(n7, StartY, n7 + armorStrength, StartY - armorIconSize - 9, color);
                Wrapper.FONT_MANAGER.TrebuchetMS.drawString(string, n7 + 2, StartY - 28, new Color(200, 200, 200, 255).getRGB());
            }
            RenderUtils.renderItem(itemStack, n7 + 2, StartY - 2);
        }
        playerIsEquipped = armorCount > 0;
    }
	
	public void renderTargetHUD(int x, int y, float partialTicks) {
        Entity entity = RaytraceUtils.getEntityLook(50.0, partialTicks);
        EntityLivingBase entityLivingBase = null;
   /*     if (AimBot.e != null && !(AimBot.e instanceof EntityArmorStand)) {
            entityLivingBase = AimBot.e;
        }
    */
        if (TargetStrafe.target != null && !(TargetStrafe.target instanceof EntityArmorStand)) {
            entityLivingBase = TargetStrafe.target;
        }
        if (BowAimBot.target != null && !(BowAimBot.target instanceof EntityArmorStand)) {
            entityLivingBase = BowAimBot.target;
        }
        if (KillAura.target != null && !(KillAura.target instanceof EntityArmorStand)) {
            entityLivingBase = KillAura.target;
        }   
        if (entity != null && entity instanceof EntityLivingBase && !(entity instanceof EntityArmorStand)) {
            entityLivingBase = (EntityLivingBase)entity;
        }
        if (Wrapper.mc().currentScreen instanceof GuiChat || Wrapper.mc().currentScreen instanceof GuiContainer) {
            entityLivingBase = Wrapper.player();
        }
        if (entityLivingBase == null || entityLivingBase.isDead || entityLivingBase.deathTime > 0) {
        	if (this.animation.b() == 0.0) {
        		AnimationUtils.tAnimate = System.currentTimeMillis();
        		AnimationUtils.uAnimate = -25.0;
        		this.animation.a();
        		return;
        	}
        	this.animation.a(false);
        }
        this.drawTargetHud(entityLivingBase, x, y);
    }
	
	public void drawTargetHud(EntityLivingBase entityLivingBase, int x, int y) {
        int n4;
        int hurtTime = 0;
        float maxHealth = 0.0F;
        float health = 0.0F;
        int n5;
        int countItems;
        int startX = x;
        int startY = y;
        String name = null;
        if(entityLivingBase != null) {
        	name = entityLivingBase.getName();
        	hurtTime = entityLivingBase.hurtTime;
        	maxHealth = entityLivingBase.getMaxHealth();
        	health = entityLivingBase.getHealth();
        }
        int n10 = 30;
        int n11 = 30;
        int n15 = 0;
        int n16 = -11;
        int n17 = 75;
        int n19 = Wrapper.FONT_MANAGER.ChakraPetchRegular.getStringWidth(name) + 6;
        int n20 = 0;
        boolean player = entityLivingBase instanceof EntityPlayer;
        if (player) {
            EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            countItems = EntityUtils.getArmorEntityAndItemInHand(entityPlayer);
            n20 = 0;
            if (countItems > 0) {
                for (n5 = 0; n5 < countItems; ++n5) {
                    n20 += 18;
                }
            }
            if (!(entityPlayer.getHeldItemOffhand() == null || entityPlayer.getHeldItemOffhand().func_190926_b())) {
                n20 += 18;
            }
        }
        if (n19 > n17) {
            n17 = n19;
        }
        if (n20 > n17) {
            n17 = n20;
        }
        int n21 = n15 + n10 + n17;
        countItems = n11;
        n5 = 4;
        if (!player) {
        	countItems = 9;
        }
        RenderUtils.drawRect((double)(startX + -n5), (double)(startY + n16 - n5), (double)(startX + n10 + n17 + n5), (double)(startY + countItems + n5), ColorUtils.setColor(new Color(0, 0, 0, 75).getRGB(), (float)(this.animation.b() / 3.0)));
        if (!player) {
        	Wrapper.FONT_MANAGER.TrebuchetMS.drawString(name, startX + 2, startY + 2, new Color(200, 200, 200, 255).getRGB());
        } else {
        	Wrapper.FONT_MANAGER.TrebuchetMS.drawString(name, startX + n10 + 4, startY + 4, new Color(200, 200, 200, 255).getRGB());
        }
        String string2 = String.format(entityLivingBase == null ? "" : "%s - %s", decimalFormat(Float.valueOf(health), 1), decimalFormat(Float.valueOf(maxHealth), 0));
        int n24 = new Color(20, 20, 20, 255).getRGB();
        if (hurtTime > 0) {
            n24 = new Color(255, 0, 0, 255).getRGB();
        }
        n24 = ColorUtils.setColor(n24, (float)this.animation.b());
        if (hurtTime > 0) {
            RenderUtils.drawRect((double)(startX + n15), (double)(startY + n16), (double)(startX + n10 + n17), (double)(startY + n16 + 9), new Color(200, 55, 55, 200).getRGB());
        } else if (health < maxHealth) {
            RenderUtils.drawRect((double)(startX + n15), (double)(startY + n16), (double)(startX + n10 + n17), (double)(startY + n16 + 9), n24);
        }
        if ((double)health > 0.0) {
            double d2 = (double)health / (double)maxHealth;
            n4 = (int)((double)n21 * d2);
            if (n4 > n21) {
                n4 = n21;
            }
            long l2 = System.currentTimeMillis();
            double d3 = AnimationUtils.uAnimate + ((double)n4 - AnimationUtils.uAnimate) * Math.pow((double)(l2 - AnimationUtils.tAnimate) / 455.0, 0.5);
            RenderUtils.drawRect((double)(startX + n15), (double)(startY + n16), (double)(startX + n15) + d3, (double)(startY + n16 + 9), new Color(0, 255, 0, 255).getRGB());
            AnimationUtils.uAnimate = d3;
            AnimationUtils.tAnimate = l2;
        }
        Wrapper.FONT_MANAGER.ChakraPetchRegular.drawString(string2, startX + 2, startY + n16 + 1, new Color(50, 50, 50, 255).getRGB());
        if (player) {
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)startX, (float)startY, (float)0.0f);
            EntityPlayer entityPlayer = (EntityPlayer)entityLivingBase;
            int n26 = 33;
            n4 = 15;
            float f5 = 0.7f;
            GL11.glScalef((float)f5, (float)f5, (float)f5);
            RenderUtils.sizeArmorPlusItemsInHands(entityPlayer, n26, n4, 1);
            NetworkPlayerInfo networkPlayerInfo = Wrapper.mc().getConnection().getPlayerInfo(entityPlayer.getName());
            if (networkPlayerInfo != null) {
                Wrapper.mc().getTextureManager().bindTexture(networkPlayerInfo.getLocationSkin());
                int n27 = n10 + 13;
                int n28 = n11 + 13;
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.enableAlpha();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                Gui.drawScaledCustomSizeModalRect(0, 2, 8.0f, 8.0f, 8, 8, n27, n28, 64.0f, 64.0f);
            }
            GlStateManager.popMatrix();
        }
        if (entityLivingBase == null) {
            return;
        }
        this.animation.a(true);
    }
	
    public static String decimalFormat(Number value, int maxvalue) {
        DecimalFormat decimalFormat = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        decimalFormat.setMaximumFractionDigits(maxvalue);
        return decimalFormat.format(value);
    }
}
