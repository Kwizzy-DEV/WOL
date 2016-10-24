package fr.kwizzy.waroflegions.util.storage;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Pattern;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.inventory.ItemStack;


/**
 * Par Alexis le 18/09/2016.
 */
public interface Storage {


    String PATH = WOL.getInstance().getDataFolder().getAbsolutePath() + File.separator + "data" + File.separator;

    void write(String path, Object t);

    void delete(String path);

    Boolean contain(String path);

    boolean getBoolean(String path);

    Object getInt(String path);

    Double getDouble(String path);

    Long getLong(String path);

    Short getShort(String path);

    Byte getByte(String path);

    Character getChar(String path);

    Float getFloat(String path);

    String getString(String path);

    List<String> getStringList(String path);

    ItemStack getItemStack(String path);

    Object getUnknown(String path);

    void saveAll();

    void clear();

    static File fileFromString(String s, String extension) {
        File file;
        if (s.contains("/")) {
            String[] pf = s.split(Pattern.quote("."));
            String[] parts = pf[0].split(Pattern.quote("/"));
            StringBuilder pathAbsolute = new StringBuilder();
            for (int x = 0; x < parts.length; x++) {
                if (x == parts.length - 1)
                    pathAbsolute.append(parts[x]);
                else
                    pathAbsolute.append(parts[x]).append(File.separator);
            }
            file = new File(PATH, pathAbsolute.toString() + extension);
        } else {
            String[] pf = s.split(Pattern.quote("."));
            file = new File(PATH, pf[0] + extension);
        }
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                WOL.getInstance().getLogger().log(Level.INFO, "Can't create " + file.getName());
            }
        }
        return file;
    }

    static String getFirstPart(String s){
        return s.split(Pattern.quote("."))[0];
    }

    static String[] getParts(String s){
        return s.split(Pattern.quote("."));
    }

    static String getFinalPart(String... part) {
        StringBuilder finalAdress = new StringBuilder();
        if (part.length > 1) {
            for (int x = 1; x < part.length; x++) {
                if (x == part.length - 1) {
                    finalAdress.append(part[x]);
                    continue;
                }
                finalAdress.append(part[x]).append('.');
            }
            return finalAdress.toString();
        }
        return "unknown";
    }
}
