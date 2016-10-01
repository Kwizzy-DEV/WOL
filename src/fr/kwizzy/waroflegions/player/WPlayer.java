package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.economy.EconomyPlayer;
import org.bukkit.entity.Player;

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
}

