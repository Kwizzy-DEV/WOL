package fr.kwizzy.waroflegions.util.bukkit;

import fr.kwizzy.waroflegions.WOL;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Par Alexis le 26/10/2016.
 */

public class ScheduleUtil
{

    public static void  runIn(long seconds, Consumer c)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                c.accept(null);
            }
        }.runTaskLater(WOL.getInstance(), seconds*20);

    }

    public static void runWhile(long seconds, Consumer c, Predicate p)
    {
        new BukkitRunnable()
        {
            @Override
            public void run()
            {
                if(p.test(null))
                    c.accept(null);
                else
                    this.cancel();
            }
        }.runTaskTimer(WOL.getInstance(), 0, seconds*20);
    }

}
