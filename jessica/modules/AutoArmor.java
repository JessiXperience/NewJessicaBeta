package jessica.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.ConnectionUtils;
import jessica.utils.EntityUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketClickWindow;
import net.minecraft.util.DamageSource;

public class AutoArmor extends Module{
	ValueBoolean enchantments = new ValueBoolean("Enchantments", true);
	ValueBoolean swapWhileMoving = new ValueBoolean("Swap while moving", false);
	ValueNumber delay = new ValueNumber("Delay", 2, 0, 20, 1);
	private int timer;
	
	public AutoArmor() {
		super("AutoArmor", Category.PLAYER);
		addValue(enchantments);
		addValue(swapWhileMoving);
		addValue(delay);
	}
	
	@Override
    public void onEnable() {
        this.timer = 0;
        super.onEnable();
    }

	@Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
		// wait for timer
		if (this.timer > 0) {
			this.timer--;
			return;
        }
        if (Wrapper.mc().currentScreen instanceof GuiContainer && !(Wrapper.mc().currentScreen instanceof InventoryEffectRenderer)) {
            return;
        }
        InventoryPlayer inventoryPlayer = Wrapper.player().inventory;
        if (!(this.swapWhileMoving.getValue() || Wrapper.player().movementInput.field_192832_b == 0.0f && Wrapper.player().movementInput.moveStrafe == 0.0f)) {
            return;
        }
        // store slots and values of best armor pieces
        int[] bestArmorSlots = new int[4];
        int[] bestArmorValues = new int[4];
        // initialize with currently equipped armor
        for (int type = 0; type < 4; type++) {
        	bestArmorSlots[type] = -1;
        	ItemStack stack = inventoryPlayer.armorItemInSlot(type);
            if (EntityUtils.isNullOrEmptyStack(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor item = (ItemArmor)stack.getItem();
            bestArmorValues[type] = this.getArmorValue(item, stack);
        }
        // search inventory for better armor
        for (int slot = 0; slot < 36; slot++) {
        	ItemStack stack = inventoryPlayer.getStackInSlot(slot);
            if (EntityUtils.isNullOrEmptyStack(stack) || !(stack.getItem() instanceof ItemArmor)) continue;
            ItemArmor item = (ItemArmor)stack.getItem();
            int armorType = item.armorType.getIndex();
            int armorValue = this.getArmorValue(item, stack);
            if (armorValue <= bestArmorValues[armorType]) continue;
            bestArmorSlots[armorType] = slot;
            bestArmorValues[armorType] = armorValue;
        }
        
        // equip better armor in random order
        ArrayList<Integer> types = new ArrayList<Integer>(Arrays.asList(0, 1, 2, 3));
        Collections.shuffle(types);
        for(int type : types) {
			// check if better armor was found
			int slot = bestArmorSlots[type];
			if(slot == -1)
				continue;
				
			// check if armor can be swapped
			// needs 1 free slot where it can put the old armor
			ItemStack oldArmor = inventoryPlayer.armorItemInSlot(type);
			if(!EntityUtils.isNullOrEmptyStack(oldArmor) && inventoryPlayer.getFirstEmptyStack() == -1)
				continue;
			
			// hotbar fix
			if(slot < 9)
				slot += 36;
			
			// swap armor
			if(!EntityUtils.isNullOrEmptyStack(oldArmor))
				Wrapper.mc().playerController.windowClick(0, 8 - type, 0, ClickType.QUICK_MOVE, Wrapper.player());
			Wrapper.mc().playerController.windowClick(0, slot, 0, ClickType.QUICK_MOVE, Wrapper.player());
			
			break;
		}
        super.onClientTick(clientTickEvent);
    }
    
    @Override
    public boolean onPacket(Object object, ConnectionUtils.Side side) {
        if (side == ConnectionUtils.Side.OUT && object instanceof CPacketClickWindow) {
            this.timer = (int)this.delay.getDoubleValue();
        }
        return true;
    }

    public int getArmorValue(ItemArmor itemArmor, ItemStack itemStack) {
        int armorPoints = itemArmor.damageReduceAmount;
        int prtPoints = 0;
        int armorToughness = (int)itemArmor.toughness;
        int armorType = itemArmor.getArmorMaterial().getDamageReductionAmount(EntityEquipmentSlot.LEGS);
        if (enchantments.getValue()) {
            Enchantment enchantment = Enchantments.PROTECTION;
            int prtLvl = EnchantmentHelper.getEnchantmentLevel(enchantment, itemStack);
            EntityPlayerSP entityPlayerSP = Wrapper.player();
            DamageSource damageSource = DamageSource.causePlayerDamage(entityPlayerSP);
            prtPoints = enchantment.calcModifierDamage(prtLvl, damageSource);
        }
        return armorPoints * 5 + prtPoints * 3 + armorToughness + armorType;
    }
}
