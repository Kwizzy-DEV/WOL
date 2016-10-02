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

    private static final String welcomeMessage0 = "§7Bienvenue §a%s §7sur §eWar Of Legions§7, il y a §e%s §7joueur%s !";
    private static final String welcomeMessage1 = "§7Si tu ne connais pas le serveur fait §a/aide§7.";
    private static final String welcomeMessage2 = "§7Site: §bwww.waroflegions.fr";
    private static final String welcomeMessage3 = "§7Twitter: §b@WarOfLegionsMC";
    private static final String welcomeMessage4 = "§7Teamspeak: §bts.waroflegions.fr";

    private static final String welcomeTitle = "§cWarOfLegions";
    private static final String welcomeSubtitle = "§eUne ère nouvelle...";

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        int py = Bukkit.getOnlinePlayers().size();
        p.sendMessage(messageWithLine(
                String.format(welcomeMessage0, p.getName(), py, (py > 1) ? "s" : ""),
                welcomeMessage1,
                "",
                welcomeMessage2,
                welcomeMessage3,
                welcomeMessage4
        ));
        if(WPlayer.createPlayer(e.getPlayer().getUniqueId())){
            Title.sendTitle(welcomeTitle, welcomeSubtitle, 2, 3, 2, p);
        }
    }

}
