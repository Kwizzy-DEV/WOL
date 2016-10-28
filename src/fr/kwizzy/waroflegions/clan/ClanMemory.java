package fr.kwizzy.waroflegions.clan;

import fr.kwizzy.waroflegions.util.storage.Memory;
import fr.kwizzy.waroflegions.util.storage.ISaveable;
import org.json.JSONArray;

/**
 * Par Alexis le 25/10/2016.
 */

public class ClanMemory extends Memory implements ISaveable
{

    String clanName;

    public ClanMemory(String clanName)
    {
        super("data/clans/" + clanName);
        this.clanName = clanName;
    }


    public void save()
    {
        IClan clan = ClanFactory.getClan(clanName);

        if(clan.isDeleted())
        {
            getJs().delete();
            return;
        }

        put("members", clan.getMembers().toJsonArray());
        put("created", clan.getTimeLong());
        put("expire", clan.getTimeExpire());
        put("name", clan.getName());
    }

    Members<Member> getMembers()
    {
        Members<Member> list = new Members<>();
        JSONArray members = getJson().getJSONArray("members");
        members.forEach(e -> {
            Member member = getGson().fromJson(e.toString(), Member.class);
            if(member != null)
                list.add(member);
        });
        return list;
    }

    Long getTime()
    {
        return (Long) get("created");
    }
    Integer getTimeExpire()
    {
        return (Integer) get("expire");
    }

    String getName()
    {
        return (String) get("name");
    }



}
