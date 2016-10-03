package fr.kwizzy.waroflegions.util.bukkit.schematics.blocks;



import fr.kwizzy.waroflegions.util.bukkit.jnbt.CompoundTag;
import fr.kwizzy.waroflegions.util.bukkit.schematics.SchematicBlock;

public class SignSchematicBlock extends SchematicBlock{

	/*
	 * private String text1; private String text2; private String text3; private String text4;
	 */

	public SignSchematicBlock(CompoundTag nbtCompound, int x, int y, int z, int id, byte data) {
		super(x, y, z, id, data);
		/*
		 * this.text1 = nbtCompound.getString("Text1", ""); this.text2 = nbtCompound.getString("Text2", ""); this.text3 = nbtCompound.getString("Text3", ""); this.text4 = nbtCompound.getString("Text4", "");
		 */
	}

	/*
	 * @Override public Block place(Location l) { Sign sign = (Sign) super.place(l).getState(); sign.setLine(0, text1); sign.setLine(1, text2); sign.setLine(2, text3); sign.setLine(3, text4); sign.update(); return sign.getBlock(); }
	 */

}
