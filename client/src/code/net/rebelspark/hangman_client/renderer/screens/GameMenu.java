package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.renderer.features.Button;
import code.net.rebelspark.hangman_client.renderer.features.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class GameMenu {

    private static final Button EXIT = new Button.Properties(GameStates.GAME_MENU, "back", 0.06f, 0.06f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU);
    }).setText("<--", 55, Color.BLACK, Color.BLUE).build();

    private static final Label SINGLEPLAYER_LABEL = new Label.Properties(0.33f, 0.075f, "Singleplayer").textSize(75).build();

    private static final Button NORMAL = new Button.Properties(GameStates.GAME_MENU, "offline", 0.33f, 0.2f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.IN_GAME_BASE);
        Classic.startGame();
    }).setBgColor(Color.DARK_GRAY, Color.GRAY).setBounds(0.2f, 0.1f).setText("Normal", 65, Color.BLACK, Color.DARK_GRAY)
            .appendBorder(6, Color.BLACK, Color.DARK_GRAY).setKeySelections(new int[]{0, 0}).build();

    private static final Label MULTIPLAYER_LABEL = new Label.Properties(0.66f, 0.075f, "Multiplayer").textSize(75).build();

    public static void render(Graphics g) {
        EXIT.render(g);

        SINGLEPLAYER_LABEL.render(g);
        NORMAL.render(g);

        MULTIPLAYER_LABEL.render(g);
    }
}
