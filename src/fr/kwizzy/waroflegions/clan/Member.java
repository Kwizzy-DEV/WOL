package fr.kwizzy.waroflegions.clan;

import com.google.gson.Gson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Comparator;
import java.util.HashMap;
import java.util.UUID;

/**
 * Par Alexis le 25/10/2016.
 */

public class Member implements Comparator<Member>, Comparable<Member>
{

    static Gson g = new Gson();
    static HashMap<UUID, Member> members = new HashMap<>();

    boolean leader = false;
    boolean tempLeader = false;
    boolean online;
    double xp = 0;
    String name = "none";
    String clanName;
    UUID uuid;

    private Member(boolean leader, boolean tempLeader, boolean online, double xp, String name, String clanName, UUID uuid)
    {
        this.leader = leader;
        this.tempLeader = tempLeader;
        this.online = online;
        this.xp = xp;
        this.name = name;
        this.clanName = clanName;
        this.uuid = uuid;
    }

    static Member getMember(Player p)
    {
        Member member = members.get(p.getUniqueId());
        if(member == null)
            return new Member(false, false, true, 0, p.getName(), "", p.getUniqueId());
        return member;
    }

    @Override
    public int compareTo(Member o)
    {
        return compare(this, o);
    }

    @Override
    public int compare(Member o1, Member o2)
    {
        return o1.xp < o2.xp ? -1 : 1;
    }

    public String toJson()
    {
        return g.toJson(this);
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(uuid);
    }

    public void sendMessage(String message)
    {
        if(online)
            getPlayer().sendMessage(message);
    }

    @Override
    public String toString()
    {
        return "Member{" +
                "leader=" + leader +
                ", tempLeader=" + tempLeader +
                ", online=" + online +
                ", xp=" + xp +
                ", name='" + name + '\'' +
                ", clanName='" + clanName + '\'' +
                ", uuid=" + uuid +
                '}';
    }
}
