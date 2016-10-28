package fr.kwizzy.waroflegions.common.armorcalc;

import fr.kwizzy.waroflegions.util.bukkit.classmanager.message.Message;
import fr.kwizzy.waroflegions.util.java.bistream.BiStream;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Par Alexis le 24/10/2016.
 */

public class ArmorCalculator {

	@Message private static String pointProtection = "§9+%s Resistance damage";

	int[] armorId = {
            298, 299, 300, 301,
            302, 303, 304, 305,
            306, 307, 308, 309,
            310, 311, 312, 313,
            314, 315, 316, 317
    };

	ItemStack armor;

	ArmorCalculator(ItemStack item) {
		armor = item;
	}

    public ItemStack getArmor()
    {
        return armor;
    }


    public void addProtPoint()
    {
		if(!isArmor())
			return;
        ItemMeta itemMeta = armor.getItemMeta();
        int index = 0;
        BiStream.BiValue<Boolean, Integer> prot = new BiStream.BiValue<>(false, -1);
        List<String> lore = itemMeta.getLore();
        if(lore != null && !lore.isEmpty())
            for (String s : lore)
            {
                if(s.contains("Resistance damage"))
                {
                    prot.setKey(true);
                    prot.setValue(index);
                }
                index++;
            }
        else
            lore = new ArrayList<>();
        if(!prot.getKey())
        {
            lore.add(" ");
            lore.add(String.format(pointProtection, calcProt()));
        }
        else
            lore.set(prot.getValue(), String.format(pointProtection, calcProt()));

        itemMeta.setLore(lore);
        armor.setItemMeta(itemMeta);
    }

	public double calcProt() {
		if (isArmor()) {
			return getBaseArmorPoint() + getProtectionPoint();
		}
		return -1.0;
	}

	public boolean isArmor() {
		for (int i : armorId) {
			if (armor.getType().getId() == i)
				return true;
		}
		return false;
	}

	private double getBaseArmorPoint() {
		switch (armor.getType()) {
			case LEATHER_HELMET :
				return 0.5;
			case GOLD_HELMET :
				return 1;
			case CHAINMAIL_HELMET :
				return 1;
			case IRON_HELMET :
				return 1;
			case DIAMOND_HELMET :
				return 1.5;

			case LEATHER_CHESTPLATE :
				return 1.5;
			case GOLD_CHESTPLATE :
				return 2.5;
			case CHAINMAIL_CHESTPLATE :
				return 2.5;
			case IRON_CHESTPLATE :
				return 3;
			case DIAMOND_CHESTPLATE :
				return 4;

			case LEATHER_LEGGINGS :
				return 1;
			case GOLD_LEGGINGS :
				return 1.5;
			case CHAINMAIL_LEGGINGS :
				return 2;
			case IRON_LEGGINGS :
				return 2.5;
			case DIAMOND_LEGGINGS :
				return 3;

			case LEATHER_BOOTS :
				return 0.5;
			case GOLD_BOOTS :
				return 0.5;
			case CHAINMAIL_BOOTS :
				return 0.5;
			case IRON_BOOTS :
				return 1;
			case DIAMOND_BOOTS :
				return 1.5;
			default :
				return 0;
		}
	}

	private double getProtectionPoint() {
		Map<Enchantment, Integer> enchantments = armor.getEnchantments();
        if (enchantments.isEmpty())
			return 0;
		if (enchantments.containsKey(Enchantment.PROTECTION_ENVIRONMENTAL)) {
			Integer integer = enchantments.get(Enchantment.PROTECTION_ENVIRONMENTAL);
            return (6 + integer) * 0.75 / 3;
		}
		return 0;
	}
}
