package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.storage.Memory;

import java.util.UUID;

/**
 * Par Alexis le 30/09/2016.
 */

class PlayerMemory extends Memory
{

    PlayerMemory(UUID uuid) {
        super("data/players/" + uuid.toString());
    }

    public String getName(){
        return (String) get("name");
    }
}
