package dev.sellworth;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public final class Text {
    public static final TextColor BLUE = TextColor.fromHexString("#00a2ff");
    private static final String NORMAL = "abcdefghijklmnopqrstuvwxyz";
    private static final String SMALL = "ᴀʙᴄᴅᴇꜰɢʜɪᴊᴋʟᴍɴᴏᴘǫʀꜱᴛᴜᴠᴡxʏᴢ";

    private Text() {}

    public static String smallCaps(String in) {
        StringBuilder sb = new StringBuilder();
        for (char c : in.toCharArray()) {
            int i = NORMAL.indexOf(Character.toLowerCase(c));
            sb.append(i >= 0 ? SMALL.charAt(i) : c);
        }
        return sb.toString();
    }

    public static String itemName(Material m) {
        StringBuilder sb = new StringBuilder();
        for (String part : m.name().toLowerCase(Locale.ROOT).split("_")) {
            if (part.isEmpty()) continue;
            if (sb.length() > 0) sb.append(' ');
            sb.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
        }
        return sb.toString();
    }

    public static String money(double v) {
        return new DecimalFormat("#,##0.##", DecimalFormatSymbols.getInstance(Locale.US)).format(v);
    }

    public static Component plain(String s, TextColor c) {
        return Component.text(smallCaps(s), c).decoration(TextDecoration.ITALIC, false);
    }

    /** +$<amount> : "$" green, amount #00a2ff */
    public static Component sold(double amount) {
        return Component.text("+$", NamedTextColor.GREEN).append(Component.text(money(amount), BLUE));
    }

    /** <Item> is unsellable : item #00a2ff, unsellable red */
    public static Component unsellable(Material m) {
        return Component.text(smallCaps(itemName(m)), BLUE)
                .append(Component.text(smallCaps(" is unsellable"), NamedTextColor.RED));
    }

    /** <Item> is worth $<price> */
    public static Component worth(Material m, double price) {
        return Component.text(smallCaps(itemName(m)), BLUE)
                .append(Component.text(smallCaps(" is worth "), NamedTextColor.WHITE))
                .append(Component.text("$", NamedTextColor.GREEN))
                .append(Component.text(money(price), BLUE));
    }

    public static void sendBoth(Player p, Component c) {
        p.sendMessage(c);
        p.sendActionBar(c);
    }
}
