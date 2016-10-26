package fr.kwizzy.waroflegions.clan;

import com.google.gson.Gson;
import fr.kwizzy.waroflegions.util.Memory;
import fr.kwizzy.waroflegions.util.ISaveable;
import fr.kwizzy.waroflegions.util.storage.JSONStorage;
import org.json.JSONArray;
import org.json.JSONObject;

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
        return (Long) getObject("created");
    }
    Integer getTimeExpire()
    {
        return (Integer) getObject("expire");
    }

    String getName()
    {
        return (String) get("name");
    }



}
