package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class GameMenu {

    private static final Button EXIT = new Button(GameStates.GAME_MENU,  0.06f, 0.06f, "<--", 55, Color.BLACK, Color.BLUE, () -> GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU));

    private static final Label SINGLEPLAYER_LABEL = new Label(0.33f, 0.075f, "Singleplayer", 75);
    private static final Button NORMAL = new Button(GameStates.GAME_MENU, 0.33f, 0.2f, 0.2f, 0.1f, Color.DARK_GRAY, Color.GRAY,
            "Normal", 65, Color.BLACK, Color.DARK_GRAY, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.IN_GAME_BASE);
        Classic.startGame();
    }).appendBorder(6, Color.BLACK, Color.DARK_GRAY);

    private static final Label MULTIPLAYER_LABEL = new Label(0.66f, 0.075f, "Multiplayer", 75);

    public static void render(Graphics g) {
        EXIT.render(g);

        SINGLEPLAYER_LABEL.render(g);
        NORMAL.render(g);

        MULTIPLAYER_LABEL.render(g);
    }
}
