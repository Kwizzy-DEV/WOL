package fr.kwizzy.waroflegions.common.essential;

import fr.kwizzy.waroflegions.player.PlayerData;
import fr.kwizzy.waroflegions.player.PlayerW;
import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.bukkit.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class PlayerEss extends PlayerData{


    private Player player;


    public PlayerEss(IMemory m, PlayerW w) {
        super(m, w);
        this.player = Bukkit.getPlayer(UUID.fromString(getMemory().get("uuid")));
    }

    public Player getPlayer() {
        return player;
    }

    boolean isGod() {
        if("null".equalsIgnoreCase(getMemory().get("ess.god"))) {
            setGod(false);
        }
        return Boolean.parseBoolean(getMemory().get("ess.god"));
    }

    void setGod(boolean god) {
        getMemory().set("ess.god", god);
    }

    boolean isAfk(){
        if("null".equalsIgnoreCase(getMemory().get("ess.afk"))) {
            setAfk(false);
        }
        return Boolean.parseBoolean(getMemory().get("ess.afk"));
    }

    void setAfk(boolean b) {
        getMemory().set("ess.afk", b);
    }

    Location getBack(){
        return BukkitUtils.stringToLocation(getMemory().get("ess.back"));
    }

    void setBack(Location l){
        getMemory().set("ess.back", BukkitUtils.toStringLocation(l));
    }

    @Override
    public void save() {
        /********************
         Already save in methods 
        ********************/
    }
}
