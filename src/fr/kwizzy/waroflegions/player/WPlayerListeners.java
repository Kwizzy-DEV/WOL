package fr.kwizzy.waroflegions.player;

import fr.kwizzy.waroflegions.util.bukkit.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static fr.kwizzy.waroflegions.util.java.StringUtils.messageWithLine;

/**
 * Par Alexis le 30/09/2016.
 */

public class WPlayerListeners implements Listener {

    private static final String welcomeMessage = "§eBienvenue §a%s §esur War Of Legions, il y a §a%s joueurs !";
    private static final String welcomeTitle = "§cWarOfLegions";
    private static final String welcomeSubtitle = "§eUne ère nouvelle...";

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if(WPlayer.createPlayer(e.getPlayer().getUniqueId())){
            p.sendMessage(messageWithLine(String.format(welcomeMessage, p.getName(), Bukkit.getOnlinePlayers().size()), String.format("§eSi tu ne connais pas le serveur fait §a/aide.")));
            Title.sendTitle(welcomeTitle, welcomeSubtitle, 35, 20*3, 35, p);
        }
    }

}
