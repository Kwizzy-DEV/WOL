package fr.kwizzy.waroflegions.util.storage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Par Alexis le 17/09/2016.
 */

public class YamlStorage implements Storage {

	/*
	 * 
	 * How to write a new yaml, the first PATH is the name of the file. Example
	 * : write("player-58498.strenght.pts", 10) = data/player-58498.yml ->
	 * strenght: pts: 10
	 * 
	 * You can use folder separation with the '/' Example :
	 * write("players/uhcbuzgyefuyzi.strenght.pts", 10) =
	 * data/players/uhcbuzgyefuyzi.yml -> strenght: pts: 10
	 * 
	 * This class "YamlStorage" is a cache class, the informations are saved at
	 * different time like 5, 10, 20 minutes. And when the server stop.
	 */


    private static File        folder   = new File(WOL.getInstance().getDataFolder() + File.separator + "data" + File.separator);
    private static YamlStorage instance = new YamlStorage();

	private static HashMap<String, BiValue<YamlConfiguration, File>> cacheFiles = new HashMap<>();

	private YamlStorage() {
        backup();
	}

	@Override
	public void write(String path, Object t) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path)))
			cacheFiles.get(Storage.getFirstPart(path)).getKey().set(Storage.getFinalPart(Storage.getParts(path)), t);
		else{
			File f = Storage.fileFromString(path, ".yml");
			YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
			y.set(Storage.getFinalPart(Storage.getParts(path)), t);
			cacheFiles.put(Storage.getFirstPart(path), new BiValue<>(y, f));
		}
	}

	@Override
	public void delete(String path) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path))) {
			cacheFiles.get(Storage.getFirstPart(path)).getKey().set(Storage.getFinalPart(Storage.getParts(path)), null);
			YamlConfiguration yml = getYml(path);
			yml.set(path, null);
		}
	}

	@Override
	public Boolean contain(String path) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path))) {
			cacheFiles.get(Storage.getFirstPart(path)).getKey().set(Storage.getFinalPart(Storage.getParts(path)), null);
			YamlConfiguration yml = getYml(path);
			return yml.contains(path);
		}
		return false;
	}

	@Override
	public boolean getBoolean(String path) {
		return Boolean.parseBoolean(getValue(path));
	}

	@Override
	public Integer getInt(String path) {
		return Integer.parseInt(getValue(path));
	}

	@Override
	public Double getDouble(String path) {
		return Double.parseDouble(getValue(path));
	}

	@Override
	public Long getLong(String path) {
		return Long.parseLong(getValue(path));
	}

	@Override
	public Short getShort(String path) {
		return Short.parseShort(getValue(path));
	}

	@Override
	public Byte getByte(String path) {
		return Byte.parseByte(getValue(path));
	}

	@Override
	public Character getChar(String path) {
		return getValue(path).charAt(0);
	}

	@Override
	public Float getFloat(String path) {
		return Float.parseFloat(getValue(path));
	}

	@Override
	public String getString(String path) {
		return getValue(path);
	}

	@Override
	public List<String> getStringList(String path) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path)) && cacheFiles.get(Storage.getFirstPart(path)).getKey().contains(Storage.getFinalPart(Storage.getParts(path)))){
			return cacheFiles.get(Storage.getFirstPart(path)).getKey().getStringList(path);
		}
		YamlConfiguration yml = getYml(path);
		return yml.getStringList(Storage.getFinalPart(path.split(Pattern.quote("."))));
	}

	@Override
	public ItemStack getItemStack(String path) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path)) && cacheFiles.get(Storage.getFirstPart(path)).getKey().contains(Storage.getFinalPart(Storage.getParts(path)))){
			return cacheFiles.get(Storage.getFirstPart(path)).getKey().getItemStack(path);
		}
		YamlConfiguration yml = getYml(path);
		return yml.getItemStack(Storage.getFinalPart(path.split(Pattern.quote("."))));
	}

	@Override
	public Object getUnknown(String path) {
		if(cacheFiles.containsKey(Storage.getFirstPart(path)) && cacheFiles.get(Storage.getFirstPart(path)).getKey().contains(Storage.getFinalPart(Storage.getParts(path)))){
			return cacheFiles.get(Storage.getFirstPart(path)).getKey().getItemStack(path);
		}
		YamlConfiguration yml = getYml(path);
		return yml.get(Storage.getFinalPart(path.split(Pattern.quote("."))));
	}

	@Override
	public void saveAll() {
		if (!folder.exists())
			folder.mkdirs();

		genericSave();
	}

	@Override
	public void clear() {
        saveAll();
		cacheFiles.clear();
	}

	private void genericSave() {
		if (cacheFiles.isEmpty())
			return;
		for (Map.Entry<String, BiValue<YamlConfiguration, File>> e : cacheFiles.entrySet()) {
			try {
				e.getValue().getKey().save(e.getValue().getValue());
			} catch (IOException ex) {
				WOL.getInstance().print("Can't save " + e.getValue().getValue().getAbsolutePath());
			}
		}
	}

	private YamlConfiguration getYml(String path) {
		String[] part = path.split(Pattern.quote("."));
		if (cacheFiles.containsKey(part[0]))
			return cacheFiles.get(part[0]).getKey();
		File f = Storage.fileFromString(part[0], ".yml");
		return YamlConfiguration.loadConfiguration(f);
	}

	private String getValue(String path) {
		String p = Storage.getFinalPart(Storage.getParts(path));
		if(cacheFiles.containsKey(Storage.getFirstPart(path))){
			return cacheFiles.get(Storage.getFirstPart(path)).getKey().getString(p);
		}
		else {
			File f = Storage.fileFromString(path, ".yml");
			YamlConfiguration y = YamlConfiguration.loadConfiguration(f);
			if(y.getString(p) != null){
				cacheFiles.put(Storage.getFirstPart(path), new BiValue<>(y, f));
				return y.getString(p);
			}
		}
		return null;
	}

	public static Storage getStorage() {
		return instance;
	}

    public void backup(){
        new BukkitRunnable(){
            @Override
            public void run() {
                instance.saveAll();
            }
        }.runTaskTimer(WOL.getInstance(), (long)20*60*5, (long)20*60*5);
    }

	public Map<String, Object> getMap(String path){
		if(cacheFiles.containsKey(Storage.getFirstPart(path)) && cacheFiles.get(Storage.getFirstPart(path)).getKey().contains(Storage.getFinalPart(Storage.getParts(path)))){
			return cacheFiles.get(Storage.getFirstPart(path)).getKey().getValues(true);
		}
		return null;
	}

	public static YamlStorage getInstance() {
		return instance;
	}

	class BiValue<T, X> {

		T key;
		X value;

		BiValue(T key, X value) {
			this.key = key;
			this.value = value;
		}

		T getKey() {
			return key;
		}

		X getValue() {
			return value;
		}
	}
}
