package fr.kwizzy.waroflegions.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 30/09/2016.
 */

public class WOLPlayer {

    /********************
     VARIABLES
    ********************/

    private static HashMap<UUID, WOLPlayer> players = new HashMap<>();

    PlayerMemory memoryPlayer;

    String name;
    UUID uuid;

    PlayerEconomy economyPlayer;
    PlayerLevel levelPlayer;
    PlayerEss essPlayer;
    PlayerQuest playerQuest;
    PlayerLegion playerLegion;

    /********************
     CONSTRUCTOR
    ********************/

    private WOLPlayer(UUID uuid) {
        players.put(uuid, this);

        this.uuid = uuid;
        this.memoryPlayer = new PlayerMemory(uuid);
        this.name = memoryPlayer.getName();

        this.economyPlayer = new PlayerEconomy(memoryPlayer, this);
        this.levelPlayer = new PlayerLevel(memoryPlayer, this);
        this.essPlayer = new PlayerEss(memoryPlayer, this);
        this.playerQuest = new PlayerQuest(memoryPlayer, this);
        this.playerLegion = new PlayerLegion(memoryPlayer, this);
    }

    /********************
     GETTERS
    ********************/

    public String getName() {
        return name;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(uuid);
    }

    public PlayerEconomy getEconomyPlayer() {
        return economyPlayer;
    }

    public PlayerEss getEssPlayer() {
        return essPlayer;
    }

    public PlayerLevel getPlayerLeveling() {
        return levelPlayer;
    }

    public PlayerQuest getPlayerQuest() {
        return playerQuest;
    }

    public PlayerLegion getPlayerLegion()
    {
        return playerLegion;
    }

    /********************
     STATIC METHODS
    ********************/

    public static WOLPlayer get(UUID uuid){
        if(players.containsKey(uuid))
            return players.get(uuid);
        return new WOLPlayer(uuid);
    }

    public static WOLPlayer get(org.bukkit.entity.Player p){
        UUID uuid = p.getUniqueId();
        return get(uuid);
    }

    public static Collection<WOLPlayer> getPlayers() {
        return players.values();
    }

    public static boolean createPlayer(UUID uuid){
        PlayerMemory m = new PlayerMemory(uuid);
        org.bukkit.entity.Player p = Bukkit.getPlayer(uuid);
        if(p == null)
            return false;
        if(m.getName() == null || "null".equalsIgnoreCase(m.getName())) {
            m.put("name", p.getName());
            m.put("uuid", p.getUniqueId().toString());
            m.put("date", p.getFirstPlayed());
            m.put("legion.legion", 0);
            m.put("legion.points", 0.0);
            m.put("legion.rank", 0);
            m.put("leveling.level", 1);
            m.put("leveling.exp", 0.0);
            m.put("economy.changes", 150);
            m.put("economy.quota", 0);
            m.put("stats.kills", 0);
            m.put("stats.deaths", 0);
            m.getJs().save();
            return true;
        }
        return false;
    }


}

