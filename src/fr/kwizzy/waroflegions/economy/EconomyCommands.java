package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.WPlayer;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import static fr.kwizzy.waroflegions.util.java.StringUtils.*;

/**
 * Par Alexis le 01/10/2016.
 */

public class EconomyCommands implements CommandListener {

    private static final String money = "§7Tu as §e%s %s§7.";
    private static final String playerNotOnline = "§cLe joueur '§e%s§c' n'est pas en ligne.";
    private static final String isNotNumber = "§cL'agument '§e%s§c' n'est pas un chiffre.";
    private static final String notEnoughtMoney = "§cTu n'as pas assez d'argent.";

    @CommandHandler(args = {"money"}, sender = Player.class)
    public void money(Command<Player> command){
        Player sender = command.getSender();
        EconomyPlayer ep = WPlayer.load(command.getSender()).getEconomyPlayer();
        sender.sendMessage(messageWithLine(String.format(money, ep.getMoney(), Economy.MONEY_NAME)));
    }

    @CommandHandler(args = {"pay", CommandHandler.UNDEFINED, }, sender = Player.class)
    public void pay(Command<Player> command){
        Player sender = command.getSender();
        EconomyPlayer ep = WPlayer.load(command.getSender()).getEconomyPlayer();
        Bukkit.broadcastMessage("" + command.getArgs()[1]);
        Player p = Bukkit.getPlayer(command.getArgs()[1]);
        if(p == null){
            sender.sendMessage(messageWithLine(String.format(playerNotOnline, command.getArgs()[1])));
            return;
        }
        int money = 0;
        try {
            money = Integer.parseInt(command.getArgs()[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(messageWithLine(String.format(isNotNumber, command.getArgs()[2])));
            return;
        }
        if(!ep.hasMoney(money)){
            sender.sendMessage(messageWithLine(notEnoughtMoney));
            return;
        }
        ep.remove(money);
        WPlayer.load(p).getEconomyPlayer().add(money);
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
                    "§e/eco give <player> <montant> - §7Donner de l'argent a un joueur",
                    "§e/eco remove <player> <montant> - §7Retirer de l'argent a un joueur",
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
