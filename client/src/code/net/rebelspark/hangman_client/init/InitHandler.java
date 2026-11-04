package code.net.rebelspark.hangman_client.init;

import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.init.regestries.Sounds;
import code.net.rebelspark.hangman_client.init.regestries.Sprites;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

public class InitHandler {
    public static void init() {
        Fonts.register();
        Sprites.register();
        Sounds.register();
        GamePanel.init();
        GameStateHandler.setGameState(GameStates.MAIN_MENU);
    }
}

