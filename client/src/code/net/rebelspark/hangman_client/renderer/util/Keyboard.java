package code.net.rebelspark.hangman_client.renderer.util;

import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.IllegalSpriteArgumentException;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.Consumer;

public class Keyboard {

    public static class KeyboardButton extends Button {
        public KeyboardButton(String name, GameStates screen, float x, float y, float w, float h, String text, float textSize, BufferedImage idleImage, BufferedImage hoverImage,
                       Color idleColor, Color hoverColor, Color idleTextColor, Color hoverTextColor, Border border, Type type, Runnable runnable) {
            super(name, screen, x, y, w, h, text, textSize, idleImage, hoverImage, idleColor, hoverColor, idleTextColor, hoverTextColor, border, type, runnable);
        }

        public void polishBoard(GameStates screen, float x, float y) {
            if (this.screen == null) {
                this.screen = screen;
            } else {
                Logger.FATAL("Cannot set screen to the same button twice");
                throw new IllegalSpriteArgumentException("Tried to set screen on button with already existing screen");
            }
            if (x < 0 || y < 0) {
                Logger.FATAL("Negative value passed for coordinates");
                throw new IllegalSpriteArgumentException("Tried to set the position of a keyboard to negative value");
            } else {
                this.x += x;
                this.y += y;
                border.updatePos(this.x, this.y);
            }
        }

        public static class Properties extends Button.Properties {
            public Properties(String name, float x, float y, Runnable runnable) {
                super(null, name, x, y, runnable);
            }

            @Override
            public KeyboardButton build() {
                if (text == null && idleImage == null && idleColor == null) {
                    Logger.FATAL("Tried to build keyboard button with no graphics");
                    throw new IllegalSpriteArgumentException("Buttons must have at least one of the following: Text, IdleImage, IdleColor.");
                }
                if (((idleColor != null) || (text != null)) && (idleImage != null)) {
                    Logger.FATAL("Tried to keyboard build button with an image and text or background color");
                    throw new IllegalSpriteArgumentException("Cannot have an image with background color nor text");
                }
                if (text != null && w != 0.0 && idleColor == null) {
                    Logger.FATAL("Tried to build a text based keyboard button with explicitly defined bounds");
                    throw new IllegalSpriteArgumentException("Pure text button cannot define width nor height");
                }
                if (text != null && textSize <= 0.0) {
                    this.textSize = DEFAULT_TEXT_SIZE;
                }
                if (text != null && idleColor == null) {
                    if (hoverTextColor == null) {
                        this.type = Type.TEXT_NON_HOVER;
                    } else {
                        this.type = Type.TEXT;
                    }
                } else if (idleImage != null) {
                    if (hoverImage == null) {
                        this.type = Type.IMAGE_NON_HOVER;
                    } else {
                        this.type = Type.IMAGE;
                    }
                } else if (idleColor != null) {
                    if (text == null) {
                        if (hoverColor == null) {
                            this.type = Type.COLOR_NON_HOVER;
                        } else {
                            this.type = Type.COLOR;
                        }
                    } else {
                        if (hoverColor == null) {
                            if (hoverTextColor == null) {
                                this.type = Type.COLOR_NON_HOVER_TEXT_NON_HOVER;
                            } else {
                                this.type = Type.COLOR_NON_HOVER_TEXT;
                            }
                        } else {
                            if (hoverTextColor == null) {
                                this.type = Type.COLOR_TEXT_NON_HOVER;
                            } else {
                                this.type = Type.COLOR_TEXT;
                            }
                        }
                    }
                }
                if (textSize <= 0.0f) {
                    textSize = DEFAULT_TEXT_SIZE;
                }
                if (idleTextColor == null) {
                    idleTextColor = Color.BLACK;
                }
                if (type == Type.TEXT_NON_HOVER || type == Type.TEXT) {
                    if (h != 0.0) {
                        Logger.FATAL("Tried to define bounds of a pure text button");
                        throw new IllegalSpriteArgumentException("Tried to define bounds of a pure text button");
                    }
                } else {
                    if (h <= 0) {
                        Logger.FATAL("Unable to build button");
                        throw new IllegalSpriteArgumentException("Bounds must be defined for non text based buttons");
                    }
                }
                return (new KeyboardButton(name, screen, x, y, w, h, text, textSize, idleImage, hoverImage, idleColor, hoverColor, idleTextColor, hoverTextColor,
                        border, type, runnable));
            }
        }
    }

    public enum KEYS {
        Q, W, E, R, T, Y, U, I, O, P,
        A, S, D, F, G, H, J, K, L,
        Z, X, C, V, B, N, M
    }

    private static final byte ROWS = 3;
    private static final byte COLUMNS = 10;

    //PERCENT OF GAME SCREEN THE KEYBOARD USES
    private static final float KEYBOARD_MAX_WIDTH = 0.5f;
    private static final float KEYBOARD_MAX_HEIGHT = 0.5f;

    //KEYS PER ROW
    private static final byte TOP_ROW_KEYS = 10;
    private static final byte MIDDLE_ROW_KEYS = 9;
    private static final byte BOTTOM_ROW_KEYS = 7;

    //OFFSET FROM THE LEFT (VALUE IN KEYS)
    private static final float TOP_ROW_OFFSET = 0f;
    private static final float MIDDLE_ROW_OFFSET = 0.5f;
    private static final float BOTTOM_ROW_OFFSET = 1.3f;

    //LISTS
    public static final ArrayList<Float[]> KEYBOARD_POSITIONS = new ArrayList<>();
    public static final TreeMap<KEYS, Float[]> LETTER_POSITIONS = new TreeMap<>();

    //BUTTON SETTINGS
    private static final float SIZE_W = 0.04f;
    private static final float SPACING_H = ((KEYBOARD_MAX_WIDTH - (COLUMNS * SIZE_W)) / (COLUMNS + 1f));
    private static final float SIZE_H = 0.12f;
    private static final float SPACING_V = ((KEYBOARD_MAX_HEIGHT - (ROWS * SIZE_H)) / (ROWS + 1f));


    static {
        for (byte i = 0; i < TOP_ROW_KEYS; i++) {
            KEYBOARD_POSITIONS.add(new Float[]{(TOP_ROW_OFFSET * SIZE_W) + ((i + 1) * SPACING_H) + (i * SIZE_W), SPACING_V});
        }
        for (byte i = 0; i < MIDDLE_ROW_KEYS; i++) {
            KEYBOARD_POSITIONS.add(new Float[]{(MIDDLE_ROW_OFFSET * SIZE_W) + ((i + 1) * SPACING_H) + (i * SIZE_W), (SPACING_V * 2) + SIZE_H});
        }
        for (byte i = 0; i < BOTTOM_ROW_KEYS; i++) {
            KEYBOARD_POSITIONS.add(new Float[]{(BOTTOM_ROW_OFFSET * SIZE_W) + ((i + 1) * SPACING_H) + (i * SIZE_W), (SPACING_V * 3) + (SIZE_H * 2)});
        }

        LETTER_POSITIONS.put(KEYS.Q, KEYBOARD_POSITIONS.get(0));
        LETTER_POSITIONS.put(KEYS.W, KEYBOARD_POSITIONS.get(1));
        LETTER_POSITIONS.put(KEYS.E, KEYBOARD_POSITIONS.get(2));
        LETTER_POSITIONS.put(KEYS.R, KEYBOARD_POSITIONS.get(3));
        LETTER_POSITIONS.put(KEYS.T, KEYBOARD_POSITIONS.get(4));
        LETTER_POSITIONS.put(KEYS.Y, KEYBOARD_POSITIONS.get(5));
        LETTER_POSITIONS.put(KEYS.U, KEYBOARD_POSITIONS.get(6));
        LETTER_POSITIONS.put(KEYS.I, KEYBOARD_POSITIONS.get(7));
        LETTER_POSITIONS.put(KEYS.O, KEYBOARD_POSITIONS.get(8));
        LETTER_POSITIONS.put(KEYS.P, KEYBOARD_POSITIONS.get(9));
        LETTER_POSITIONS.put(KEYS.A, KEYBOARD_POSITIONS.get(10));
        LETTER_POSITIONS.put(KEYS.S, KEYBOARD_POSITIONS.get(11));
        LETTER_POSITIONS.put(KEYS.D, KEYBOARD_POSITIONS.get(12));
        LETTER_POSITIONS.put(KEYS.F, KEYBOARD_POSITIONS.get(13));
        LETTER_POSITIONS.put(KEYS.G, KEYBOARD_POSITIONS.get(14));
        LETTER_POSITIONS.put(KEYS.H, KEYBOARD_POSITIONS.get(15));
        LETTER_POSITIONS.put(KEYS.J, KEYBOARD_POSITIONS.get(16));
        LETTER_POSITIONS.put(KEYS.K, KEYBOARD_POSITIONS.get(17));
        LETTER_POSITIONS.put(KEYS.L, KEYBOARD_POSITIONS.get(18));
        LETTER_POSITIONS.put(KEYS.Z, KEYBOARD_POSITIONS.get(19));
        LETTER_POSITIONS.put(KEYS.X, KEYBOARD_POSITIONS.get(20));
        LETTER_POSITIONS.put(KEYS.C, KEYBOARD_POSITIONS.get(21));
        LETTER_POSITIONS.put(KEYS.V, KEYBOARD_POSITIONS.get(22));
        LETTER_POSITIONS.put(KEYS.B, KEYBOARD_POSITIONS.get(23));
        LETTER_POSITIONS.put(KEYS.N, KEYBOARD_POSITIONS.get(24));
        LETTER_POSITIONS.put(KEYS.M, KEYBOARD_POSITIONS.get(25));
    }


    private Consumer<Character> runnable;

    //BUTTONS

    public final KeyboardButton KEYBOARD_Q = keyboardKeyBuilder(KEYS.Q, this);
    public final KeyboardButton KEYBOARD_W = keyboardKeyBuilder(KEYS.W, this);
    public final KeyboardButton KEYBOARD_E = keyboardKeyBuilder(KEYS.E, this);
    public final KeyboardButton KEYBOARD_R = keyboardKeyBuilder(KEYS.R, this);
    public final KeyboardButton KEYBOARD_T = keyboardKeyBuilder(KEYS.T, this);
    public final KeyboardButton KEYBOARD_Y = keyboardKeyBuilder(KEYS.Y, this);
    public final KeyboardButton KEYBOARD_U = keyboardKeyBuilder(KEYS.U, this);
    public final KeyboardButton KEYBOARD_I = keyboardKeyBuilder(KEYS.I, this);
    public final KeyboardButton KEYBOARD_O = keyboardKeyBuilder(KEYS.O, this);
    public final KeyboardButton KEYBOARD_P = keyboardKeyBuilder(KEYS.P, this);

    public final KeyboardButton KEYBOARD_A = keyboardKeyBuilder(KEYS.A, this);
    public final KeyboardButton KEYBOARD_S = keyboardKeyBuilder(KEYS.S, this);
    public final KeyboardButton KEYBOARD_D = keyboardKeyBuilder(KEYS.D, this);
    public final KeyboardButton KEYBOARD_F = keyboardKeyBuilder(KEYS.F, this);
    public final KeyboardButton KEYBOARD_G = keyboardKeyBuilder(KEYS.G, this);
    public final KeyboardButton KEYBOARD_H = keyboardKeyBuilder(KEYS.H, this);
    public final KeyboardButton KEYBOARD_J = keyboardKeyBuilder(KEYS.J, this);
    public final KeyboardButton KEYBOARD_K = keyboardKeyBuilder(KEYS.K, this);
    public final KeyboardButton KEYBOARD_L = keyboardKeyBuilder(KEYS.L, this);

    public final KeyboardButton KEYBOARD_Z = keyboardKeyBuilder(KEYS.Z, this);
    public final KeyboardButton KEYBOARD_X = keyboardKeyBuilder(KEYS.X, this);
    public final KeyboardButton KEYBOARD_C = keyboardKeyBuilder(KEYS.C, this);
    public final KeyboardButton KEYBOARD_V = keyboardKeyBuilder(KEYS.V, this);
    public final KeyboardButton KEYBOARD_B = keyboardKeyBuilder(KEYS.B, this);
    public final KeyboardButton KEYBOARD_N = keyboardKeyBuilder(KEYS.N, this);
    public final KeyboardButton KEYBOARD_M = keyboardKeyBuilder(KEYS.M, this);


    private static KeyboardButton keyboardKeyBuilder(KEYS key, Keyboard board) {
        return (KeyboardButton) new KeyboardButton.Properties(board + key.toString(), LETTER_POSITIONS.get(key)[0] + (SIZE_W / 2f), LETTER_POSITIONS.get(key)[1] + (SIZE_H / 2f),
                () -> board.runnable.accept(key.toString().charAt(0))).setText(key.toString(), 55).setBgColor(new Color(100, 100, 100), new Color(180, 180, 180))
                .setBounds(SIZE_W, SIZE_H).appendBorder(4, Color.BLACK, Color.DARK_GRAY).build();
    }

    public Keyboard(GameStates screen, float x, float y, Consumer<Character> runnable) {
        this.runnable = runnable;
        KEYBOARD_Q.polishBoard(screen, x, y);
        KEYBOARD_W.polishBoard(screen, x, y);
        KEYBOARD_E.polishBoard(screen, x, y);
        KEYBOARD_R.polishBoard(screen, x, y);
        KEYBOARD_T.polishBoard(screen, x, y);
        KEYBOARD_Y.polishBoard(screen, x, y);
        KEYBOARD_U.polishBoard(screen, x, y);
        KEYBOARD_I.polishBoard(screen, x, y);
        KEYBOARD_O.polishBoard(screen, x, y);
        KEYBOARD_P.polishBoard(screen, x, y);

        KEYBOARD_A.polishBoard(screen, x, y);
        KEYBOARD_S.polishBoard(screen, x, y);
        KEYBOARD_D.polishBoard(screen, x, y);
        KEYBOARD_F.polishBoard(screen, x, y);
        KEYBOARD_G.polishBoard(screen, x, y);
        KEYBOARD_H.polishBoard(screen, x, y);
        KEYBOARD_J.polishBoard(screen, x, y);
        KEYBOARD_K.polishBoard(screen, x, y);
        KEYBOARD_L.polishBoard(screen, x, y);

        KEYBOARD_Z.polishBoard(screen, x, y);
        KEYBOARD_X.polishBoard(screen, x, y);
        KEYBOARD_C.polishBoard(screen, x, y);
        KEYBOARD_V.polishBoard(screen, x, y);
        KEYBOARD_B.polishBoard(screen, x, y);
        KEYBOARD_N.polishBoard(screen, x, y);
        KEYBOARD_M.polishBoard(screen, x, y);
    }

    public void render(Graphics g, final int w, final int h) {
        KEYBOARD_Q.render(g, w, h);
        KEYBOARD_W.render(g, w, h);
        KEYBOARD_E.render(g, w, h);
        KEYBOARD_R.render(g, w, h);
        KEYBOARD_T.render(g, w, h);
        KEYBOARD_Y.render(g, w, h);
        KEYBOARD_U.render(g, w, h);
        KEYBOARD_I.render(g, w, h);
        KEYBOARD_O.render(g, w, h);
        KEYBOARD_P.render(g, w, h);

        KEYBOARD_A.render(g, w, h);
        KEYBOARD_S.render(g, w, h);
        KEYBOARD_D.render(g, w, h);
        KEYBOARD_F.render(g, w, h);
        KEYBOARD_G.render(g, w, h);
        KEYBOARD_H.render(g, w, h);
        KEYBOARD_J.render(g, w, h);
        KEYBOARD_K.render(g, w, h);
        KEYBOARD_L.render(g, w, h);

        KEYBOARD_Z.render(g, w, h);
        KEYBOARD_X.render(g, w, h);
        KEYBOARD_C.render(g, w, h);
        KEYBOARD_V.render(g, w, h);
        KEYBOARD_B.render(g, w, h);
        KEYBOARD_N.render(g, w, h);
        KEYBOARD_M.render(g, w, h);
    }

}
