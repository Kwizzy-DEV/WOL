package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.IMemory;
import fr.kwizzy.waroflegions.util.ISaveable;

/**
 * Par Alexis le 03/10/2016.
 */

public abstract class PlayerData implements ISaveable {

    IMemory memory;
    PlayerW player;

    public PlayerData(IMemory memory, PlayerW player) {
        this.memory = memory;
        this.player = player;
    }

    public IMemory getMemory() {
        return memory;
    }

    public PlayerW getWolPlayer() {
        return player;
    }
}
