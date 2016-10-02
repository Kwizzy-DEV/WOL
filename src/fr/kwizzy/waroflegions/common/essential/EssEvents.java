package fr.kwizzy.waroflegions.common.essential;

import fr.kwizzy.waroflegions.player.WPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * Par Alexis le 02/10/2016.
 */

public class EssEvents implements Listener {

    @EventHandler
    public void god(EntityDamageEvent e){
        if(e.getEntity() instanceof Player) {
            EssPlayer essPlayer = WPlayer.load((Player) e.getEntity()).getEssPlayer();
            if(essPlayer.isGod()) {
                Bukkit.broadcastMessage("no damage");
                e.setCancelled(true);
            }
        }
    }
}
