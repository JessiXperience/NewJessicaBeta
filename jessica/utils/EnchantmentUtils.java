package jessica.utils;

import net.minecraft.enchantment.Enchantment;

public class EnchantmentUtils {
	private Enchantment enchantment;
    private String name;

    public EnchantmentUtils(Enchantment enchantment, String name) {
        this.enchantment = enchantment;
        this.name = name;
    }

    public Enchantment getEnchantment() {
        return this.enchantment;
    }

    public String getName() {
        return this.name;
    }
}
