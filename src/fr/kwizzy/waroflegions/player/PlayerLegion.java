package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.legion.Legion;
import fr.kwizzy.waroflegions.rank.Rank;
import fr.kwizzy.waroflegions.util.java.MathsUtils;
import fr.kwizzy.waroflegions.util.storage.Memory;

/**
 * Par Alexis le 25/10/2016.
 */

public class PlayerLegion extends PlayerData
{

    private Legion legion;
    private double legionPoints;
    private Rank rank;

    public PlayerLegion(Memory memory, WOLPlayer player)
    {
        super(memory, player);
        legion = Legion.getById((Integer) memory.get("legion.legion"));
        legionPoints = Double.parseDouble(memory.getJs().getString("legion.points"));
        rank = Rank.getById((Integer) memory.get("legion.rank"));
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

    public double getLegionPoints()
    {
        return MathsUtils.roundDouble(legionPoints, 2);
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
