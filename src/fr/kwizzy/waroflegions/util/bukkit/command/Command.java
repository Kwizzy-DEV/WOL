package fr.kwizzy.waroflegions.util.bukkit.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command<T extends CommandSender> {
    private String[] args;
    private T sender;
    private String label;
    private CommandRegisterer registerer;

    public Command(String[] args, T sender, String label , CommandRegisterer registerer) {
        this.args = args;
        this.sender = sender;
        this.label = label;
        this.registerer = registerer;
    }

    public String[] getArgs() {
        return args;
    }

    public T getSender() {
        return sender;
    }

    public String getLabel() {
        return label;
    }

    public CommandRegisterer getRegisterer() {
        return registerer;
    }

    //Shortcuts
    public String[] a() {
        return getArgs();
    }

    public T s() {
        return getSender();
    }

    public String l() {
        return getLabel();
    }

    public CommandRegisterer r() {
        return getRegisterer();
    }

    public boolean singleArg(){
        return args.length == 1;
    }

    public boolean isPlayer(String s){
        return Bukkit.getPlayer(s) != null;
    }

    public boolean isPlayer(int i){
        return getPlayer(i) != null;
    }

    public Player getPlayer(String s){
        return Bukkit.getPlayer(s);
    }

    public Player getPlayer(Integer arg){
        if(arg >= args.length-1)
            return null;
        return Bukkit.getPlayer(args[arg]);
    }
}
