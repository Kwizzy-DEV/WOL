package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.storage.JsonStorage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static fr.kwizzy.waroflegions.util.StringUtils.*;

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
            p.sendMessage(messageWithLine(String.format("§eBienvenue §a%s §esur War Of Legions, il y a §a%s joueurs !", p.getName(), Bukkit.getOnlinePlayers().size()), String.format("§eSi tu ne connais pas le serveur fait §a/aide.")));
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
        return get("name");
    }

    public void set(String path, Object o){
        j.write(this.path + path, o);
    }

    public String get(String path){
        return j.getString(path);
    }


}
