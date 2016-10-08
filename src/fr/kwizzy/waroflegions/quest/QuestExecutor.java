package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.WarOfLegions;
import org.bukkit.Bukkit;
import org.bukkit.event.*;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

/**
 * Par Alexis le 08/10/2016.
 */

class QuestExecutor<T extends Event> implements EventExecutor, Listener {

    private static final Plugin plugin = WarOfLegions.getInstance();

    private IQuestFactory questFactory;
    private Class<T> event;

    public QuestExecutor(Class<T> event) {
        this.event = event;
        Bukkit.getPluginManager().registerEvent(event , this , EventPriority.HIGHEST , this , plugin);
    }

    public void setQuestFactory(IQuestFactory questFactory) {
        this.questFactory = questFactory;
    }

    public void unregister() {
        try{
            ((HandlerList)event.getMethod("getHandlerList").invoke(null)).unregister(this);
        }catch(ReflectiveOperationException e){
            Bukkit.getLogger().log(Level.SEVERE , event.getName() + " havn't getHandlerList method " , e);
        }
    }

    @Override
    public void execute(Listener listener, Event event) throws EventException {

    }
}
