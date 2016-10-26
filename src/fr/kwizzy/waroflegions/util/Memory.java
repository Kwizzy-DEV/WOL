package fr.kwizzy.waroflegions.util;

import com.google.gson.Gson;
import fr.kwizzy.waroflegions.util.storage.JSONStorage;
import org.json.JSONObject;

/**
 * Par Alexis le 03/10/2016.
 */
public abstract class Memory
{

    static Gson gson = new Gson();
    JSONStorage jsonStorage;


    public Memory(String filePath)
    {
        jsonStorage = new JSONStorage(filePath);
    }

    public void put(String path, Object o)
    {
        jsonStorage.put(path, o);
    }

    public String get(String path){
        return (String) jsonStorage.get(path);
    }

    public Object getObject(String path){
        return jsonStorage.get(path);
    }

    public JSONObject getJson(){
        return jsonStorage.getFullJson();
    }

    public JSONStorage getJs(){
        return jsonStorage;
    }

    public static Gson getGson()
    {
        return gson;
    }
}
