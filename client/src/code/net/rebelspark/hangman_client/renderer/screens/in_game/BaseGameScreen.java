package code.net.rebelspark.hangman_client.renderer.screens.in_game;

import code.net.rebelspark.hangman_client.game_logic.client.KeyboardKeyStates;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.util.function.Consumer;

public class BaseGameScreen {

    private static final Keyboard keyboard = new Keyboard(GameStates.IN_GAME_BASE, 0f, 0.5f, (character) -> {
        Logger.INFO("Keyboard key clicked");
        Logger.DEBUG(character);
    });

    public static void render(Graphics g, int w, int h) {
        g.drawLine((int)(0.5 * w), 0, (int)(0.5 * w), h);
        g.drawLine(0, (int)(0.5 * h), w, (int)(0.5 * h));
        keyboard.render(g, w, h);
    }

    private static final float GAMESPACE_POS_X = 0f;
    private static final float GAMESPACE_W = 0.5f;
    private static final float GAMESPACE_POS_Y = 0f;
    private static final float GAMESPACE_H = 0.5f;

    private static final byte MAX_WORD_SLOTS = 12;
    private static final float WORD_SLOT_W = 0.375f;
    private static final float WORD_SLOT_SPACING = GAMESPACE_W;

    private static final float WORD_SLOT_LINE_H = 0.01f;

    public static void renderGame(final byte spaces, final byte remainingLives) {
        final Graphics g = GamePanel.panel.getGraphics();
        final int W = GamePanel.panel.getWidth();
        final int H = GamePanel.panel.getHeight();

    }
}
