package code.net.rebelspark.hangman_client.init.regestries;

import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;

public class Fonts {

    public static Font RUSSO_ONE;
    public static Font RUBIK;

    public static void register() {
        RUSSO_ONE = registerFont("/resources/font/russo_one.ttf");
        RUBIK = registerFont("/resources/font/rubik.ttf");
    }

    public static Font registerFont(String path) {
        Logger.INFO("Loading font \"" + path + "\"");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Fonts.class.getResourceAsStream(path));
            if (font == null) {
                Logger.CRITICAL("Unable to find font \"" + path + "\"");
                Logger.WARN("Unable to load font \"" + path + "\"");
                return(null);
            } else {
                Logger.INFO("Loaded font " + font + "!");
                return (font);
            }
        } catch (Exception exception) {
            Logger.WARN("Unable to load font " + path);
            exception.printStackTrace();
            return(null);
        }
    }
}
