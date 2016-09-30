package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.storage.JsonStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

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
        createPlayer();
    }

    public boolean createPlayer(){
        Player p = Bukkit.getPlayer(uuid);
        if(p == null)
            return false;
        if(getName() == null) {
            j.write(path + "name", p.getName());
            set("name", p.getName());
            set("uuid", p.getUniqueId().toString());
            set("joinDate", p.getFirstPlayed());
            set("legion", "neutre");
            set("economy.changes", 150);
            set("economy.quota", 0);
            set("stats.kills", 0);
            set("stats.deaths", 0);
            j.saveAll();
            return true;
        }
        return false;
    }

    public String getName(){
        return j.getString(path + "name");
    }

    public void set(String path, Object o){
        j.write(this.path + path, o);
    }

    public String get(String path){
        return j.getString(path);
    }


}
