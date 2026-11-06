package code.net.rebelspark.hangman_client.init;

import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.init.regestries.Sprites;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.sound.SoundHandler;
import code.net.rebelspark.hangman_client.sound.soundLocations.SongLocations;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;

public class InitHandler {
    public static void init() {
        Logger.INFO("Initializing " + CONSTANTS.WINDOW_NAME);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        Fonts.register();
        Sprites.register();
        GamePanel.init();
        GameStateHandler.setGameState(GameStates.MAIN_MENU);
        /*
        new Thread(() -> {
            try {
                SoundHandler.playSong(SongLocations.ARIA_MATH);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();
         */
    }
}

