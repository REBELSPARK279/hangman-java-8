package code.net.rebelspark.hangman_client.init.regestries;

import code.net.rebelspark.hangman_client.util.Logger;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Sprites {

    public static void register() {

    }

    private static BufferedImage registerSprite(String path) {
        Logger.INFO("Registering sprite " + path);
        try {
            InputStream inputStream = Sprites.class.getResourceAsStream(path);
            if (inputStream == null) {
                Logger.CRITICAL("Unable to find sprite \"" + path + "\"");
                Logger.WARN("Unable to register sprite \"" + path + "\"");
                return(null);
            } else {
                Logger.INFO("Registered sprite " + path + "!");
                return(ImageIO.read(inputStream));
            }
        } catch (IOException ioException) {
            Logger.WARN("Unable to register sprite \"" + path + "\"");
            ioException.printStackTrace();
            return(null);
        }
    }

}
