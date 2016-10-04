package fr.kwizzy.waroflegions.common.essential;

import fr.kwizzy.waroflegions.player.PlayerEss;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Par Alexis le 02/10/2016.
 */

public class EssListeners implements Listener {

    @EventHandler
    public void god(EntityDamageEvent e){
        if(e.getEntity() instanceof org.bukkit.entity.Player) {
            PlayerEss essPlayer = WOLPlayer.get((org.bukkit.entity.Player) e.getEntity()).getEssPlayer();
            if(essPlayer.isGod()) {
                Bukkit.broadcastMessage("no damage");
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void back(PlayerDeathEvent e){
        org.bukkit.entity.Player p = e.getEntity();
        if(p.hasPermission("wol.back")){
            PlayerEss essPlayer = WOLPlayer.get(p).getEssPlayer();
            essPlayer.setBack(p.getLocation());
        }
    }
}
