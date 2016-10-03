package fr.kwizzy.waroflegions.util.bukkit.schematics;

import fr.kwizzy.waroflegions.util.java.Matrix4f;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.util.Vector;

public class SchematicBlock {

	private int id;
	private byte data;
	private int x;
	private int y;
	private int z;

	public SchematicBlock(int x, int y, int z, int id, byte data) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.id = id;
		this.data = data;
	}

	public Location getLocation(Location location) {
		return new Location(location.getWorld(), x + location.getBlockX(), y + location.getBlockY(), z + location.getBlockZ());
	}


	@SuppressWarnings("deprecation")
	public Material getType() {
		return Material.getMaterial(id);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public int getId() {
		return id;
	}

	public byte getData() {
		return data;
	}

	void rotate(float xAngle , float yAngle , float zAngle) {
		Vector v = Matrix4f.identity().rotateX(xAngle).rotateY(yAngle).rotateZ(zAngle).multiply(new Vector(x , y ,z));
		this.x = (int) v.getX();
		this.y = (int) v.getY();
		this.z = (int) v.getZ();
		for (int i = 0; i < xAngle % 90; i++)
			rotate90();
		for (int i = 0; i < zAngle % 90; i++){
			rotate90();
			rotate90();
			rotate90();
		}

	}

	public int rotate90() {
		switch (id) {
			case 50:
			case 75:
			case 76:
				switch (data) {
					case 1:
						return 3;
					case 2:
						return 4;
					case 3:
						return 2;
					case 4:
						return 1;
				}
				break;

			case 66:
				switch (data) {
					case 6:
						return 7;
					case 7:
						return 8;
					case 8:
						return 9;
					case 9:
						return 6;
				}
				/*
				 * FALL-THROUGH
				 */

			case 27:
			case 28:
				switch (data & 0x7) {
					case 0:
						return 1;
					case 1:
						return 0;
					case 2:
						return 5;
					case 3:
						return 4;
					case 4:
						return 2;
					case 5:
						return 3;
				}
				break;

			case 53:
			case 67:
				switch (data) {
					case 0:
						return 2;
					case 1:
						return 3;
					case 2:
						return 1;
					case 3:
						return 0;
				}
				break;

			case 69:
			case 77:
				int thrown = data & 0x8;
				int withoutThrown = data & ~0x8;
				switch (withoutThrown) {
					case 1:
						return 3 | thrown;
					case 2:
						return 4 | thrown;
					case 3:
						return 2 | thrown;
					case 4:
						return 1 | thrown;
				}
				break;

			case 71:
			case 64:
				int topHalf = data & 0x8;
				int swung = data & 0x4;
				int withoutFlags = data & ~(0x8 | 0x4);
				switch (withoutFlags) {
					case 0:
						return 1 | topHalf | swung;
					case 1:
						return 2 | topHalf | swung;
					case 2:
						return 3 | topHalf | swung;
					case 3:
						return 0 | topHalf | swung;
				}
				break;

			case 63:
				return (data + 4) % 16;

			case 65:
			case 68:
			case 61:
			case 62:
			case 23:
				switch (data) {
					case 2:
						return 5;
					case 3:
						return 4;
					case 4:
						return 2;
					case 5:
						return 3;
				}
				break;

			case 91:
			case 86:
				switch (data) {
					case 0:
						return 1;
					case 1:
						return 2;
					case 2:
						return 3;
					case 3:
						return 0;
				}
				break;

			case 93:
			case 94:
				int dir = data & 0x03;
				int delay = data - dir;
				switch (dir) {
					case 0:
						return 1 | delay;
					case 1:
						return 2 | delay;
					case 2:
						return 3 | delay;
					case 3:
						return 0 | delay;
				}
				break;

			case 96:
				int open = data & 0x4;
				int withoutOpen = data & ~0x4;
				switch (withoutOpen) {
					case 0:
						return 3 | open;
					case 1:
						return 2 | open;
					case 2:
						return 0 | open;
					case 3:
						return 1 | open;
				}
			case 33:
			case 34:
			case 29:
				switch (data) {
					case 0:
						return 0;
					case 1:
						return 1;
					case 2:
						return 5;
					case 3:
						return 4;
					case 4:
						return 2;
					case 5:
						return 3;
				}
		}

		return data;
	}
}
