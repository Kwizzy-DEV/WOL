package fr.kwizzy.waroflegions.util;

import org.json.JSONObject;

/**
 * Par Alexis le 03/10/2016.
 */
public interface IMemory {

    void set(String path, Object o);
    String get(String path);
    JSONObject getJson();

}
