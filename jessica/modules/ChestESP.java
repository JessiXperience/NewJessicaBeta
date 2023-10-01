package jessica.modules;

import java.util.ArrayDeque;

import org.lwjgl.opengl.GL11;

import jessica.Wrapper;
import jessica.events.RenderWorldLastEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.RenderUtils;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.math.BlockPos;

public class ChestESP extends Module{
	private int maxChests = 1000;
    public boolean shouldInform = true;
    private ArrayDeque emptyChests = new ArrayDeque();
    private ArrayDeque nonEmptyChests = new ArrayDeque();
    private String[] chestClasses = new String[]{"TileEntityIronChest", "TileEntityGoldChest", "TileEntityDiamondChest", "TileEntityCopperChest", "TileEntitySilverChest", "TileEntityCrystalChest", "TileEntityObsidianChest", "TileEntityDirtChest"};
    private boolean shouldRenderIronChest = true;
	
	public ChestESP() {
		super("ChestESP", Category.RENDER);
	}
	
	@Override
    public void onEnable() {
        this.shouldInform = true;
        this.emptyChests.clear();
        this.nonEmptyChests.clear();
        super.onEnable();
    }

    @Override
    public void onRenderWorldLastEvent(RenderWorldLastEvent renderWorldLastEvent) {
        int chests = 0;
        for (int i = 0; i < Wrapper.world().loadedTileEntityList.size(); ++i) {
        	TileEntity tileEntity = (TileEntity)Wrapper.world().loadedTileEntityList.get(i);
            if (chests >= this.maxChests) {
            	break;
            }
            
            if (tileEntity instanceof TileEntityChest) {
                ++chests;
                TileEntityChest chest = (TileEntityChest)tileEntity;
                boolean trapped = chest.getChestType() == BlockChest.Type.TRAP;
                if (this.emptyChests.contains(tileEntity)) {
                	RenderUtils.drawBlockESP(chest.getPos(), 0.25f, 0.25f, 0.25f);
                } else if (this.nonEmptyChests.contains(tileEntity)) {
                    if (trapped) {
                    	RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                    } else {
                    	RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 0.4f, 1.0f);
                    }
                } else if (trapped) {
                	RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                } else {
                	RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 0.4f, 1.0f);
                }
                if (trapped) {
                	RenderUtils.drawBlockESP(chest.getPos(), 1.0f, 0.0f, 0.0f);
                    continue;
                }
                RenderUtils.drawBlockESP(chest.getPos(), 0.0f, 0.4f, 1.0f);
                continue;
            }
            if (tileEntity instanceof TileEntityEnderChest) {
                ++chests;
                RenderUtils.drawBlockESP(((TileEntityEnderChest)tileEntity).getPos(), 1.0f, 0.0f, 1.0f);
                continue;
            }
            if (!this.shouldRenderIronChest) continue;
            try {
                for (String string : this.chestClasses) {
                    Class<?> clazz = Class.forName("cpw.mods.ironchest.common.tileentity.chest." + string);
                    if (clazz == null || !clazz.isInstance(tileEntity)) continue;
                    RenderUtils.drawBlockESP(tileEntity.getPos(), 0.7f, 0.7f, 0.7f);
                }
                continue;
            }
            catch (ClassNotFoundException classNotFoundException) {
                this.shouldRenderIronChest = false;
            }
        }
        for (int i = 0; i < Wrapper.world().getLoadedEntityList().size(); ++i) {
        	Entity tileEntity = (Entity)Wrapper.world().getLoadedEntityList().get(i);
            if (chests >= this.maxChests) break;
            if (!(tileEntity instanceof EntityMinecartChest)) continue;
            ++chests;
            RenderUtils.drawBlockESP(((EntityMinecartChest)tileEntity).getPosition(), 1.0f, 1.0f, 1.0f);
        }
        if (chests >= this.maxChests && this.shouldInform) {
        	Wrapper.msg("To prevent lag, it will only show the first " + this.maxChests + " chests.", true);
        	this.shouldInform = false;
        } else if (chests < this.maxChests) {
            this.shouldInform = true;
        }
        super.onRenderWorldLastEvent(renderWorldLastEvent);
    }
}
