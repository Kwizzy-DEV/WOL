package fr.kwizzy.waroflegions.util.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import fr.kwizzy.waroflegions.WOL;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Par Alexis le 26/10/2016.
 */

public class JSONStorage
{
    private static final String PATH = WOL.getInstance().getDataFolder().getAbsolutePath() + File.separator;
    private static HashMap<String, JSONObject> jsonObjects = new HashMap<>();

    /* like folder/file */
    String pathFile;
    JSONObject jo;

    public JSONStorage(String path)
    {
        this.pathFile = path;
        init();
    }

    public JSONStorage()
    {
        jo = new JSONObject();
    }

    private void init()
    {
        if(!jsonObjects.containsKey(pathFile))
        {
            String doc = readFile(getFile(pathFile).getAbsolutePath());
            JSONObject j = new JSONObject();
            if(doc != null && !doc.isEmpty())
                j = new JSONObject(doc);
            jsonObjects.put(pathFile, j);
        }
    }

    public void put(String path, Object o)
    {
        String[] split = path.split(Pattern.quote("."));
        Map<String, Object> base = (pathFile != null) ? getFullJson().toMap(): jo.toMap();
        Map<String, Object> m = base;
        for (int i = 0; i < split.length - 1; i++) {
            String p = split[i];
            Object mp = m.get(p);
            if (mp == null || !(mp instanceof Map)) {
                mp = new HashMap<>();
                m.put(p, mp);
            }
            m = (Map<String, Object>) mp;
        }
        m.put(split[split.length - 1], o);
        jsonObjects.put(pathFile, new JSONObject(base));
    }

    public Object get(String path)
    {
        String[] split = path.split(Pattern.quote("."));
        JSONObject jsonObject = (pathFile != null) ? getFullJson() : jo;
        JSONObject json = null;
        Object o = "";
        for (int i = 0; i < split.length; i++) {
            try {
                if (i == split.length - 1)
                    o = ((json == null) ? jsonObject.get(split[i]) : json.get(split[i]));
                else
                    json = (JSONObject) ((json == null) ? jsonObject.get(split[i]) : json.get(split[i]));
            } catch (JSONException e) {
                return null;
            }
        }
        return o;
    }

    public boolean remove(String path)
    {
        String[] paths = path.split(Pattern.quote("."));
        Map<String, Object> base = (pathFile != null) ? getFullJson().toMap() : jo.toMap();
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
        if(pathFile != null)
            jsonObjects.put(pathFile, new JSONObject(base));
        else
            jo = new JSONObject(base);
        return true;
    }

    public boolean contain(String path)
    {
        return get(path) != null;
    }

    public void delete()
    {
        if(pathFile != null)
        {
            jsonObjects.remove(pathFile);
            getFile(pathFile).deleteOnExit();
        }
    }

    public JSONObject getFullJson()
    {
        return jsonObjects.get(pathFile);
    }

    public void save()
    {
        if(pathFile != null)
            save(pathFile, jsonObjects.get(pathFile));
    }

    public static void save(String k, JSONObject v)
    {
        File f = getFile(k);
        if (!f.exists() && f.isFile())
        {
            try
            {
                f.createNewFile();
            } catch (IOException ex)
            {
                WOL.getInstance().print(ex.getMessage());
            }
        }
        try (FileWriter fw = new FileWriter(f.getAbsolutePath()))
        {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonParser jp = new JsonParser();
            JsonElement je = jp.parse(v.toString());
            fw.write(gson.toJson(je));
            fw.flush();
            fw.close();
        } catch (IOException ex)
        {
            WOL.getInstance().print(ex.getMessage());
        }
    }

    public static File getFile(String path)
    {
        String[] parts = path.split(Pattern.quote("/"));
        StringBuilder pathAbsolute = new StringBuilder();
        for (int x = 0; x < parts.length; x++) {
            if (x == parts.length - 1)
                pathAbsolute.append(parts[x]);
            else
                pathAbsolute.append(parts[x]).append(File.separator);
        }
        return new File(PATH, pathAbsolute.toString() + ".json");

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
            WOL.getInstance().print(ex.getMessage());
        }
        return result;

    }


    public static void saveAll()
    {
        jsonObjects.forEach(JSONStorage::save);
    }
}
