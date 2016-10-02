package fr.kwizzy.waroflegions.util.bukkit;

import fr.kwizzy.waroflegions.WarOfLegions;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Par Alexis le 02/10/2016.
 */

public class FireworkUtil {

    private static Color getColor(int i) {
        Color c = null;
        if (i == 1) {
            c = Color.AQUA;
        }
        if (i == 2) {
            c = Color.BLACK;
        }
        if (i == 3) {
            c = Color.BLUE;
        }
        if (i == 4) {
            c = Color.FUCHSIA;
        }
        if (i == 5) {
            c = Color.GRAY;
        }
        if (i == 6) {
            c = Color.GREEN;
        }
        if (i == 7) {
            c = Color.LIME;
        }
        if (i == 8) {
            c = Color.MAROON;
        }
        if (i == 9) {
            c = Color.NAVY;
        }
        if (i == 10) {
            c = Color.OLIVE;
        }
        if (i == 11) {
            c = Color.ORANGE;
        }
        if (i == 12) {
            c = Color.PURPLE;
        }
        if (i == 13) {
            c = Color.RED;
        }
        if (i == 14) {
            c = Color.SILVER;
        }
        if (i == 15) {
            c = Color.TEAL;
        }
        if (i == 16) {
            c = Color.WHITE;
        }
        if (i == 17) {
            c = Color.YELLOW;
        }
        return c;
    }

    public static void playFirework(final Location loc, int detonateTicks) {

        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        Random r = new Random();

        int rt = r.nextInt(4) + 1;
        FireworkEffect.Type type = FireworkEffect.Type.BALL;
        if (rt == 1)
            type = FireworkEffect.Type.BALL;
        if (rt == 2)
            type = FireworkEffect.Type.BALL_LARGE;
        if (rt == 3)
            type = FireworkEffect.Type.BURST;
        if (rt == 5)
            type = FireworkEffect.Type.STAR;


        Color c1 = getColor(r.nextInt(17) + 1);
        Color c2 = getColor(r.nextInt(17) + 1);

        FireworkEffect effect = FireworkEffect.builder()
                .flicker(r.nextBoolean())
                .withColor(c1)
                .withFade(c2)
                .with(type)
                .trail(r.nextBoolean())
                .build();

        fwm.addEffect(effect);
        fwm.setPower(r.nextInt(2) + 1);
        fw.setFireworkMeta(fwm);

        if(detonateTicks == -1)
            return;

        new BukkitRunnable(){
            @Override
            public void run() {
                fw.detonate();
            }
        }.runTaskLater(WarOfLegions.getInstance(), detonateTicks);
    }


}
