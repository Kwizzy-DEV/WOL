package fr.kwizzy.waroflegions.util.java;


import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Par Alexis le 24/10/2016.
 */

public class Log
{

    static Logger log = Bukkit.getLogger();


    public static void printException(Exception e)
    {
        log.log(Level.SEVERE, "an exception was thrown", new Exception(e));
    }

}
