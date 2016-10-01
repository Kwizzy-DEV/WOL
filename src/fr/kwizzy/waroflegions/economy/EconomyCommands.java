package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.WPlayer;
import fr.kwizzy.waroflegions.util.command.Command;
import fr.kwizzy.waroflegions.util.command.CommandHandler;
import fr.kwizzy.waroflegions.util.command.CommandListener;
import org.bukkit.entity.Player;

import static fr.kwizzy.waroflegions.util.java.StringUtils.*;

/**
 * Par Alexis le 01/10/2016.
 */

public class EconomyCommands implements CommandListener {

    @CommandHandler(args = {"money"}, sender = Player.class)
    public void money(Command<Player> command){
        Player sender = command.getSender();
        EconomyPlayer ep = WPlayer.load(command.getSender()).getEconomyPlayer();
        sender.sendMessage(messageWithLine(String.format("§eTu as %s %s", ep.getMoney(), Economy.money_name)));
    }

    @CommandHandler(args = {}, sender = Player.class)
    public void defaultCommand(Command<Player> command){
        Player sender = command.getSender();
        if(sender.isOp()){
            sender.sendMessage(messageWithLine(
                    "§e/eco help - §7Aide sur la commande eco",
                    "§e/eco money - §7Regardes ton argent",
                    "§e/eco pay <player> - §7Paye un joueur",
                    "§e/eco quota - §7Regardes ton quota",
                    "§e/eco give <player> <amount> - §7Donner de l'argent a un joueur",
                    "§e/eco remove <player> <amount> - §7Retirer de l'argent a un joueur",
                    "§e/eco check <player> - §7Regardes l'argent d'un joueur"
            ));
            return;
        }
        sender.sendMessage(messageWithLine(
                "§e/eco help - §7Aide sur la commande eco",
                "§e/eco money - §7Regardes ton argent",
                "§e/eco pay <player> - §7Paye un joueur",
                "§e/eco quota - §7Regardes ton quota")
        );

    }

    @CommandHandler(args = {"help"}, sender = Player.class)
    public void help(Command<Player> command){
        defaultCommand(command);
    }

}
