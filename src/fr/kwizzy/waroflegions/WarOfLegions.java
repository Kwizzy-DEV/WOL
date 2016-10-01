package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.common.listener.MessagesJoinQuit;
import fr.kwizzy.waroflegions.economy.EconomyCommands;
import fr.kwizzy.waroflegions.player.WPlayerListeners;
import fr.kwizzy.waroflegions.util.command.CommandRegisterer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

/**
 * Par Alexis le 30/09/2016.
 */

public class WarOfLegions extends JavaPlugin {

    private static WarOfLegions instance;

    @Override
    public void onEnable() {

        setInstance(this);
        registerEvents();
        registerCommands();

    }

    @Override
    public void onDisable() {

    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new WPlayerListeners(), this);
        Bukkit.getPluginManager().registerEvents(new MessagesJoinQuit(), this);
    }

    private void registerCommands(){
        CommandRegisterer.register("eco", new EconomyCommands());
    }

    private static void setInstance(WarOfLegions instance) {
        WarOfLegions.instance = instance;
    }

    public static WarOfLegions getInstance() {
        return instance;
    }

    public void print(String s){
        Bukkit.getLogger().log(Level.INFO, s);
    }
}
