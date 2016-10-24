package fr.kwizzy.waroflegions.util.bukkit.safe;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * Par Alexis le 10/07/2016.
 */

public abstract class SafeTeleportation
{

    private static String teleport = "§7Tu vas être téléporté dans §a%s secondes§7.\n§7Ne bouge pas !";
    private static String teleportSuccess = "§7Vous avez été téléporté !";
    private static String cancelTeleport = "§cLa téléportation a été annulé !";

    private static final int TELEPORTATION_TIME = 5;

    Player p;
    int pX;
    int pZ;
    Location l;

    public SafeTeleportation(Player p, Location l)
    {
        this.p = p;
        this.l = l;
    }

    public void isTeleported(){
        pX =(int) p.getLocation().getX();
        pZ = (int) p.getLocation().getZ();
        p.sendMessage(String.format(teleport, TELEPORTATION_TIME));
        new BukkitRunnable(){
            int i = TELEPORTATION_TIME;
            @Override
            public void run()
            {
                if(isCancelled()) {
                    this.cancel();
                    return;
                }
                if(isTeleported(i)){
                    this.cancel();
                    return;
                }
                i--;
            }
        }.runTaskTimer(WOL.getInstance(), 0, 20);
    }

    private boolean isCancelled(){
        if(!equalPlayerCoords()){
            teleported(TeleportationEvent.FAIL);
            p.sendMessage(cancelTeleport);
            return true;
        }
        return false;
    }

    private boolean isTeleported(int i){
        if(i == 0){
            teleported(TeleportationEvent.SUCCESS);
            p.sendMessage(teleportSuccess);
            p.teleport(l);
            return true;
        }
        return false;
    }


    private boolean equalPlayerCoords(){
        return pX == (int) p.getLocation().getX() && pZ == (int) p.getLocation().getZ();

    }

    public abstract void teleported(TeleportationEvent e);


    public enum TeleportationEvent{
        SUCCESS,
        FAIL,
    }



}
