package fr.kwizzy.waroflegions.util.bukkit.schematics.blocks;

import fr.kwizzy.waroflegions.util.bukkit.jnbt.CompoundTag;
import fr.kwizzy.waroflegions.util.bukkit.schematics.SchematicBlock;

public class MusicSchematicBlock extends SchematicBlock {

	@SuppressWarnings("unused")
	private byte note;

	public MusicSchematicBlock(CompoundTag nbtCompound, int x, int y, int z, int id, byte data) {
		super(x, y, z, id, data);
		// this.note = nbtCompound.getByte("note", (byte) 0);
	}

	/*
	 * @SuppressWarnings("deprecation")
	 * 
	 * @Override public Block place(Location l) { NoteBlock b = (NoteBlock) super.place(l).getState(); b.setRawNote(note); return b.getBlock(); }
	 */

}
