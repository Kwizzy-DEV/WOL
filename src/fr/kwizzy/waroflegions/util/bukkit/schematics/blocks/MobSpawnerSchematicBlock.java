package fr.kwizzy.waroflegions.util.bukkit.schematics.blocks;


import fr.kwizzy.waroflegions.util.bukkit.jnbt.CompoundTag;
import fr.kwizzy.waroflegions.util.bukkit.schematics.SchematicBlock;

public class MobSpawnerSchematicBlock extends SchematicBlock{

	public MobSpawnerSchematicBlock(CompoundTag nbtCompound, int x, int y, int z, byte id, byte data) {
		super(x, y, z, id, data);
	}

}
