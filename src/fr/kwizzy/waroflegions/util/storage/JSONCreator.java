package fr.kwizzy.waroflegions.util.storage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Par Alexis le 04/10/2016.
 */

public class JSONCreator {

    JSONObject jo = new JSONObject();

    public JSONCreator(JSONObject jo) {
        this.jo = jo;
    }

    public Object get(String path) {
        String[] split = path.split(Pattern.quote("."));
        JSONObject jsonObject = jo;
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

    public void set(String path, Object object){
        String[] paths = path.split(Pattern.quote("."));
        Map<String, Object> base = jo.toMap();
        Map<String, Object> m = base;
        for (int i = 0; i < paths.length - 1; i++) {
            String p = paths[i];
            Object mp = m.get(p);
            if (mp == null || !(mp instanceof Map)) {
                mp = new HashMap<>();
                m.put(p, mp);
            }
            m = (Map<String, Object>) mp;
        }
        m.put(paths[paths.length - 1], object);
        this.jo = new JSONObject(base);
    }

    public JSONObject getJson() {
        return jo;
    }
}