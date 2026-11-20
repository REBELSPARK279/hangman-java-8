package code.net.rebelspark.hangman_client.init;

import code.net.rebelspark.hangman_client.init.file_readers.SettingsReader;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.init.regestries.Sprites;
import code.net.rebelspark.hangman_client.init.verifications.OfflineWordListVerifier;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.overlay.common.DebugOverlay;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

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
        SettingsReader.readSettings();
        GamePanel.init();
        GameStateHandler.setGameState(GameStates.MAIN_MENU);
        OfflineWordListVerifier.verifyOfflineWordList();
        DebugOverlay.startClass();
       /* new Thread(() -> {
            try {
                SoundHandler.playSong(SongLocations.T322);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
        }).start();

        */
    }
}

