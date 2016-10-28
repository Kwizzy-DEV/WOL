package fr.kwizzy.waroflegions.clan;

import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.rank.Rank;
import fr.kwizzy.waroflegions.util.bukkit.ScheduleUtil;
import fr.kwizzy.waroflegions.util.bukkit.classmanager.message.Message;
import fr.kwizzy.waroflegions.util.java.DateUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Par Alexis le 25/10/2016.
 */
class Clan implements IClan
{

    /********************
     Messages
    ********************/

    @Message private static String delete = "Votre clan a été supprimé !";

    private Members<Member> members = new Members<>(this);
    private List<Member> invitedMembers = new ArrayList<>();

    private String name;

    private int timeExpire = 60;
    private long created;

    private boolean deleted = false;
    private ClanMemory clanMemory;

    Clan(String name, long created, Member leader)
    {
        this.members.add(leader);
        this.setLeader(leader);
        this.name = name;
        this.timeExpire = timeExpire();
        this.created = created;
        this.clanMemory = new ClanMemory(name);
        this.checkTime();
    }

    Clan(Members<Member> members, String name, int timeExpire, long created, boolean deleted)
    {

        this.members = members;
        this.name = name;
        this.timeExpire = timeExpire;
        this.created = created;
        this.deleted = deleted;
        this.clanMemory = new ClanMemory(name);
        this.checkTime();
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public Member getLeader()
    {
        return members.getLeader();
    }

    @Override
    public Member getTempLeader()
    {
        return members.getTempLeader();
    }

    @Override
    public String getTime()
    {
        return DateUtils.toString(created);
    }

    @Override
    public void invite(Member m)
    {
        invitedMembers.add(m);
        ScheduleUtil.runIn(20, c -> invitedMembers.remove(m));
    }

    @Override
    public boolean isDeleted()
    {
        return deleted;
    }

    @Override
    public void kick(Member m)
    {
        members.remove(m);
    }

    @Override
    public void promote(Member m)
    {
        members.getLeader().leader = false;
        setLeader(m);
    }

    @Override
    public String getTopPlayer()
    {
        Collection<Member> sort = members.sort();
        StringBuilder topPlayers = new StringBuilder("§aTop des joueurs du clan: ");
        sort.forEach(e -> topPlayers.append("  §e- ").append(e.name).append(": §a").append(e.xp).append(" xp."));
        return topPlayers.toString();
    }

    @Override
    public void delete()
    {
        deleted = true;
        members.sendMessage(getPrefix() + delete);
    }

    @Override
    public String getPrefix()
    {
        return "§9[§e" + name + "§9] §c";
    }

    @Override
    public int getTimeExpire()
    {
        return timeExpire;
    }

    @Override
    public Members<Member> getMembers()
    {
        return members;
    }

    @Override
    public Long getTimeLong()
    {
        return created;
    }

    @Override
    public void setLeader(Member leader)
    {
        leader.leader = true;
        leader.clanName = name;
    }

    @Override
    public boolean isLeader(Member m)
    {
        return (members.contains(m) && m.leader);
    }

    private int timeExpire()
    {
        WOLPlayer wolPlayer = WOLPlayer.get(getLeader().getPlayer());
        Rank rank = wolPlayer.getPlayerLegion().getRank();
        switch (rank)
        {
            case RECRUE:
                timeExpire *= 2;
                break;
            case ASPIRANT:
                timeExpire *= 3;
                break;

            case SENTINELLE:
                timeExpire *= 5;
                break;

            case DEFENSEUR:
                timeExpire *= 7;
                break;

            case CHEVALIER:
                timeExpire *= 10;
                break;

            case CHAMPION:
                timeExpire *= 12;
                break;

            case ASSASSIN:
                timeExpire *= 16;
                break;

            case STRATEGE:
                timeExpire *= 18;
                break;

            case BROYEUR:
                timeExpire *= 20;
                break;

            case HERO:
                timeExpire *= 24;
                break;
            default:
                timeExpire *= 2;
        }
        return timeExpire;
    }

    private void checkTime()
    {
        ScheduleUtil.runWhileOut(5, e -> this.delete(), e -> {
            long seconds = TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - created);
            return seconds >= timeExpire;
        });
    }

    ClanMemory getClanMemory()
    {
        return clanMemory;
    }
}
