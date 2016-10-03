package fr.kwizzy.waroflegions.common.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static fr.kwizzy.waroflegions.util.java.StringUtils.messageWithLine;

/**
 * Par Alexis le 30/09/2016.
 */

public class MessagesJoinQuit implements Listener {


    private static String welcomeMessage0 = "§7Bienvenue §a%s §7sur §eWar Of Legions§7, il y a §e%s §7joueur%s !";
    private static String welcomeMessage1 = "§7Si tu ne connais pas le serveur fait §a/aide§7.";
    private static String welcomeMessage2 = "§7Site: §bwww.waroflegions.fr";
    private static String welcomeMessage3 = "§7Twitter: §b@WarOfLegionsMC";
    private static String welcomeMessage4 = "§7Teamspeak: §bts.waroflegions.fr";

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
        e.setJoinMessage(String.format("§a%s §ea rejoint le jeu !", e.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(String.format("§a%s §ea rejoint le jeu !", e.getPlayer().getName()));
    }


}
