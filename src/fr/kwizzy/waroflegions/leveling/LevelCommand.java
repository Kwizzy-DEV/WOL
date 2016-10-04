package fr.kwizzy.waroflegions.leveling;

import fr.kwizzy.waroflegions.player.PlayerLevel;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import org.bukkit.entity.Player;

/**
 * Par Alexis le 04/10/2016.
 */

public class LevelCommand implements CommandListener {

    @CommandHandler(args = "test", sender = Player.class)
    public void command(Command<Player> co){
        PlayerLevel playerLeveling = WOLPlayer.get(co.getSender()).getPlayerLeveling();
        playerLeveling.addExp(50);
    }
}
