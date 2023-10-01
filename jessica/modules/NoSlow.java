package jessica.modules;

import jessica.Wrapper;
import jessica.events.ClientTickEvent;
import jessica.events.InputUpdateEvent;
import jessica.events.LivingJumpEvent;
import jessica.module.Category;
import jessica.module.Module;
import jessica.utils.EntityUtils;
import jessica.utils.MotionUtils;
import jessica.value.ValueBoolean;
import jessica.value.ValueMode;
import jessica.value.ValueNumber;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemShield;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;

public class NoSlow extends Module{
	ValueMode mode = new ValueMode("Mode", "Default", new String[] {"Default", "Bypass", "SunRise", "Matrix"});
	ValueNumber speed = new ValueNumber("Speed", 5.0, 0.5, 5.0, 0.1);
	ValueBoolean matrixAutoJump = new ValueBoolean("Matrix auto jump", true);
	static ValueBoolean rightHand = new ValueBoolean("RightHand", true);
	static ValueBoolean leftHand = new ValueBoolean("LeftHand", true);
	static ValueBoolean bow = new ValueBoolean("Bow", true);
	static ValueBoolean shield = new ValueBoolean("Shield", true);
	static ValueBoolean food = new ValueBoolean("Food", true);
	static ValueBoolean potion = new ValueBoolean("Potion", true);
	public static boolean toggled;
    public boolean needJump;
	
	public NoSlow() {
		super("NoSlow", Category.PLAYER);
		addValue(mode);
		addValue(speed);
		addValue(matrixAutoJump);
		addValue(rightHand);
		addValue(leftHand);
		addValue(bow);
		addValue(shield);
		addValue(food);
		addValue(potion);
	}
	
	@Override
    public void onDisable() {
		toggled = false;
        super.onDisable();
    }

    @Override
    public void onClientTick(ClientTickEvent clientTickEvent) {
    	toggled = true;
        if (this.mode.getValue().equalsIgnoreCase("SunRise") && NoSlow.isUseItem()) {
            Wrapper.player().setSprinting(false);
            KeyBinding.setKeyBindState((int)Wrapper.mc().gameSettings.keyBindJump.getKeyCode(), (boolean)true);
            this.needJump = true;
        } else if (this.needJump) {
            KeyBinding.setKeyBindState((int)Wrapper.mc().gameSettings.keyBindJump.getKeyCode(), (boolean)false);
            this.needJump = false;
        }
        super.onClientTick(clientTickEvent);
    }

    @Override
    public void onJumpEvent(LivingJumpEvent livingJumpEvent) {
        if (this.mode.getValue().equalsIgnoreCase("SunRise") && livingJumpEvent.entityLiving == Wrapper.player() && this.needJump) {
            Wrapper.player().motionY *= 4.2E-12;
        }
        super.onJumpEvent(livingJumpEvent);
    }

    @Override
    public void onInputUpdate(InputUpdateEvent inputUpdateEvent) {
        if (!NoSlow.isUseItem() || !MotionUtils.isWalking()) {
            return;
        }
        float speedValue = (float) this.speed.getDoubleValue();
        if (this.mode.getValue().equalsIgnoreCase("Matrix")) {
            if ((double)Wrapper.player().fallDistance > 0.5) {
                Wrapper.player().jumpMovementFactor = 0.01f;
            }
            if (this.matrixAutoJump.getValue() && Wrapper.player().onGround) {
            	if (Wrapper.player().onGround) {
                    Wrapper.player().motionY = 0.42f;
                }
            }
            inputUpdateEvent.getMovementInput().field_192832_b *= speedValue;
            inputUpdateEvent.getMovementInput().moveStrafe *= speedValue;
        } else if (this.mode.getValue().equalsIgnoreCase("Bypass")) {
            if (Wrapper.player().ticksExisted % 2 == 0) {
                inputUpdateEvent.getMovementInput().field_192832_b *= speedValue;
                inputUpdateEvent.getMovementInput().moveStrafe *= speedValue;
            }
        } else {
            inputUpdateEvent.getMovementInput().field_192832_b *= speedValue;
            inputUpdateEvent.getMovementInput().moveStrafe *= speedValue;
        }
        super.onInputUpdate(inputUpdateEvent);
    }

    public static boolean isUseItem() {
        return toggled && Wrapper.mc().gameSettings.keyBindUseItem.isKeyDown() && NoSlow.getActiveHand();
    }

    public static boolean getActiveHand() {
        EntityPlayerSP entityPlayerSP = Wrapper.player();
        boolean mainhand = rightHand.getValue() && NoSlow.getValidItem(entityPlayerSP.getHeldItemMainhand());
        boolean offhand = leftHand.getValue() && NoSlow.getValidItem(entityPlayerSP.getHeldItemOffhand());
        return mainhand || offhand;
    }

    private static boolean getValidItem(ItemStack itemStack) {
        return !EntityUtils.isNullOrEmptyStack(itemStack) && (food.getValue() && itemStack.getItem() instanceof ItemFood || bow.getValue() && itemStack.getItem() instanceof ItemBow || shield.getValue() && itemStack.getItem() instanceof ItemShield || potion.getValue() && itemStack.getItem() instanceof ItemPotion || food.getValue() && itemStack.getItem() instanceof ItemSoup || food.getValue() && itemStack.getItem() instanceof ItemBucketMilk);
    }
}
