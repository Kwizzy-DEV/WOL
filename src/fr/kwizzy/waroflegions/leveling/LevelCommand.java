package fr.kwizzy.waroflegions.leveling;

import fr.kwizzy.waroflegions.player.PlayerLevel;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/**
 * Par Alexis le 04/10/2016.
 */

public class LevelCommand implements CommandListener {

    private static String isNotNumber = "§cL'agument '§e%s§c' n'est pas un chiffre.";

    @CommandHandler(args = "addexp", sender = Player.class)
    public void command(Command<Player> co){
        Player p = co.getSender();
        PlayerLevel playerLeveling = WOLPlayer.get(co.getSender()).getPlayerLeveling();
        String[] a = co.a();
        if(a.length < 2){
           p.sendMessage("§7/level addexp §a<montant>");
            return;
        }
        int i ;
        try {
            i = Integer.parseInt(a[1]);
        } catch (NumberFormatException e) {
            p.sendMessage(String.format(isNotNumber, a[1]));
            return;
        }
        playerLeveling.addExp(i);
    }

    @CommandHandler(args = "exp", sender = Player.class)
    public void exp(Command<Player> co){
        new LevelingVisualizer(co.getSender()).show();
    }
}
