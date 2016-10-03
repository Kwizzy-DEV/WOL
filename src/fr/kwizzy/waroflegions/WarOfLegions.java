package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.common.essential.Commands;
import fr.kwizzy.waroflegions.common.essential.Listeners;
import fr.kwizzy.waroflegions.common.listener.MessagesJoinQuit;
import fr.kwizzy.waroflegions.player.PlayerW;
import fr.kwizzy.waroflegions.player.listener.WPlayerListeners;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandRegisterer;

import fr.kwizzy.waroflegions.util.storage.JsonStorage;
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

        print("WarOfLegions plugin by _Kwizzy");

        Bukkit.getOnlinePlayers().forEach(PlayerW::load);
    }

    @Override
    public void onDisable() {
        PlayerW.getPlayers().forEach(PlayerW::save);
        JsonStorage.getInstance().saveAll();
    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new WPlayerListeners(), this);

        Bukkit.getPluginManager().registerEvents(new MessagesJoinQuit(), this);
        Bukkit.getPluginManager().registerEvents(new Listeners(), this);
    }

    private void registerCommands(){
        CommandRegisterer.register("eco", new fr.kwizzy.waroflegions.economy.Commands());
        new Commands().init();
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
