package fr.kwizzy.waroflegions.util.bukkit.schematics.blocks;

import org.bukkit.inventory.ItemStack;

import fr.kwizzy.waroflegions.util.bukkit.jnbt.CompoundTag;
import fr.kwizzy.waroflegions.util.bukkit.schematics.SchematicBlock;

public class ChestSchematicBlock extends SchematicBlock {

	@SuppressWarnings("unused")
	private ItemStack[] items;

	public ChestSchematicBlock(CompoundTag tag, int x, int y, int z, int id, byte data) {
		super(x, y, z, id, data);
		/*
		 * NbtFactory.NbtList list = nbtCompound.getList("Items", true); items = new ItemStackJson[27]; for(Object it : list){ if(it instanceof NbtFactory.NbtCompound){ NbtFactory.NbtCompound itm = (NbtFactory.NbtCompound) it; byte slot = itm.getByte("Slot",(byte) 0); if (slot >= 0 && slot < 27) {
		 * items[slot] = new ItemStackJson(itm.getInteger("id", 0), itm.getByte("Count", (byte) 0), itm.getShort("Damage", (short) 0)); }
		 *
		 * } }
		 */
	}

}
