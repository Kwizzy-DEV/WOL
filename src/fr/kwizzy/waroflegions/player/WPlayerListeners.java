package fr.kwizzy.waroflegions.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Par Alexis le 30/09/2016.
 */

public class WPlayerListeners implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        WPlayer.load(e.getPlayer().getUniqueId());
    }

}
