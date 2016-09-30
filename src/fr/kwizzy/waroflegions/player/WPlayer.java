package fr.kwizzy.waroflegions.player;

import com.sun.jna.Memory;

import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 30/09/2016.
 */

public class WPlayer {

    private static HashMap<UUID, WPlayer> players = new HashMap<>();

    MemoryPlayer memoryPlayer;

    String name;
    UUID uuid;


    private WPlayer(UUID uuid) {
        this.memoryPlayer = new MemoryPlayer(uuid);
        this.name = memoryPlayer.getName();
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public static WPlayer load(UUID uuid){
        if(players.containsKey(uuid))
            return players.get(uuid);
        WPlayer w = new WPlayer(uuid);
        players.put(uuid, w);
        return w;
    }
}

