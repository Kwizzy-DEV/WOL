package fr.kwizzy.waroflegions.common.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Par Alexis le 30/09/2016.
 */

public class MessagesJoinQuit implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        e.setJoinMessage(String.format("§a%s §ea rejoint le jeu !", e.getPlayer().getName()));
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        e.setQuitMessage(String.format("§a%s §ea rejoint le jeu !", e.getPlayer().getName()));
    }


}
