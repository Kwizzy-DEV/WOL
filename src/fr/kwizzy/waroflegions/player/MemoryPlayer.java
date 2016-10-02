package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.bukkit.Title;
import fr.kwizzy.waroflegions.util.storage.JsonStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.UUID;

import static fr.kwizzy.waroflegions.util.java.StringUtils.*;

/**
 * Par Alexis le 30/09/2016.
 */

public class MemoryPlayer {

    JsonStorage j = JsonStorage.getInstance();

    private UUID uuid;
    String path;

    public MemoryPlayer(UUID uuid) {
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
