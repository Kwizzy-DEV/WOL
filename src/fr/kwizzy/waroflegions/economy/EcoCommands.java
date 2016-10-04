package fr.kwizzy.waroflegions.economy;

import fr.kwizzy.waroflegions.player.PlayerEconomy;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.util.bukkit.command.Command;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandHandler;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import fr.kwizzy.waroflegions.util.java.ArraysUtils;
import fr.kwizzy.waroflegions.util.java.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;

/**
 * Par Alexis le 01/10/2016.
 */

public class EcoCommands implements CommandListener {

    private static String money = "§7Tu as §e%s %s§7.";
    private static String playerNotOnline = "§cLe joueur '§e%s§c' n'est pas en ligne.";
    private static String isNotNumber = "§cL'agument '§e%s§c' n'est pas un chiffre.";
    private static String[] help = ArraysUtils.toArray(
            "§e/eco help - §7Aide sur la commande eco",
            "§e/eco money - §7Regardes ton argent",
            "§e/eco pay <player> <montant> - §7Payer un joueur",
            "§e/eco quota - §7Regardes ton quota",
            "§e/eco give <player> <montant> - §7Donner de l'argent a un joueur",
            "§e/eco remove <player> <montant> - §7Retirer de l'argent a un joueur",
            "§e/eco check <player> - §7Regardes l'argent d'un joueur"
    );

    @CommandHandler(args = "money", sender = Player.class)
    public void money(Command<Player> command){
        Player sender = command.getSender();
        PlayerEconomy ep = WOLPlayer.get(command.getSender()).getEconomyPlayer();
        sender.sendMessage(String.format(money, ep.getMoney(), Economy.MONEY_NAME));
    }

    @CommandHandler(args = "pay", sender = Player.class)
    public void pay(Command<Player> command){
        Player p = command.getSender();
        if(command.singleArg()){
            p.sendMessage("§7/pay §a<joueur> <montant>");
            return;
        }
        PlayerEconomy ep = WOLPlayer.get(command.getSender()).getEconomyPlayer();
        if(!command.isPlayer(command.getArgs()[1])){
            p.sendMessage(String.format(playerNotOnline, command.getArgs()[1]));
            return;
        }
        Player target = command.getPlayer(1);
        int amount;
        try {
            amount = Integer.parseInt(command.getArgs()[2]);
        } catch (NumberFormatException e) {
            p.sendMessage(String.format(isNotNumber, command.getArgs()[2]));
            return;
        }
        ep.transaction(amount, WOLPlayer.get(target));

    }

    @CommandHandler(sender = Player.class)
    public void defaultCommand(Command<Player> command){
        Player sender = command.getSender();
        if(sender.isOp()){
            sender.sendMessage(StringUtils.messageWithLine(help));
            return;
        }
        sender.sendMessage(StringUtils.messageWithLine(
                "§e/eco help - §7Aide sur la commande eco",
                "§e/eco money - §7Regardes ton argent",
                "§e/eco pay <player> - §7Paye un joueur",
                "§e/eco quota - §7Regardes ton quota")
        );
    }

    @CommandHandler(args = "help", sender = Player.class)
    public void help(Command<Player> command){
        defaultCommand(command);
    }

}
