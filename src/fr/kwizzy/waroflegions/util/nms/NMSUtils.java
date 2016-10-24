package fr.kwizzy.waroflegions.util.nms;

import fr.kwizzy.waroflegions.util.java.Log;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static fr.kwizzy.waroflegions.util.java.Reflection.*;


public class NMSUtils {

    public static Object getHandle(Entity e){
        return getHandleUnsafe(e);
    }

    public static Object getHandle(World w){
        return getHandleUnsafe(w);
    }

    public static Entity getBukkitEntity(Object o){
        return (Entity) invoke(o , "getBukkitEntity");
    }

    public static Object getNMSWorld(Entity e){
        return invoke(getHandle(e) , "getWorld");
    }

    public static void sendPacket(Player p , Object packet){
        invoke(get(NMSUtils.getHandle(p),"playerConnection"),"sendPacket",packet);
    }

    public static Object getHandleUnsafe(Object o){
        return invoke(o , "getHandle");
    }

    public static Object newPacketMap(short mapId, byte[] render) {
        try {
            return newInstance(NMSVer.getCurrentNMSClass("PacketPlayOutMap") , (int)mapId, (byte) 0, new ArrayList<>(), render, 0, 0,
                    128, 128);
        } catch (ClassNotFoundException e) {
            Log.printException(e);
            return null;
        }
    }

    public static Object newPacketEntityMetadata(int entityId, Object dataWatcher, boolean b) {
        try {
            return newInstance(NMSVer.getCurrentNMSClass("PacketPlayOutEntityMetadata") ,entityId, dataWatcher, b);
        } catch (ClassNotFoundException e) {
            Log.printException(e);
            return null;
        }
    }

    public static Object newPacketDisplayScoreboardObjective(int position , String name) {
        try {
            Object packet = newInstance(NMSVer.getCurrentNMSClass("PacketPlayOutScoreboardDisplayObjective"));
            set(packet , "a" , position);
            set(packet , "b" , name);
            return packet;
        } catch (ClassNotFoundException e) {
            Log.printException(e);
            return null;
        }
    }

    public static Object newPacketScoreboardScore(String name , String objectiveName , int value , String action) {
        try {

            Object packet = newInstance(NMSVer.getCurrentNMSClass("PacketPlayOutScoreboardScore"));
            set(packet , "a" ,name);
            set(packet , "b" , objectiveName);
            set(packet , "c" , value);
            set(packet , "d" , getMethod(NMSVer.getCurrentNMSClass("PacketPlayOutScoreboardScore$EnumScoreboardAction") , "valueOf" , new Class<?>[]{String.class}).invoke(null ,action));
            return packet;
        } catch (Exception e) {
            Log.printException(e);
            return null;
        }
    }

    public static Object newPacketScoreboardObjective(String name , String value , String type ,  int mode) {
        try {
            Object packet = newInstance(NMSVer.getCurrentNMSClass("PacketPlayOutScoreboardObjective"));
            set(packet , "a" ,name);
            set(packet , "b" , value);
            set(packet , "c" , getMethod(NMSVer.getCurrentNMSClass("IScoreboardCriteria$EnumScoreboardHealthDisplay") , "valueOf" , new Class<?>[]{String.class}).invoke(null ,type));
            set(packet , "d" , mode);
            return packet;
        } catch (Exception e) {
            Log.printException(e);
            return null;
        }
    }

    public static void setMap(ItemFrame frame, Player p, short mapId) {
        try {
            Object watcher = newDataWatcher();
            invoke(watcher , "a", 0, (byte) 0);
            invoke(watcher , "a", 1, (short) 0);
            invoke(watcher , "a", 8,
                    newInstance(NMSVer.getCurrentNMSClass("ItemStack") ,
                            get(NMSVer.getCurrentNMSClass("Items") , "FILLED_MAP" , Object.class)
                            , 1 , (int)mapId));
            sendPacket(p, newPacketEntityMetadata(frame.getEntityId(), watcher, true));
        } catch (Exception e) {
            Log.printException(e);
        }


    }

    public static Object newDataWatcher() {
        try {
            return newInstance(NMSVer.getCurrentNMSClass("DataWatcher") , new Object[]{null});
        } catch (ClassNotFoundException e) {
            Log.printException(e);
            return null;
        }
    }
}
