package fr.kwizzy.waroflegions.clan;

import com.google.gson.Gson;
import org.bukkit.entity.Player;
import org.json.JSONArray;


import java.util.*;
import java.util.stream.Collectors;

/**
 * Par Alexis le 25/10/2016.
 */

public class Members<T extends Member> extends AbstractList<T> implements Iterable<T>
{
    IClan clan;
    private final T[] list;

    public Members(IClan clan, T... a)
    {
        this.clan = clan;
        this.list = a;
    }

    public Members(T... a)
    {
        this.list = a;
    }

    public IClan getClan()
    {
        return clan;
    }

    public T getMember(String name)
    {
        for (T t : list)
        {
            if(t.name.equalsIgnoreCase(name))
                return t;
        }
        return null;
    }

    public T getMember(Player player)
    {
        for (T t : list)
        {
            if(t.uuid.equals(player.getUniqueId()))
                return t;
        }
        return null;
    }

    public T getMember(UUID uuid)
    {
        for (T t : list)
        {
            if(t.uuid.equals(uuid))
                return t;
        }
        return null;
    }

    public T getLeader()
    {
        for (T t : list)
        {
            if(t.leader)
                return t;
        }
        return getTempLeader();
    }

    public T getTempLeader()
    {
        for (T t : list)
        {
            if(t.tempLeader)
                return t;
        }
        return null;
    }

    public Collection<T> getOnlinePlayers()
    {
        return this.stream().filter(e -> e.online).collect(Collectors.toList());
    }

    public Collection<T> getOfflinePlayers()
    {
        return this.stream().filter(e -> !e.online).collect(Collectors.toList());
    }

    public int getNumberOnline()
    {
        return getOnlinePlayers().size();
    }

    public boolean isLeader(Player p)
    {
        T a = getLeader();
        return a.uuid == p.getUniqueId();
    }

    public double getTotalExp()
    {
        double exp = 0;
        for (T t : list)
        {
            exp += t.xp;
        }
        return exp;
    }

    public Collection<Member> sort()
    {
        List<Member> collection = new ArrayList<>(this);
        collection.sort(Member::compareTo);
        return collection;
    }

    public void sendMessage(String s)
    {
        getOnlinePlayers().forEach(e -> e.sendMessage(s));
    }

    public void sendMessageExceptionForLeader(String s, String sLeader)
    {
        getOnlinePlayers().forEach(e -> {
            if(!e.leader || !e.tempLeader)
                e.sendMessage(s);
            else
                e.sendMessage(sLeader);
        });
    }

    @Override
    public T get(int index)
    {
        return list[index];
    }

    @Override
    public T set(int index, T element) {
        T oldValue = list[index];
        list[index] = element;
        return oldValue;
    }

    @Override
    public int size()
    {
        return list.length;
    }

    JSONArray toJsonArray()
    {
        return new JSONArray(new Gson().toJson(this));
    }
}
