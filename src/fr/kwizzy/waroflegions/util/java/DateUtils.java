package fr.kwizzy.waroflegions.util.java;

import java.util.concurrent.TimeUnit;

/**
 * Par Alexis le 30/09/2016.
 */

public class DateUtils {

    public static String toString(Long millis)
    {
        Long diff = System.currentTimeMillis() - millis;
        long l = TimeUnit.SECONDS.toSeconds(diff);

        long hours = l / 3600;
        long minutes = (l % 3600) / 60;
        long seconds = l % 60;

        return String.format("%s heure%s %s minute%s et %s seconde%s",
                Long.toString(hours), (hours > 1) ? "s" : "",
                Long.toString(minutes), (minutes > 1) ? "s" : "",
                Long.toString(seconds), (seconds > 1) ? "s" : "");
    }

}
