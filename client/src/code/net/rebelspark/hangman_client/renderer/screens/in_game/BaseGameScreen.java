package code.net.rebelspark.hangman_client.renderer.screens.in_game;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.overlay.in_game.EndOverlay;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.util.HashSet;

public class BaseGameScreen {

    private static final Keyboard keyboard = new Keyboard(GameStates.IN_GAME_BASE, 0f, 0.5f, (key) -> {
        Classic.receiveInput(key);
        Logger.INFO("Keyboard key clicked");
        Logger.DEBUG(key);
    });

    public static void render(Graphics g) {
        final int WINDOW_W = GamePanel.panel.getWidth();
        final int WINDOW_H = GamePanel.panel.getHeight();
        g.drawLine((int)(0.5 * WINDOW_W), 0, (int)(0.5 * WINDOW_W), WINDOW_H);
        g.drawLine(0, (int)(0.5 * WINDOW_H), WINDOW_W, (int)(0.5 * WINDOW_H));
        Keyboard.updateKeyboardKeyStates(keyboard);
        keyboard.render(g);
        renderGame(g, Classic.getWordLength(), Classic.getLives());
        if (EndOverlay.getState()) {
            EndOverlay.renderEndScreenOverlay(g);
        }
    }

    private static final float GAMESPACE_POS_X = 0f;
    private static final float GAMESPACE_W = 0.5f;
    private static final float GAMESPACE_POS_Y = 0f;
    private static final float GAMESPACE_H = 0.5f;

    private static final float WORD_SLOT_Y_PLACEMENT = 0.75f; //RELATIVE TO GAMESPACE! 1f HERE PLACES IT AT GAMESPACE_H + GAMESPACE+_POS_Y

    private static final byte MAX_WORD_SLOTS = 12;
    private static final float WORD_SLOT_W = 0.0275f;
    private static final float WORD_SLOT_SPACING = (GAMESPACE_W - (WORD_SLOT_W * MAX_WORD_SLOTS)) / (MAX_WORD_SLOTS + 1);

    private static final float WORD_SLOT_LINE_H = 0.01f;

    private static final float HALF_SLOT_W = WORD_SLOT_W / 2f;

    private static final GameSlot GAME_SLOT_1 = new GameSlot((byte)1, WORD_SLOT_SPACING * 1 + WORD_SLOT_W * (1 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_2 = new GameSlot((byte)2, WORD_SLOT_SPACING * 2 + WORD_SLOT_W * (2 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_3 = new GameSlot((byte)3, WORD_SLOT_SPACING * 3 + WORD_SLOT_W * (3 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_4 = new GameSlot((byte)4, WORD_SLOT_SPACING * 4 + WORD_SLOT_W * (4 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_5 = new GameSlot((byte)5, WORD_SLOT_SPACING * 5 + WORD_SLOT_W * (5 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_6 = new GameSlot((byte)6, WORD_SLOT_SPACING * 6 + WORD_SLOT_W * (6 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_7 = new GameSlot((byte)7, WORD_SLOT_SPACING * 7 + WORD_SLOT_W * (7 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_8 = new GameSlot((byte)8, WORD_SLOT_SPACING * 8 + WORD_SLOT_W * (8 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_9 = new GameSlot((byte)9, WORD_SLOT_SPACING * 9 + WORD_SLOT_W * (9 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_10 = new GameSlot((byte)10, WORD_SLOT_SPACING * 10 + WORD_SLOT_W * (10 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_11 = new GameSlot((byte)11, WORD_SLOT_SPACING * 11 + WORD_SLOT_W * (11 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);
    private static final GameSlot GAME_SLOT_12 = new GameSlot((byte)12, WORD_SLOT_SPACING * 12 + WORD_SLOT_W * (12 - 1), WORD_SLOT_Y_PLACEMENT * GAMESPACE_H,
            WORD_SLOT_W, WORD_SLOT_LINE_H, Color.BLACK);

    private static final Label LIVES_LABEL = new Label(0.75f, 0.25f, "- Lives", 90);

    public static void setLivesLabel(String text) {
        LIVES_LABEL.setText(text);
    }

    public static HashSet<GameSlot> gameSlots = new HashSet<>();

    static {
        gameSlots.add(GAME_SLOT_1);
        gameSlots.add(GAME_SLOT_2);
        gameSlots.add(GAME_SLOT_3);
        gameSlots.add(GAME_SLOT_4);
        gameSlots.add(GAME_SLOT_5);
        gameSlots.add(GAME_SLOT_6);
        gameSlots.add(GAME_SLOT_7);
        gameSlots.add(GAME_SLOT_8);
        gameSlots.add(GAME_SLOT_9);
        gameSlots.add(GAME_SLOT_10);
        gameSlots.add(GAME_SLOT_11);
        gameSlots.add(GAME_SLOT_12);
    }

    public static void renderGame(Graphics g, final byte spaces, final byte remainingLives) {
        GAME_SLOT_1.render(g, spaces);
        GAME_SLOT_2.render(g, spaces);
        GAME_SLOT_3.render(g, spaces);
        GAME_SLOT_4.render(g, spaces);
        GAME_SLOT_5.render(g, spaces);
        GAME_SLOT_6.render(g, spaces);
        GAME_SLOT_7.render(g, spaces);
        GAME_SLOT_8.render(g, spaces);
        GAME_SLOT_9.render(g, spaces);
        GAME_SLOT_10.render(g, spaces);
        GAME_SLOT_11.render(g, spaces);
        GAME_SLOT_12.render(g, spaces);
        LIVES_LABEL.render(g);
    }

    public static void resetGameSlotIndexes() {
        GAME_SLOT_1.resetIndex();
        GAME_SLOT_2.resetIndex();
        GAME_SLOT_3.resetIndex();
        GAME_SLOT_4.resetIndex();
        GAME_SLOT_5.resetIndex();
        GAME_SLOT_6.resetIndex();
        GAME_SLOT_7.resetIndex();
        GAME_SLOT_8.resetIndex();
        GAME_SLOT_9.resetIndex();
        GAME_SLOT_10.resetIndex();
        GAME_SLOT_11.resetIndex();
        GAME_SLOT_12.resetIndex();
    }

    public static class GameSlot {

        private static byte nextIndex = 0;

        public static void resetNextIndex() {
            nextIndex = 0;
        }

        public static byte getNextIndex() {
            return (nextIndex);
        }

        private byte id;
        private float x;
        private float y;
        private float w;
        private float h;
        private Color color;
        private char letter = '-';
        private Color letterColor;
        private byte index = -1;

        public GameSlot(byte id, float x, float y, float w, float h, Color color) {
            this.id = id;
            this.x = x;
            this. y = y;
            this. w = w;
            this.h = h;
            this.color = color;
        }

        public byte getId() {
            return (id);
        }

        public byte getIndex() {
            return (index);
        }

        public void setLetter(char letter) {
            this.letter = letter;
        }

        public void resetIndex() {
            this.index = -1;
        }

        public void render(Graphics g, byte activeSlots) {
            final int WINDOW_W = GamePanel.panel.getWidth();
            final int WINDOW_H = GamePanel.panel.getHeight();
            final boolean applyOddOffset = activeSlots % 2 == 1;
            if (id >= Math.ceil(6 - ((activeSlots / 2.0) - 0.5)) && id <= Math.ceil(6 + ((activeSlots / 2.0) - 0.5))) {
                if (index == -1) {
                    index = nextIndex;
                    nextIndex++;
                }
                Graphics2D g2d = (Graphics2D)g;
                int X = (applyOddOffset? (int)((x + WORD_SLOT_W + (WORD_SLOT_SPACING / 2f)) * WINDOW_W): (int)((x + HALF_SLOT_W) * WINDOW_W));
                int Y = (int)(y * WINDOW_H);
                int W = (int)(w * WINDOW_W);
                int H = (int)(h * WINDOW_H);
                g2d.setColor(letterColor);
                g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                g2d.setFont(Fonts.RUBIK.deriveFont((float)(65f / 1080.0) * GamePanel.panel.getHeight()));
                FontMetrics fontMetrics = g2d.getFontMetrics();
                g2d.drawString(letter == '-'? "": "" + letter, X - (int)(W / 2f) + (((WORD_SLOT_W * WINDOW_W) - fontMetrics.stringWidth("" + index)) / 2f),
                        Y - (0.0075f * WINDOW_H));
                g.setColor(color);
                g.fillRect(X - (W / 2), Y - (H / 2), W, H);
                g.setColor(Color.BLACK);
            }
        }

        public String toString() {
            return("[GameSlot-" + ((id / 10) == 0? "0" + id: id) + ", letter " + letter + "]");
        }

    }

}
