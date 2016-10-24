package fr.kwizzy.waroflegions;

import fr.kwizzy.waroflegions.common.essential.EssCommands;
import fr.kwizzy.waroflegions.player.PlayerData;
import fr.kwizzy.waroflegions.player.WOLPlayer;
import fr.kwizzy.waroflegions.quest.QuestManager;
import fr.kwizzy.waroflegions.quest.list.Quests;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandListener;
import fr.kwizzy.waroflegions.util.bukkit.command.CommandRegisterer;

import fr.kwizzy.waroflegions.util.bukkit.noteblocklib.SongUtils;
import fr.kwizzy.waroflegions.util.bukkit.register.AutoRegister;
import fr.kwizzy.waroflegions.util.storage.JsonStorage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Par Alexis le 30/09/2016.
 */

public class WOL extends JavaPlugin {

    private static WOL instance;

    @Override
    public void onEnable() {

        setInstance(this);
        register();

        print("WOL plugin by _Kwizzy");

        Bukkit.getOnlinePlayers().forEach(WOLPlayer::get);
    }

    @Override
    public void onDisable() {
        PlayerData.saveAll();
        JsonStorage.getInstance().saveAll();
        SongUtils.clearSongs();
    }

    private void register()
    {
        AutoRegister a = new AutoRegister("fr", this);
        /********************
         Event 
        ********************/
        AutoRegister.registerEvents("fr", this);
        /********************
         Commands 
        ********************/
        a.registerAction(CommandListener.class, t -> CommandRegisterer.register(t.getCommand(), t));
        new EssCommands().init();
        /********************
         Quests
        ********************/
        QuestManager.register(new Quests());

    }


    private static synchronized void setInstance(WOL instance) {
        WOL.instance = instance;
    }

    public static synchronized WOL getInstance() {
        return instance;
    }

    public void print(String s){
        ConsoleCommandSender console = getServer().getConsoleSender();
        console.sendMessage(ChatColor.YELLOW + "[" + ChatColor.AQUA + "WOL" + ChatColor.YELLOW + "] : " + ChatColor.GREEN + s);
    }
}
