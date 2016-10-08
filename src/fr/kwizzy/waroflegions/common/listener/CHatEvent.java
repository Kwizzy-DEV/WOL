package fr.kwizzy.waroflegions.common.listener;

import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.builder.JsonMessageBuidler;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Par Alexis le 04/10/2016.
 */

public class ChatEvent implements Listener {

    @EventHandler
    public void chat(AsyncPlayerChatEvent e){
        e.setCancelled(true);
        String message = e.getMessage();
        WOLPlayer w = WOLPlayer.get(e.getPlayer());
        String[] strings = new String[8];
        strings[0] = "§7Légion: " + "todo";
        strings[1] = "§7Pts de légion: §6" + "todo";
        strings[2] = "§7Niveau: §a" + w.getPlayerLeveling().getLevel();
        strings[3] = "§7Exp: §a" + w.getPlayerLeveling().getExp() + "§e/§a" + w.getPlayerLeveling().getTotalExp();
        strings[4] = "§7Balance: §6" + w.getEconomyPlayer().getMoney();
        strings[5] = "§7Rang: " + "todo";
        strings[6] = "";
        strings[7] = "§6§nClique pour envoyer un message";
        JsonMessageBuidler js = new JsonMessageBuidler();
        js.newJComp("§e" + e.getPlayer().getName() + " §7» §f")
                .addHoverEvent(strings)
                .addCommandSuggest("/msg " + e.getPlayer().getName() + " msg")
                .addText(message);
        js.send();
    }

}
