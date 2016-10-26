package fr.kwizzy.waroflegions.clan;

import org.bukkit.entity.Player;

/**
 * Par Alexis le 25/10/2016.
 */
public interface IClan
{

    String getName();

    Member getLeader();

    Member getTempLeader();

    String getTime();

    Long getTimeLong();

    int getTimeExpire();

    void invite(Member m);

    void kick(Member m);

    void promote(Member m);

    String getTopPlayer();

    void delete();

    boolean isDeleted();

    Members<Member> getMembers();

    void setLeader(Member m);

    boolean isLeader(Member m);

    static IClan getClan(String name)
    {

        return ClanFactory.getClan(name);
    }

    static IClan getClanByPlayer(Player player)
    {

        return ClanFactory.getClan(player);
    }
}
