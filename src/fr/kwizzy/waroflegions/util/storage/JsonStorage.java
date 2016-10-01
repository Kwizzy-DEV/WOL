package fr.kwizzy.waroflegions.util.storage;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import fr.kwizzy.waroflegions.WarOfLegions;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.StringUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

/**
 * Par Alexis le 18/09/2016.
 */

public class JsonStorage implements Storage {


	private static File folder = new File(WarOfLegions.getInstance().getDataFolder() + File.separator + "data" + File.separator);
	static HashMap<String, JSONObject> cacheFiles = new HashMap<>();
	private static JsonStorage instance = new JsonStorage();

	static {

	}

	private JsonStorage(){

	}

	@Override
	public void write(String path, Object t) {
		if (cacheFiles.containsKey(Storage.getFirstPart(path))) {
			JSONObject jsonObject = writeValue(cacheFiles.get(Storage.getFirstPart(path)), path, t);
			cacheFiles.put(Storage.getFirstPart(path), jsonObject);
		} else {
			JSONObject j = new JSONObject();
			cacheFiles.put(Storage.getFirstPart(path), j);
			JSONObject jsonObject = writeValue(cacheFiles.get(Storage.getFirstPart(path)), path, t);
			cacheFiles.put(Storage.getFirstPart(path), jsonObject);
		}
	}

	@Override
	public void delete(String path) {
		JSONObject jsonObject = cacheFiles.get(Storage.getFirstPart(path));
		deleteValue(jsonObject, path);
	}

	@Override
	public Boolean contain(String path) {
		return getFinalValue(path) != null;
	}

	@Override
	public boolean getBoolean(String path) {
		return (boolean) getFinalValue(path);
	}

	@Override
	public Integer getInt(String path) {
		return (Integer) getFinalValue(path);
	}

	@Override
	public Double getDouble(String path) {
		return (double) getFinalValue(path);
	}

	@Override
	public Long getLong(String path) {
		return Long.parseLong((String) getFinalValue(path));
	}

	@Override
	public Short getShort(String path) {
		return Short.parseShort((String) getFinalValue(path));
	}

	@Override
	public Byte getByte(String path) {
		return Byte.parseByte((String) getFinalValue(path));
	}

	@Override
	public Character getChar(String path) {
		return ((String) getFinalValue(path)).charAt(0);
	}

	@Override
	public Float getFloat(String path) {
		return Float.parseFloat((String) getFinalValue(path));
	}

	@Override
	public String getString(String path) {
		return String.valueOf(getFinalValue(path));
	}

	@Override
	public List<String> getStringList(String path) {
		List<String> finalL = new ArrayList<>();
		JSONArray ja = (JSONArray) getFinalValue(path);
		ja.forEach(e -> finalL.add((String) e));
		return null;
	}

	@Override
	@Deprecated
	public ItemStack getItemStack(String path) {
		return null;
	}

	@Override
	public Object getUnknown(String path) {
		return getFinalValue(path);
	}

	@Override
	public void saveAll() {
		for (Map.Entry<String, JSONObject> e : cacheFiles.entrySet()) {
			String file = e.getKey();
			File f = Storage.fileFromString(file, ".json");
			if(!f.exists()&& f.isFile()) {
				try {
					f.createNewFile();
				} catch (IOException ex) {
					WarOfLegions.getInstance().print(ex.getMessage());
				}
			}
			try (FileWriter fw = new FileWriter(f.getAbsolutePath())){

				Gson gson = new GsonBuilder().setPrettyPrinting().create();
				JsonParser jp = new JsonParser();
				JsonElement je = jp.parse(e.getValue().toString());
				fw.write(gson.toJson(je));
				fw.flush();
				fw.close();

			} catch (IOException ex) {
				WarOfLegions.getInstance().print(ex.getMessage());
			}

		}
	}

	@Override
	public void clear() {
		saveAll();
		cacheFiles.clear();
	}

	public void backup(){
		new BukkitRunnable(){
			@Override
			public void run() {
				saveAll();
			}
		}.runTaskTimer(WarOfLegions.getInstance(), (long)20*60*5, (long)20*60*5);
	}

	private Object getFinalValue(String path) {
		String[] parts = Storage.getParts(path);
		String str = Storage.getFinalPart(parts);
		String[] split = str.split(Pattern.quote("."));
		JSONObject jsonObject = cacheFiles.get(Storage.getFirstPart(path));
		if (jsonObject == null) {
			File f = Storage.fileFromString(path, ".json");
			try {
				jsonObject = new JSONObject(readFile(f.getAbsolutePath()));
			} catch (JSONException e) {
				return null;
			}
		}
		if(split.length == 1){
			return jsonObject.get(split[0]);
		}
		JSONObject json = null;
		Object o = "";

		for (int i = 0; i < split.length; i++) {
			if (i == split.length - 1)
				o = ((json == null) ? jsonObject.get(split[i]) : json.get(split[i]));
			else
				json = (JSONObject) ((json == null) ? jsonObject.get(split[i]) : json.get(split[i]));

		}
		return o;
	}


	private JSONObject writeValue(JSONObject j, String path, Object object) {
		String[] paths = path.split(Pattern.quote("."));
		Map<String, Object> base = j.toMap();
		Map<String, Object> m = base;
		for (int i = 1; i < paths.length - 1; i++) {
			String p = paths[i];
			Object mp = m.get(p);
			if (mp == null || !(mp instanceof Map)) {
				mp = new HashMap<>();
				m.put(p, mp);
			}
			m = (Map<String, Object>) mp;
		}
		m.put(paths[paths.length - 1], object);
		return new JSONObject(base);
	}

	private boolean deleteValue(JSONObject j, String path) {
		String[] paths = path.split(Pattern.quote("."));
		Map<String, Object> base = j.toMap();
		Map<String, Object> m = base;
		for (int i = 1; i < paths.length - 1; i++) {
			String p = paths[i];
			Object mp = m.get(p);
			try {
				m = (Map<String, Object>) mp;
			} catch (Exception e) {
				return false;
			}
		}
		m.remove(paths[paths.length - 1]);
		return true;
	}

	public static JsonStorage getInstance() {
		return instance;
	}

	public static Storage getStorage() {
		return instance;
	}

	private String readFile(String filePath) {
		String result = "";
		try (FileReader fileReader = new FileReader(filePath)){
			BufferedReader br = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null) {
				sb.append(line);
				line = br.readLine();
			}
			result = sb.toString();
			fileReader.close();
			br.close();
		} catch(Exception ex) {
			WarOfLegions.getInstance().print(ex.getMessage());
		}
		return result;
	}
}
