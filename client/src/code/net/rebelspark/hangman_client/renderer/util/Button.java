package code.net.rebelspark.hangman_client.renderer.util;

import code.net.rebelspark.hangman_client.eventHandlers.MouseMovementHandler;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.IllegalSpriteArgumentException;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Objects;

public class Button {

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
        private float t;
        private Color idleColor;
        private Color hoverColor;

        private Rect borderIdle;
        private Rect borderHover;

        private Border(final float x, final float y, final float w, final float h, final float t, Color idleColor, Color hoverColor) {
            this.x = x;
            this.y = y;
            this.w = w;
            this.h = h;
            this.t = t;
            this.idleColor = idleColor;
            this.hoverColor = hoverColor;
            borderIdle = new Rect(x - (w / 2), y - (h / 2), w, h, t, idleColor);
            borderHover = new Rect(x - (w / 2), y - (h / 2), w, h, t, hoverColor);
        }

        public void updatePos(final float x, final float y) {
            borderIdle = new Rect(x - (w / 2), y - (h / 2), w, h, t, idleColor);
            borderHover = new Rect(x - (w / 2), y - (h / 2), w, h, t, hoverColor);
        }

        private void render(Graphics g, boolean hovering) {
            Rect renderThis = hovering? borderHover: borderIdle;
            renderThis.render(g);
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

    public static HashSet<Button> buttons = new HashSet<>();
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


    protected Button(String name, GameStates screen, float x, float y, float w, float h, String text, float textSize, BufferedImage idleImage, BufferedImage hoverImage,
                     Color idleColor, Color hoverColor, Color idleTextColor, Color hoverTextColor, Border border, Type type, Runnable runnable) {
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
        buttons.add(this);
        hitbox = new Hitbox();
    }

    @Deprecated
    public Button() {
        this.x = 0;
        this.y = 0;
        this.width = 10;
        this.height = 10;
        buttons.add(this);
        hitbox = new Hitbox();
    }

    @Deprecated
    public Button(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = Type.NONE;
        buttons.add(this);
        hitbox = new Hitbox();
    }

    @Deprecated
    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textSize = (float)(65.0 / 1080.0);
        this.type = Type.TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
    }

    @Deprecated
    public Button(int x, int y, int width, int height, String text, int textSize) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.type = Type.TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
    }


    //-------------------------------------------------------------------------------------------------------
    //                                            TEXT BASED
    //-------------------------------------------------------------------------------------------------------


    //creates plain text button centered at [x, y] with font size 65 and static color black
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(65.0 / 1080.0);
        this.idleTextColor = Color.black;
        this.type = Type.TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //creates plain text button centered at [x, y] with font size 65 and static color 'idleTextColor'
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(65.0 / 1080.0);
        this.idleTextColor = idleTextColor;
        this.type = Type.TEXT_NON_HOVER;
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
        buttons.add(this);
    }

    //creates plain text button centered at [x, y] with font size 65 and hoverable color 'idleTextColor' <--> 'hoverTextColor'
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(65.0 / 1080.0);
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //creates plain text button centered at ['x', 'y'] with font size 'textSize' and static color 'idleTextColor'
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, int textSize, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = Color.black;
        this.type = Type.TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //creates plain text button centered at ['x', 'y'] with font size 'textSize' and static color 'idleTextColor'
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, int textSize, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.type = Type.TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //creates plain text button centered at ['x', 'y'] with font size 'textSize' and hoverable color 'idleTextColor' <--> 'hoverTextColor'
    @Deprecated
    public Button(GameStates screen, float x, float y, String text, int textSize, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //-------------------------------------------------------------------------------------------------------
    //                                             IMAGE BASED
    //-------------------------------------------------------------------------------------------------------

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, BufferedImage idleImage, BufferedImage hoverImage, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleImage = idleImage;
        this.hoverImage = hoverImage;
        this.type = Type.IMAGE;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, BufferedImage idleImage, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleImage = idleImage;
        this.type = Type.IMAGE_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    //-------------------------------------------------------------------------------------------------------
    //                                            COLOR BASED         [pain]
    //-------------------------------------------------------------------------------------------------------

    ///just color

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.type = Type.COLOR_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.type = Type.COLOR;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    ///


    /// static text color hoverable bg color
    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = Color.BLACK;
        this.type = Type.COLOR_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = idleTextColor;
        this.type = Type.COLOR_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, float textSize, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = Color.BLACK;
        this.type = Type.COLOR_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, float textSize, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.type = Type.COLOR_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

///


///static bg color static text color

@Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, String text, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = Color.BLACK;
        this.type = Type.COLOR_NON_HOVER_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, String text, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = idleTextColor;
        this.type = Type.COLOR_NON_HOVER_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, String text, float textSize, Color idleTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.type = Type.COLOR_NON_HOVER_TEXT_NON_HOVER;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    ///


    ///static bg color hoverable text color

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, String text, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.COLOR_NON_HOVER_TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, String text, float textSize, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.COLOR_NON_HOVER_TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    ///


    ///hoverable text hoverable bg

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = DEFAULT_TEXT_SIZE;
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.COLOR_TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    @Deprecated
    public Button(GameStates screen, float x, float y, float width, float height, Color idleColor, Color hoverColor, String text, float textSize, Color idleTextColor, Color hoverTextColor, Runnable runnable) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.idleTextColor = idleTextColor;
        this.hoverTextColor = hoverTextColor;
        this.type = Type.COLOR_TEXT;
        buttons.add(this);
        hitbox = new Hitbox();
        this.runnable = Objects.requireNonNull(runnable);
    }

    ///

    @Deprecated
    public Button appendBorder(int thickness, Color idleColor, Color hoverColor) {
        border = new Border(x, y, width, height, thickness, idleColor, hoverColor);
        return (this);
    }

    public void mouseClicked(MouseEvent e) {
        if (hitbox.checkBounds(e)) {
            Logger.DEBUG(this + " clicked");
            if (runnable == null) {
                Logger.FATAL(this + " is missing action!");
                System.exit(-1);
            } else {
                runnable.run();
            }
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
            BORDER
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

        protected final String PROPERTY_ALR_DEFINED_MSG = "Tried to set one property to a button twice";

        public Properties(GameStates screen, String name, float x, float y, Runnable runnable) {
            this.screen = screen;
            this.name = name;
            this.x = x;
            this.y = y;
            this.runnable = Objects.requireNonNull(runnable);
        }

        public Properties setText(String text) {
            return (setText(text, 65f));
        }

        public Properties setText(String text, float textSize) {
            if (setProperties.contains(Props.SET_TEXT)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
            }
            setProperties.add(Props.SET_TEXT);
            this.text = text;
            this.textSize = (float)(textSize / 1080.0);
            return (this);
        }

        public Properties setBounds(float w, float h) {
            if (setProperties.contains(Props.SET_BOUNDS)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
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
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
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
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
            }
            setProperties.add(Props.SET_BG_COLOR);
            this.idleColor = idleColor;
            this.hoverColor = hoverColor;
            return (this);
        }

        public Properties setTextColor(Color idleColor) {
            return (this.setTextColor(idleColor, null));
        }

        public Properties setTextColor(Color idleColor, Color hoverColor) {
            if (setProperties.contains(Props.SET_TEXT_COLOR)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
            }
            setProperties.add(Props.SET_TEXT_COLOR);
            this.idleTextColor = idleColor;
            this.hoverTextColor = hoverColor;
            return (this);
        }

        public Properties appendBorder(int thickness, Color idleColor, Color hoverColor) {
            if (setProperties.contains(Props.BORDER)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_MSG);
                throw new IllegalSpriteArgumentException("You cannot set the same property to a button more than once.");
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
                    border, type, runnable));
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
        switch (type) {
            case NONE:
                break;
            case TEXT:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                X = (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
                hitbox.setHitbox(X, Y, fontMetrics.stringWidth(text), (fontMetrics.getAscent() + fontMetrics.getDescent()));
                g2d.setColor(hitbox.checkBounds()? hoverTextColor: idleTextColor);
                g2d.drawString(text, X, Y + fontMetrics.getAscent());
                g2d.setColor(Color.BLACK);
                break;
            case TEXT_NON_HOVER:
                g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
                fontMetrics = g2d.getFontMetrics();
                X = (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
                hitbox.setHitbox(X, Y, fontMetrics.stringWidth(text), (fontMetrics.getAscent() + fontMetrics.getDescent()));
                g2d.setColor(idleTextColor);
                g2d.drawString(text, X, Y + fontMetrics.getAscent());
                g2d.setColor(Color.BLACK);
                break;
            case IMAGE:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                BufferedImage image = hitbox.checkBounds()? hoverImage: idleImage;
                g.drawImage(image, X, Y, W, H, null);
                break;
            case IMAGE_NON_HOVER:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                BufferedImage imageNonHover = idleImage;
                g.drawImage(imageNonHover, X, Y, W, H, null);
                break;
            case COLOR:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hitbox.checkBounds()? hoverColor: idleColor);
                g.fillRect(X, Y, W, H);
                g.setColor(Color.BLACK);
                break;
            case COLOR_NON_HOVER:
                W = (int)(width * WINDOW_WIDTH);
                H = (int)(height * WINDOW_HEIGHT);
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
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
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hitbox.checkBounds()? hoverColor: idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(hitbox.checkBounds()? hoverTextColor: idleTextColor);
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
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(hitbox.checkBounds()? hoverColor: idleColor);
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
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
                hitbox.setHitbox(X, Y, W, H);
                g.setColor(idleColor);
                g.fillRect(X, Y, W, H);
                g2d.setColor(hitbox.checkBounds()? hoverTextColor: idleTextColor);
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
                X = (int)((x * WINDOW_WIDTH) - (W / 2.0));
                Y = (int)((y * WINDOW_HEIGHT) - (H / 2.0));
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
            border.render(g, hitbox.checkBounds());
        }
    }

    public String toString() {
        return ("Button {x " + x + ", y " + y + ", w " + width + ", h " + height + ", text " + text + ", text_size " + textSize
            + ", idle_text_color " + idleTextColor + ", hover_text_color " + hoverTextColor + ", idle_color " + idleColor
            + ", hover_color " + hoverColor + ", idle_image " + idleImage + ", hover_image " + hoverColor + ", type " + type
            + ", hitbox " + hitbox + ", action " + runnable + "}");
    }

}
