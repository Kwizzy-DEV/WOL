package fr.kwizzy.waroflegions.util.nms.tracker;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Set;

import static fr.kwizzy.waroflegions.util.java.Reflection.*;
import static fr.kwizzy.waroflegions.util.nms.NMSUtils.*;


public class TrackerUtils {

    public static void hook(Entity e , EventHashSet.HashSetEventHandler<Player> eventHandler){
        Object trackerEntry = getTrackerEntry(e);
        EventHashSet<Object> set = new EventHashSet<>(new EventHashSet.HashSetEventHandler<Object>() {
            @Override
            public boolean onAdd(Object o) {
                return eventHandler.onAdd((Player) getBukkitEntity(o));
            }

            @Override
            public boolean onRemove(Object o) {
                return eventHandler.onRemove(getBukkitEntity(o));
            }
        });
        set.addAll(getTrackedPlayers(trackerEntry));
        setTrackedPlayers(trackerEntry, set);
    }

    public static Object getTrackerEntry(Entity e){
        return invoke(get(get(getNMSWorld(e) , "tracker"),"trackedEntities"),"get" , e.getEntityId());
    }

    public static Set<?> getTrackedPlayers(Object trackerEntry){
        return (Set<?>) get(trackerEntry , "trackedPlayers");
    }

    public static void setTrackedPlayers(Object trackerEntry , Set<?> s){
        set(trackerEntry , "trackedPlayers" , s);
    }
}
