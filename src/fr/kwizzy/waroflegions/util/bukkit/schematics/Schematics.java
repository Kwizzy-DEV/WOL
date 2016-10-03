package fr.kwizzy.waroflegions.util.bukkit.schematics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import java.util.*;

import fr.kwizzy.waroflegions.util.bukkit.jnbt.*;
import fr.kwizzy.waroflegions.util.bukkit.schematics.blocks.*;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class Schematics {

	private short width;
	private short length;
	private short height;
	private List<Tag> entities;
	private List<Tag> titleEntities;
	private Collection<SchematicBlock> schematicBlocks;
	private boolean air;

	private Schematics(boolean air) {
		this.width = 0;
		this.length = 0;
		this.height = 0;
		this.air = air;
		this.schematicBlocks = new ArrayList<>();
	}

	private Schematics(URL url, boolean air) throws IOException {
		this(url.openConnection().getInputStream(), air);
	}

	public static Schematics from(String path, boolean includeAir, boolean canBeNull) {
		File file = new File(path);
		if (!canBeNull && !file.exists())
			throw new NullPointerException("Fichier schematic non trouvé ! (" + path + ")");
		try {
			return new Schematics(file.toURI().toURL(), includeAir);
		} catch (IOException e) {
			return null;
		}
	}

	private Schematics(final InputStream in, boolean air) throws IOException {
		this(air);
		NBTInputStream input = new NBTInputStream(in);
		CompoundTag schematic = (CompoundTag) input.readTag();
		this.width = NBTUtils.getTagValue(schematic, "Width", (short) 0);
		this.length = NBTUtils.getTagValue(schematic, "Length", (short) 0);
		this.height = NBTUtils.getTagValue(schematic, "Height", (short) 0);
		byte[] blocks = NBTUtils.getTagValue(schematic, "Blocks", new byte[0]);
		byte[] blocksData = NBTUtils.getTagValue(schematic, "Data", new byte[0]);
		this.entities = NBTUtils.getTagValue(schematic, "Entities", new ArrayList<Tag>());
		this.titleEntities = NBTUtils.getTagValue(schematic, "TitleEntities", new ArrayList<Tag>());
		this.air = air;
		HashMap<Vector, CompoundTag> tileEntityLookup = new HashMap<>();
		titleEntities.stream().filter(t -> t instanceof ListTag).forEach(t -> {
			CompoundTag titleEntityData = (CompoundTag) t;
			tileEntityLookup.put(new Vector(NBTUtils.getTagValue(titleEntityData, "x", 0), NBTUtils.getTagValue(titleEntityData, "y", 0), NBTUtils.getTagValue(titleEntityData, "z", 0)),
					titleEntityData);
		});

		for (int xCoord = 0; xCoord < width; xCoord++) {
			for (int yCoord = 0; yCoord < height; yCoord++) {
				for (int zCoord = 0; zCoord < length; zCoord++) {
					int blockIndex = yCoord * width * length + zCoord * width + xCoord;
					int id = blocks[blockIndex];
					byte data = blocksData[blockIndex];
					if (id < 0) id = Integer.parseInt(String.format("%02x", (byte) id), 16);
					if (!air && id == 0)
						continue;
					switch (Material.getMaterial(id)) {
						case CAULDRON:
							schematicBlocks.add(new CauldronSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case CHEST:
							schematicBlocks.add(new ChestSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case DISPENSER:
							schematicBlocks.add(new TrapSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case ENCHANTMENT_TABLE:
							schematicBlocks.add(new EnchantmentTableSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case ENDER_PORTAL:
							schematicBlocks.add(new EnderPortalSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case FURNACE:
						case BURNING_FURNACE:
							schematicBlocks.add(new FurnaceSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case NOTE_BLOCK:
							schematicBlocks.add(new MusicSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case PISTON_BASE:
						case PISTON_STICKY_BASE:
						case PISTON_EXTENSION:
						case PISTON_MOVING_PIECE:
							schematicBlocks.add(new PistonSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case JUKEBOX:
							schematicBlocks.add(new JukeboxSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case SIGN:
						case SIGN_POST:
							schematicBlocks.add(new SignSchematicBlock(tileEntityLookup.get(new Vector(xCoord, yCoord, zCoord)), xCoord, yCoord, zCoord, id, data));
							break;
						case AIR:
							if (this.air) schematicBlocks.add(new SchematicBlock(xCoord, yCoord, zCoord, id, data));
							break;
						default:
							// regular schematic block
							schematicBlocks.add(new SchematicBlock(xCoord, yCoord, zCoord, id, data));
							break;
					}
				}
			}
		}
		in.close();
	}

	public Collection<SchematicBlock> getBlocks() {
		return schematicBlocks;
	}

	public SchematicBlock[] getBlocksArray() {
		return getBlocks().toArray(new SchematicBlock[getBlocks().size()]);
	}

	public void save(final OutputStream stream) throws IOException {
		Map<String, Tag> tags = new HashMap<>();
		tags.put("Width", new ShortTag("Widht", width));
		tags.put("Length", new ShortTag("Length", length));
		tags.put("Heigth", new ShortTag("Heigth", height));
		byte[] blocks = new byte[width * height * length];
		byte[] data = new byte[width * height * length];
		for (SchematicBlock b : schematicBlocks) {
			int blockIndex = b.getY() * width * length + b.getZ() * width + b.getX();
			blocks[blockIndex] = (byte) b.getId();
			data[blockIndex] = b.getData();
		}
		tags.put("Blocks", new ByteArrayTag("Blocks", blocks));
		tags.put("Data", new ByteArrayTag("Data", data));
		NBTOutputStream s = new NBTOutputStream(stream);
		s.writeTag(new CompoundTag("", tags));
		s.close();
	}

	public short getWidth() {
		return width;
	}

	public short getHeigth() {
		return height;
	}

	public short getLength() {
		return length;
	}

	public Schematics rotate(float angleX , float angleY , float angleZ) {
		for (SchematicBlock b : schematicBlocks) b.rotate(angleX , angleY , angleZ);
		return this;
	}

}
