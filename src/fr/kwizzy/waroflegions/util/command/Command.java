package fr.kwizzy.waroflegions.util.command;

import org.bukkit.command.CommandSender;

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
}
