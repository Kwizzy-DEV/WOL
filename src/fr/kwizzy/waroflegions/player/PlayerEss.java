package fr.kwizzy.waroflegions.player;

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


    PlayerEss(IMemory m, PlayerW w) {
        super(m, w);
        this.player = Bukkit.getPlayer(UUID.fromString(getMemory().get("uuid")));
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isGod() {
        if("null".equalsIgnoreCase(getMemory().get("ess.god"))) {
            setGod(false);
        }
        return Boolean.parseBoolean(getMemory().get("ess.god"));
    }

    public void setGod(boolean god) {
        getMemory().set("ess.god", god);
    }

    public boolean isAfk(){
        if("null".equalsIgnoreCase(getMemory().get("ess.afk"))) {
            setAfk(false);
        }
        return Boolean.parseBoolean(getMemory().get("ess.afk"));
    }

    public void setAfk(boolean b) {
        getMemory().set("ess.afk", b);
    }

    public Location getBack(){
        return BukkitUtils.stringToLocation(getMemory().get("ess.back"));
    }

    public void setBack(Location l){
        getMemory().set("ess.back", BukkitUtils.toStringLocation(l));
    }

    @Override
    public void save() {
        /********************
         Already save in methods
        ********************/
    }
}
