package fr.kwizzy.waroflegions.util.nms;

import org.bukkit.Bukkit;

public enum NMSVer {
    v1_8_R3,
    v1_8_R2, NMSVer;

    public Class<?> getNMSClass(String name) throws ClassNotFoundException {
        return Class.forName("net.minecraft.server." + NMSVer.getCurrent()  + "." +name);
    }


    private static NMSVer current;
    static {
        try {
            String nmsver = Bukkit.getServer().getClass().getPackage().getName();
            nmsver = nmsver.substring(nmsver.lastIndexOf(".") + 1);
            current = valueOf(nmsver);
        }catch(Exception e){
            //Todo : Add message
            current = v1_8_R3;
        }
        System.out.println(current);
    }

    public static NMSVer getCurrent() {
        return current;
    }

    public static Class<?> getCurrentNMSClass(String name) throws ClassNotFoundException {
        return getCurrent().getNMSClass(name);
    }

}
