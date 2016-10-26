package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.legion.Legion;
import fr.kwizzy.waroflegions.rank.Rank;
import fr.kwizzy.waroflegions.util.Memory;

/**
 * Par Alexis le 25/10/2016.
 */

public class PlayerLegion extends PlayerData
{

    private Legion legion;
    private int legionPoints;
    private Rank rank;

    public PlayerLegion(Memory memory, WOLPlayer player)
    {
        super(memory, player);
        legion = Legion.getById(Integer.parseInt(memory.get("legion.legion")));
        legionPoints = Integer.parseInt(memory.get("legion.points"));
        rank = Rank.getById(Integer.parseInt(memory.get("legion.rank")));
    }

    public boolean setLegion(Legion l)
    {
        if(getWolPlayer().getPlayerLeveling().getLevel() >= 3 && l != legion)
        {
            legion = l;
            return true;
        }
        return false;
    }
    
    public Legion getLegion()
    {
        return legion;
    }

    public int getLegionPoints()
    {
        return legionPoints;
    }

    public Rank getRank()
    {
        return rank;
    }

    @Override
    public void save()
    {
        memory().put("legion.legion", legion.getId());
        memory().put("legion.rank", rank.getId());
    }


}
