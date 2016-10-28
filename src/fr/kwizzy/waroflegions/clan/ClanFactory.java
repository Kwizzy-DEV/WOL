package fr.kwizzy.waroflegions.clan;

import org.bukkit.entity.Player;

import java.util.HashMap;


/**
 * Par Alexis le 25/10/2016.
 */

public class ClanFactory
{

    static HashMap<String, IClan> clanList = new HashMap<>();

    private ClanFactory()
    {}

    public static IClan newClan(Player leader, String clanName)
    {
        IClan clan = clanList.get(clanName);
        if(clan != null && !clan.isDeleted())
            return clanList.get(clanName);

        ClanMemory clanMemory = new ClanMemory(clanName);
        if(clanMemory.getName() == null || "null".equalsIgnoreCase(clanMemory.getName()))
            clan = new Clan(clanName, System.currentTimeMillis(), Member.getMember(leader));
        else
            clan = new Clan(clanMemory.getMembers(), clanMemory.getName(), clanMemory.getTimeExpire(), clanMemory.getTime(), false);

        clanList.put(clanName, clan);
        return clan;
    }

    public static IClan getClan(String name)
    {
        return clanList.get(name);
    }

    public static IClan getClan(Player p)
    {
        return IClan.getClan(Member.getMember(p).name);
    }
}
