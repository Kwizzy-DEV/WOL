package fr.kwizzy.waroflegions.player.listener;

import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.Title;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Par Alexis le 30/09/2016.
 */

public class InitializerListener implements Listener {

    private static String welcomeTitle = "§cWarOfLegions";
    private static String welcomeSubtitle = "§eUne ère nouvelle...";

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        org.bukkit.entity.Player p = e.getPlayer();
        if(WOLPlayer.createPlayer(e.getPlayer().getUniqueId())){
            Title.sendTitle(welcomeTitle, welcomeSubtitle, 2, 3, 2, p);
        }
    }

}
