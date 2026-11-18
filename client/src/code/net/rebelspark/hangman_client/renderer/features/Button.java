package code.net.rebelspark.hangman_client.renderer.features;

import code.net.rebelspark.hangman_client.eventHandlers.MouseMovementHandler;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavMain;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.features.interfaces.InputtableFeature;
import code.net.rebelspark.hangman_client.renderer.features.util.InputtableFeatures;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.exceptions.IllegalSpriteArgumentException;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public class Button implements InputtableFeature {

    protected static class Hitbox {
        private int hitboxX;
        private int hitboxY;
        private int hitboxW;
        private int hitboxH;

        private Hitbox(){}

        private void setHitbox(int x, int y, int w, int h) {
            hitboxX = x;
            hitboxY = y;
            hitboxW = w;
            hitboxH = h;
        }

        private void disableHitbox() {
            hitboxX = -1;
            hitboxY = -1;
            hitboxW = 0;
            hitboxH = 0;
        }

        @Deprecated
        private boolean checkBounds(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            return((mouseX >= hitboxX) && (mouseX <= hitboxX + hitboxW) && (mouseY >= hitboxY) && (mouseY <= hitboxY + hitboxH));
        }

        private boolean checkBounds() {
            int mouseX = MouseMovementHandler.getMouseX();
            int mouseY = MouseMovementHandler.getMouseY();
            return((mouseX >= hitboxX) && (mouseX <= hitboxX + hitboxW) && (mouseY >= hitboxY) && (mouseY <= hitboxY + hitboxH));
        }

        public String toString() {
            return ("hitbox [x " + hitboxX + ", y " + hitboxY + ", w " + hitboxW + ", h " + hitboxH + "]");
        }

    }

    protected static class Border {

        private float x;
        private float y;
        private float w;
        private float h;
        private int t;
        private Color idleColor;
        private Color hoverColor;

        private Rect borderIdle;
        private Rect borderHover;

        private Border(final float x, final float y, final float w, final float h, final int t, Color idleColor, Color hoverColor) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.t = t;
            this.idleColor = idleColor;
            this.hoverColor = hoverColor;
            borderIdle = new Rect(x, y, w, h, t, idleColor);
            borderHover = new Rect(x, y, w, h, t, hoverColor);
        }

        public void updatePos(final float x, final float y) {
            borderIdle = new Rect(x, y, w, h, t, idleColor);
            borderHover = new Rect(x, y, w, h, t, hoverColor);
        }

        private void render(Graphics g, boolean hovering) {
            Rect renderThis = hovering? borderHover: borderIdle;
            renderThis.render(g);
        }

        public String toString() {
            return ("border [x " + x + ", y " + y + ", w " + w + ", h " + h + ", t " + t + ", idleColor " + idleColor + ", hoverColor " + hoverColor + "]");
        }
    }

    protected enum Type {
        NONE,
        TEXT,
        TEXT_NON_HOVER,
        COLOR,
        COLOR_NON_HOVER,
        COLOR_TEXT,
        COLOR_TEXT_NON_HOVER,
        COLOR_NON_HOVER_TEXT,
        COLOR_NON_HOVER_TEXT_NON_HOVER,
        IMAGE,
        IMAGE_NON_HOVER
    }

    protected static final float DEFAULT_TEXT_SIZE = ((float)(65.0 / 1080.0));


    //exempt from hover and clicks
    private static HashSet<GameStates> exemptScreens = new HashSet<>();

    public static void disableInputsTo(GameStates screen) {
        exemptScreens.add(screen);
    }

    public static void enableInputsTo(GameStates screen) {
        exemptScreens.remove(screen);
    }

    public static HashSet<GameStates> getExemptScreens() {
        return (new HashSet<>(exemptScreens));
    }

    public static boolean canRecieveInput(GameStates screen) {
        return (!exemptScreens.contains(screen));
    }


    protected String name;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected String text;
    protected float textSize;
    protected BufferedImage idleImage;
    protected BufferedImage hoverImage;
    protected Color idleColor;
    protected Color hoverColor;
    protected Color idleTextColor;
    protected Color hoverTextColor;
    protected Type type;
    protected Hitbox hitbox;
    protected Runnable runnable;
    protected GameStates screen;
    protected Border border;
    protected boolean drawFromTopLeft;
    protected int[] keyboardSelectCoords;


    protected Button(String name, GameStates screen, float x, float y, float w, float h, String text, float textSize, BufferedImage idleImage, BufferedImage hoverImage,
                     Color idleColor, Color hoverColor, Color idleTextColor, Color hoverTextColor, Border border, Type type, boolean drawFromTopLeft, Runnable runnable,
                     int[] keyboardSelectIndex) {
        this.name = name;
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.text = text;
        this.textSize = textSize;
        this.idleImage = idleImage;
        this.hoverImage = hoverImage;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.border = border;
        this.type = type;
        this.runnable = runnable;
        InputtableFeatures.INPUTTABLE_FEATURES.add(this);
        hitbox = new Hitbox();
        this.drawFromTopLeft = drawFromTopLeft;
        keyboardSelectCoords = keyboardSelectIndex;
    }

    @Override
    public void input() {
        Logger.DEBUG(this + " clicked");
        if (runnable == null) {
            Logger.FATAL(this + " is missing action!");
            System.exit(-1);
        } else {
            runnable.run();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (hitbox.checkBounds(e)) {
            input();
        }
    }

    @Override
    public void enterClicked(int[] selection) {
        if (Arrays.equals(selection, keyboardSelectCoords)) {
            input();
        }
    }

    public void setPos(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void setPos(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setText(String text) {
        this.setText(text, 65);
    }

    public void setText(String text, int textSize) {
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
    }

    @Override
    public GameStates getRenderScreen() {
        return (this.screen);
    }

    public static class Properties {

        protected enum Props {
            SET_TEXT,
            SET_BOUNDS,
            SET_IMAGE,
            SET_BG_COLOR,
            SET_TEXT_COLOR,
            BORDER,
            DRAW_FROM_TOP_LEFT,
            KEY_SELECTIONS
        }

        protected HashSet<Props> setProperties = new HashSet<>();

        protected String name;
        protected float x = 0.0f;
        protected float y = 0.0f;
        protected float w = 0.0f;
        protected float h = 0.0f;
        protected String text;
        protected float textSize;
        protected BufferedImage idleImage;
        protected BufferedImage hoverImage;
        protected Color idleColor;
        protected Color hoverColor;
        protected Color idleTextColor;
        protected Color hoverTextColor;
        protected Type type;
        protected Runnable runnable;
        protected GameStates screen;
        protected Border border;
        protected boolean drawFromTopLeft;
        protected int[] keyboardSelectIndex = {-1, -1};

        protected final String PROPERTY_ALR_DEFINED_MSG_LOGGER = "Tried to set one property to a button twice";
        protected final String PROPERTY_ALR_DEFINED_THROWED_MSG = "You cannot set the same property to a button more than once.";

        public Properties(GameStates screen, String name, float x, float y, Runnable runnable) {
            this.screen = screen;
            this.name = name;
            this.x = x;
            this.y = y;
            this.runnable = Objects.requireNonNull(runnable);
        }

        public Properties setText(String text) {
            return (setText(text, 65));
        }

        public Properties setText(String text, float textSize) {
            return (setText(text, textSize, Color.BLACK));
        }

        public Properties setText(String text, float textSize, Color idleColor) {
            return (setText(text, textSize, idleColor, Color.BLACK));
        }

        public Properties setText(String text, float textSize, Color idleColor, Color hoverColor) {
            if (setProperties.contains(Props.SET_TEXT)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.SET_TEXT);
            this.text = text;
            this.textSize = (float)(textSize / 1080.0);
            this.idleTextColor = idleColor;
            this.hoverTextColor = hoverColor;
            return (this);
        }

        public Properties setBounds(float w, float h) {
            if (setProperties.contains(Props.SET_BOUNDS)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.SET_BOUNDS);
            if (w <= 0.0f || h <= 0.0f) {
                Logger.FATAL("Unable to set bounds");
                throw new IllegalSpriteArgumentException("Tried to set bounds of button to or less than zero.");
            }
            this.w = w;
            this.h = h;
            return (this);
        }

        public Properties setImage(BufferedImage idleImage) {
            return (this.setImage(idleImage, null));
        }

        public Properties setImage(BufferedImage idleImage, BufferedImage hoverImage) {
            if (setProperties.contains(Props.SET_IMAGE)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.SET_IMAGE);
            this.idleImage = idleImage;
            this.hoverImage = hoverImage;
            return (this);
        }

        public Properties setBgColor(Color idleColor) {
            return(this.setBgColor(idleColor, null));
        }

        public Properties setBgColor(Color idleColor, Color hoverColor) {
            if (setProperties.contains(Props.SET_BG_COLOR)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.SET_BG_COLOR);
            this.idleColor = idleColor;
            this.hoverColor = hoverColor;
            return (this);
        }

        public Properties appendBorder(int thickness, Color idleColor, Color hoverColor) {
            if (setProperties.contains(Props.BORDER)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.BORDER);
            if (h == 0.0) {
                Logger.FATAL("Tried to append border to button with no bounds.");
                throw new IllegalSpriteArgumentException("You cannot append a border to a button with no bounds. Make sure append border is after set bounds");
            }
            border = new Border(x, y, w, h, thickness, idleColor, hoverColor);
            return (this);
        }

        public Properties appendBorder(int thickness, Color idleColor) {
            return (appendBorder(thickness, idleColor, idleColor));
        }

        public Properties drawFromTopLeft() {
            if (setProperties.contains(Props.DRAW_FROM_TOP_LEFT)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            drawFromTopLeft = true;
            setProperties.add(Props.DRAW_FROM_TOP_LEFT);
            return (this);
        }

        public Properties setKeySelections(int[] selectionIndex) {
            if (setProperties.contains(Props.KEY_SELECTIONS)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG_LOGGER);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            if (selectionIndex[0] <= -1 || selectionIndex[1] <= -1) {
                Logger.FATAL("Tried to set button's selection index to invalid position");
                throw new IllegalSpriteArgumentException("setKeySelections(int[] selectionIndex) must be used where both elements of selectionIndex are 0 or greater");
            }
            setProperties.add(Props.KEY_SELECTIONS);
            keyboardSelectIndex = selectionIndex;
            return (this);
        }

        public Button build() {
            if (text == null && idleImage == null && idleColor == null) {
                Logger.FATAL("Tried to build button with no graphics");
                throw new IllegalSpriteArgumentException("Buttons must have at least one of the following: Text, IdleImage, IdleColor.");
            }
            if (((idleColor != null) || (text != null)) && (idleImage != null)) {
                Logger.FATAL("Tried to build button with an image and text or background color");
                throw new IllegalSpriteArgumentException("Cannot have an image with background color nor text");
            }
            if (text != null && w != 0.0 && idleColor == null) {
                Logger.FATAL("Tried to build a text based button with explicitly defined bounds");
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
            return (new Button(name, screen, x, y, w, h, text, textSize, idleImage, hoverImage, idleColor, hoverColor, idleTextColor, hoverTextColor,
                    border, type, drawFromTopLeft, runnable, keyboardSelectIndex));
        }
    }

    public void render(Graphics g) {
        final int WINDOW_WIDTH = GamePanel.panel.getWidth();
        final int WINDOW_HEIGHT = GamePanel.panel.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics;
        int W;
        int H;
        int X;
        int Y;
        boolean keyboardHover = false;
        boolean hover = false;
        if (KeyboardNavMain.keyboardHoverActive) {
            if (Arrays.equals(KeyboardNavMain.getSelectedIndex(), keyboardSelectCoords)) {
                keyboardHover = true;
            }
        }
        if (keyboardHover || hitbox.checkBounds()) {
            hover = true;
        }
        switch (type) {
            case NONE:
                break;
            case TEXT:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
                hitbox.setHitbox(X, Y, fontMetrics.stringWidth(text), (fontMetrics.getAscent() + fontMetrics.getDescent()));
                g2d.setColor(hover? hoverTextColor: idleTextColor);
                g2d.drawString(text, X, Y + fontMetrics.getAscent());
                g2d.setColor(Color.BLACK);
                break;
            case TEXT_NON_HOVER:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
                hitbox.setHitbox(X, Y, fontMetrics.stringWidth(text), (fontMetrics.getAscent() + fontMetrics.getDescent()));
                g2d.setColor(idleTextColor);
                g2d.drawString(text, X, Y + fontMetrics.getAscent());
                g2d.setColor(Color.BLACK);
                break;
            case IMAGE:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                BufferedImage image = hover? hoverImage: idleImage;
                g.drawImage(image, X, Y, W, H, null);
                break;
            case IMAGE_NON_HOVER:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                BufferedImage imageNonHover = idleImage;
                g.drawImage(imageNonHover, X, Y, W, H, null);
                break;
            case COLOR:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hover? hoverColor: idleColor);
                g.fillRect(X, Y, W, H);
                g.setColor(Color.BLACK);
                break;
            case COLOR_NON_HOVER:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(idleColor);
                g.fillRect(X, Y, W, H);
                g.setColor(Color.BLACK);
                break;
            case COLOR_TEXT:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hover? hoverColor: idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(hover? hoverTextColor: idleTextColor);
                g2d.drawString(text, X + (int)((W - fontMetrics.stringWidth(text)) / 2.0), (int)((Y + fontMetrics.getAscent())
                        + ((H - (fontMetrics.getDescent()) - fontMetrics.getAscent()) / 2.0)));
                g.setColor(Color.BLACK);
                g2d.setColor(Color.BLACK);
                break;
            case COLOR_TEXT_NON_HOVER:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hover? hoverColor: idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(idleTextColor);
                g2d.drawString(text, X + (int)((W - fontMetrics.stringWidth(text)) / 2.0), (int)((Y + fontMetrics.getAscent())
                        + ((H - (fontMetrics.getDescent()) - fontMetrics.getAscent()) / 2.0)));
                g.setColor(Color.BLACK);
                g2d.setColor(Color.BLACK);
                break;
            case COLOR_NON_HOVER_TEXT:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(hover? hoverTextColor: idleTextColor);
                g2d.drawString(text, X + (int)((W - fontMetrics.stringWidth(text)) / 2.0), (int)((Y + fontMetrics.getAscent())
                        + ((H - (fontMetrics.getDescent()) - fontMetrics.getAscent()) / 2.0)));
                g.setColor(Color.BLACK);
                g2d.setColor(Color.BLACK);
                break;
            case COLOR_NON_HOVER_TEXT_NON_HOVER:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(idleTextColor);
                g2d.drawString(text, X + (int)((W - fontMetrics.stringWidth(text)) / 2.0), (int)((Y + fontMetrics.getAscent())
                        + ((H - (fontMetrics.getDescent()) - fontMetrics.getAscent()) / 2.0)));
                g.setColor(Color.BLACK);
                g2d.setColor(Color.BLACK);
                break;
        }
        if (!canRecieveInput(screen)) {
            hitbox.disableHitbox();
        }
        if (border != null) {
            border.render(g, hover);
        }
    }

    public String toString() {
        return ("Button {name " + name + ", screen " + screen + ", x " + x + ", y " + y + ", w " + width + ", h " + height + ", text " + text + ", text_size " + textSize
            + ", idle_text_color " + idleTextColor + ", hover_text_color " + hoverTextColor + ", idle_color " + idleColor
            + ", hover_color " + hoverColor + ", idle_image " + idleImage + ", hover_image " + hoverImage + ", type " + type
            + ", hitbox " + hitbox + ", border " + border + " , draw_from_top_left " + drawFromTopLeft + ", action " + runnable + "}");
    }

}
