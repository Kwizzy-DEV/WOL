package fr.kwizzy.waroflegions.util.bukkit.centered;

import com.sun.istack.internal.NotNull;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Par Alexis le 04/10/2016.
 */

public class CenteredMessage {

    private static final int CENTER_PX = 154;

    public static void sendCenteredMessage(@NotNull String m, Player... players) {
        for (Player player : players) {
            if (m == null || m.isEmpty()) {
                player.sendMessage("");
                continue;
            }
            String message = ChatColor.translateAlternateColorCodes('&', m);

            int messagePxSize = 0;
            boolean previousCode = false;
            boolean isBold = false;

            for (char c : message.toCharArray()) {
                if (c == '§')
                    previousCode = true;
                else if (previousCode) {
                    previousCode = false;
                    if (c == 'l' || c == 'L') {
                        isBold = true;
                    } else
                        isBold = false;
                }
                else {
                    DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                    messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                    messagePxSize++;
                }
            }

            int halvedMessageSize = messagePxSize / 2;
            int toCompensate = CENTER_PX - halvedMessageSize;
            int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
            int compensated = 0;
            StringBuilder sb = new StringBuilder();
            while (compensated < toCompensate) {
                sb.append(" ");
                compensated += spaceLength;
            }
            player.sendMessage(sb.toString() + message);
        }

    }

}
