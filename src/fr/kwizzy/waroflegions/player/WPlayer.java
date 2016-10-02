package fr.kwizzy.waroflegions.player;

import com.sun.jna.Memory;
import fr.kwizzy.waroflegions.economy.EconomyPlayer;
import fr.kwizzy.waroflegions.util.bukkit.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

import static fr.kwizzy.waroflegions.util.java.StringUtils.messageWithLine;

/**
 * Par Alexis le 30/09/2016.
 */

public class WPlayer {

    private static HashMap<UUID, WPlayer> players = new HashMap<>();

    MemoryPlayer memoryPlayer;

    String name;
    UUID uuid;

    EconomyPlayer economyPlayer;


    private WPlayer(UUID uuid) {
        this.uuid = uuid;
        this.memoryPlayer = new MemoryPlayer(uuid);
        this.name = memoryPlayer.get("name");
        this.economyPlayer = new EconomyPlayer(memoryPlayer);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public EconomyPlayer getEconomyPlayer() {
        return economyPlayer;
    }

    public static WPlayer load(UUID uuid){
        if(players.containsKey(uuid))
            return players.get(uuid);
        WPlayer w = new WPlayer(uuid);
        players.put(uuid, w);
        return w;
    }

    public static WPlayer load(Player p){
        UUID uuid = p.getUniqueId();
        return load(uuid);
    }

    static boolean createPlayer(UUID uuid){
        MemoryPlayer m = new MemoryPlayer(uuid);
        Player p = Bukkit.getPlayer(uuid);
        if(p == null)
            return false;
        if(m.getName() == null) {
            m.set("name", p.getName());
            m.set("uuid", p.getUniqueId().toString());
            m.set("joinDate", p.getFirstPlayed());
            m.set("legion", "neutre");
            m.set("money.changes", 150);
            m.set("money.quota", 0);
            m.set("stats.kills", 0);
            m.set("stats.deaths", 0);
            m.j.saveAll();
            return true;
        }
        return false;
    }
}

