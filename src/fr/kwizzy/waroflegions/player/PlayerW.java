package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.common.essential.PlayerEss;
import fr.kwizzy.waroflegions.economy.PlayerEconomy;
import fr.kwizzy.waroflegions.level.PlayerLevel;
import fr.kwizzy.waroflegions.util.Saveable;
import org.bukkit.Bukkit;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 30/09/2016.
 */

public class PlayerW implements Saveable {

    private static HashMap<UUID, PlayerW> players = new HashMap<>();

    PlayerMemory memoryPlayer;

    String name;
    UUID uuid;

    PlayerEconomy economyPlayer;
    PlayerLevel levelPlayer;
    PlayerEss essPlayer;


    private PlayerW(UUID uuid) {
        this.uuid = uuid;
        this.memoryPlayer = new PlayerMemory(uuid);
        this.name = memoryPlayer.get("name");

        this.economyPlayer = new PlayerEconomy(memoryPlayer, this);
        this.levelPlayer = new PlayerLevel(memoryPlayer, this);
        this.essPlayer = new PlayerEss(memoryPlayer, this);
    }

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public PlayerEconomy getEconomyPlayer() {
        return economyPlayer;
    }

    public PlayerEss getEssPlayer() {
        return essPlayer;
    }

    @Override
    public void save() {
        economyPlayer.save();
        levelPlayer.save();
    }

    public static PlayerW load(UUID uuid){
        if(players.containsKey(uuid))
            return players.get(uuid);
        PlayerW w = new PlayerW(uuid);
        players.put(uuid, w);
        return w;
    }

    public static PlayerW load(org.bukkit.entity.Player p){
        UUID uuid = p.getUniqueId();
        return load(uuid);
    }

    public static Collection<PlayerW> getPlayers() {
        return players.values();
    }

    public static boolean createPlayer(UUID uuid){
        PlayerMemory m = new PlayerMemory(uuid);
        org.bukkit.entity.Player p = Bukkit.getPlayer(uuid);
        if(p == null)
            return false;
        if(m.getName() == null || m.getName().equalsIgnoreCase("null")) {
            m.set("name", p.getName());
            m.set("uuid", p.getUniqueId().toString());
            m.set("date", p.getFirstPlayed());
            m.set("legion.legion", "neutre");
            m.set("legion.rank", "mercenaire");
            m.set("leveling.level", 1);
            m.set("leveling.exp", 0);
            m.set("economy.changes", 150);
            m.set("economy.quota", 0);
            m.set("stats.kills", 0);
            m.set("stats.deaths", 0);
            m.j.saveAll();
            return true;
        }
        return false;
    }


}

