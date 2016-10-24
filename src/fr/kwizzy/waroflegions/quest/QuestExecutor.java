package fr.kwizzy.waroflegions.quest;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

/**
 * Par Alexis le 08/10/2016.
 */

class QuestExecutor<T extends Event> implements EventExecutor, Listener {

    private static final Plugin plugin = WOL.getInstance();

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
    @SuppressWarnings("unchecked")
    public void execute(Listener listener, Event event) throws EventException {
        if(event.getClass().equals(event.getClass())) {
            Player player = getPlayer(event);
            if (player != null) {
                if (!player.equals(questFactory.getPlayerQuest().getPlayer()))
                    return;
            } else
                return;
            int count = 0;
            try {
                count = (int) questFactory.getQuest().getTester().apply((T) event);
            } catch (Exception e) {
                return;
            }
            if (count != 0)
                questFactory.call(count);
        }
    }

    private static Player getPlayer(Event event){
        Player player = null;
        if(event instanceof PlayerEvent)
            player = ((PlayerEvent)event).getPlayer();
        else if(event instanceof PlayerLeashEntityEvent)
            player = ((PlayerLeashEntityEvent)event).getPlayer();
        else if(event instanceof EntityEvent && ((EntityEvent)event).getEntityType() == EntityType.PLAYER)
            player = (Player)((EntityEvent)event).getEntity();
        else if(event instanceof EnchantItemEvent)
            player = ((EnchantItemEvent)event).getEnchanter();
        else if(event instanceof InventoryCloseEvent)
            player = (Player) ((InventoryCloseEvent)event).getPlayer();
        else if(event instanceof InventoryOpenEvent)
            player = (Player) ((InventoryOpenEvent)event).getPlayer();
        else if(event instanceof InventoryInteractEvent)
            player = (Player) ((InventoryInteractEvent)event).getWhoClicked();
        else if(event instanceof BlockBreakEvent)
            player = ((BlockBreakEvent)event).getPlayer();
        else if(event instanceof BlockPlaceEvent)
            player = ((BlockPlaceEvent)event).getPlayer();
        else if(event instanceof FurnaceExtractEvent)
            player = ((FurnaceExtractEvent)event).getPlayer();
        else if(event instanceof EntityDeathEvent)
            player = ((EntityDeathEvent)event).getEntity().getKiller();
        return player;
    }

}
