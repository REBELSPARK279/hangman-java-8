package code.net.rebelspark.hangman_client.renderer.screens.in_game;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.FilledRect;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class EndOverlay {

    private static final FilledRect BG_DIMMER = new FilledRect(0.5f, 0.5f, 1f, 1f, new Color(0, 0, 0, 140));
    private static final FilledRect BANNER_OUTLINE = new FilledRect(0.5f, 0.5f, 0.80333f, 0.455f, Color.BLACK);
    private static final FilledRect BANNER_MAIN = new FilledRect(0.5f, 0.5f, 0.8f, 0.45f, new Color(165, 165, 165));

    private static final Button MENU = new Button.Properties(GameStates.END_SCREEN, "menu", 0.35f, 0.575f, () ->
            GameStateHandler.triggerDelayedSetGameState(GameStates.GAME_MENU))
            .setBgColor(new Color(95, 95, 95), new Color(195, 50, 50)).setBounds(0.25f, 0.175f).appendBorder(7, Color.BLACK)
            .setText("Back to Menu", 70).setTextColor(Color.BLACK).build();

    private static final Button PLAY_AGAIN = new Button.Properties(GameStates.END_SCREEN, "play_again", 0.65f, 0.575f, Classic::startGame
    ).setBgColor(new Color(95, 95, 95), new Color(130, 130, 130)).setBounds(0.25f, 0.175f).appendBorder(7, Color.BLACK)
            .setText("Play again", 70).setTextColor(Color.BLACK).build();

    private static final Label TITLE = new Label(0.5f, 0.38f, "-", 70, Color.BLACK);

    public static void setEndScreenTitleStatus(boolean won, String word) {
        if (won) {
            TITLE.setText("You Won! The word was " + word + ".");
        } else {
            TITLE.setText("You Lost. The word was " + word + '.');
        }
    }

    public static void renderEndScreenOverlay(Graphics g) {
        final int WINDOW_W = GamePanel.panel.getWidth();
        final int WINDOW_H = GamePanel.panel.getHeight();
        BG_DIMMER.render(g);
        BANNER_OUTLINE.render(g);
        BANNER_MAIN.render(g);
        TITLE.render(g);
        MENU.render(g);
        PLAY_AGAIN.render(g);
    }
}
