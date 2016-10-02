package fr.kwizzy.waroflegions.common.essential;

import fr.kwizzy.waroflegions.player.MemoryPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Par Alexis le 02/10/2016.
 */

public class EssPlayer {

    private MemoryPlayer memory;
    private Player player;


    public EssPlayer(MemoryPlayer memoryPlayer) {
        this.memory = memoryPlayer;
        this.player = Bukkit.getPlayer(UUID.fromString(memory.get("uuid")));
    }

    public Player getPlayer() {
        return player;
    }

    boolean isGod() {
        if(memory.get("ess.god").equalsIgnoreCase("null")) {
            setGod(false);
        }
        return Boolean.parseBoolean(memory.get("ess.god"));
    }

    void setGod(boolean god) {
        memory.set("ess.god", god);
    }
}
