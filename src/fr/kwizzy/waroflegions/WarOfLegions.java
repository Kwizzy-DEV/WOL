package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.common.essential.EssCommands;
import fr.kwizzy.waroflegions.common.essential.EssListeners;
import fr.kwizzy.waroflegions.common.listener.ChatEvent;
import fr.kwizzy.waroflegions.common.listener.MessagesJoinQuit;
import fr.kwizzy.waroflegions.economy.EcoCommands;
import fr.kwizzy.waroflegions.player.PlayerData;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.player.listener.InitializerListener;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandRegisterer;

import fr.kwizzy.waroflegions.util.bukkit.noteblocklib.SongUtils;
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

        Bukkit.getOnlinePlayers().forEach(WOLPlayer::get);
    }

    @Override
    public void onDisable() {

        PlayerData.saveAll();
        SongUtils.clearSongs();
        JsonStorage.getInstance().saveAll();
    }

    private void registerEvents(){
        Bukkit.getPluginManager().registerEvents(new InitializerListener(), this);

        Bukkit.getPluginManager().registerEvents(new MessagesJoinQuit(), this);
        Bukkit.getPluginManager().registerEvents(new ChatEvent(), this);
        Bukkit.getPluginManager().registerEvents(new EssListeners(), this);
    }

    private void registerCommands(){
        CommandRegisterer.register("eco", new EcoCommands());
        new EssCommands().init();
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
