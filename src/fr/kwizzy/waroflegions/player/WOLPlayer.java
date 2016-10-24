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

    /********************
     CONSTRUCTOR
    ********************/

    private WOLPlayer(UUID uuid) {
        players.put(uuid, this);

        this.uuid = uuid;
        this.memoryPlayer = new PlayerMemory(uuid);
        this.name = memoryPlayer.get("name");

        this.economyPlayer = new PlayerEconomy(memoryPlayer, this);
        this.levelPlayer = new PlayerLevel(memoryPlayer, this);
        this.essPlayer = new PlayerEss(memoryPlayer, this);
        this.playerQuest = new PlayerQuest(memoryPlayer, this);
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

