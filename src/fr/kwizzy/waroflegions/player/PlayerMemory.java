package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.storage.JsonStorage;

import java.util.UUID;

/**
 * Par Alexis le 30/09/2016.
 */

class PlayerMemory implements IMemory {

    JsonStorage j = JsonStorage.getInstance();

    private UUID uuid;
    String path;

    public PlayerMemory(UUID uuid) {
        this.uuid = uuid;
        this.path = "players/" + uuid.toString() + ".";
    }

    public String getName(){
        return get("name");
    }

    public void set(String path, Object o){
        j.write(this.path + path, o);
    }

    public String get(String path){
        return j.getString(this.path + path);
    }

}