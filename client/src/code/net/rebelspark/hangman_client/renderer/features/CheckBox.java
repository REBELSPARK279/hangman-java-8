package code.net.rebelspark.hangman_client.renderer.features;

import code.net.rebelspark.hangman_client.eventHandlers.MouseMovementHandler;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavMain;
import code.net.rebelspark.hangman_client.renderer.features.interfaces.InputtableFeature;
import code.net.rebelspark.hangman_client.renderer.features.util.InputtableFeatures;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.Logger;
import code.net.rebelspark.hangman_client.util.MoreGraphicsMethods;
import code.net.rebelspark.hangman_client.util.exceptions.IllegalSpriteArgumentException;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashSet;

public class CheckBox implements InputtableFeature {

    protected static class Hitbox {
        private int hitboxX;
        private int hitboxY;
        private int hitboxW;
        private int hitboxH;

        protected Hitbox(){}

        protected void setHitbox(int x, int y, int w, int h) {
            hitboxX = x;
            hitboxY = y;
            hitboxW = w;
            hitboxH = h;
        }

        @Deprecated
        protected boolean checkBounds(MouseEvent e) {
            int mouseX = e.getX();
            int mouseY = e.getY();
            return((mouseX >= hitboxX) && (mouseX <= hitboxX + hitboxW) && (mouseY >= hitboxY) && (mouseY <= hitboxY + hitboxH));
        }

        protected boolean checkBounds() {
            int mouseX = MouseMovementHandler.getMouseX();
            int mouseY = MouseMovementHandler.getMouseY();
            return((mouseX >= hitboxX) && (mouseX <= hitboxX + hitboxW) && (mouseY >= hitboxY) && (mouseY <= hitboxY + hitboxH));
        }

        public String toString() {
            return ("hitbox [x " + hitboxX + ", y " + hitboxY + ", w " + hitboxW + ", h " + hitboxH + "]");
        }

    }

    private enum Type {
        STATIC,
        HOVERABLE
    }

    protected GameStates screen;
    public boolean checked;
    protected float x;
    protected float y;
    protected float w;
    protected Color idleColor;
    protected Color hoverColor;
    protected Type type;
    protected Hitbox hitbox;
    protected Runnable action;
    protected boolean drawFromTopLeft;
    protected int[] keySelectionPos;

    protected CheckBox(GameStates screen, float x, float y, float w, Color idleColor, Color hoverColor, boolean checkedByDefault, boolean drawFromTopLeft, Runnable action, int[] keySelectionPos) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.w = w;
        this.idleColor = idleColor;
        this.hoverColor = hoverColor;
        this.checked = checkedByDefault;
        this.type = Type.HOVERABLE;
        hitbox = new Hitbox();
        this.action = action;
        this.keySelectionPos = keySelectionPos;
        this.drawFromTopLeft = drawFromTopLeft;
        InputtableFeatures.INPUTTABLE_FEATURES.add(this);
    }

    public static class Properties {
        private GameStates screen;
        public boolean checked = false;
        private float x;
        private float y;
        private float w;
        private Color idleColor;
        private Color hoverColor;
        private Type type;
        private Runnable action;
        private boolean drawFromTopLeft;
        protected int[] keyboardSelectIndex = {-1, -1};

        private enum Props {
            COLOR,
            CHECKED,
            DRAW_TL,
            KEY_SELECTIONS
        }

        protected final String PROPERTY_ALR_DEFINED_LOGGER_MSG = "Tried to set one property to a label more than once";
        protected final String PROPERTY_ALR_DEFINED_THROWED_MSG = "You cannot set the same property to a label more than once.";

        private final HashSet<Props> appliedProperties = new HashSet<>();

        public Properties(GameStates screen, float x, float y, float w, Runnable action) {
            this.screen = screen;
            this.x = x;
            this.y = y;
            this.w = w;
            this.action = action;
        }

        public Properties setColor(Color idleColor, Color hoverColor) {
            if (appliedProperties.contains(Props.COLOR)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            appliedProperties.add(Props.COLOR);
            this.idleColor = idleColor;
            this.hoverColor = hoverColor;
            return (this);
        }

        public Properties setColor(Color idleColor) {
            return (setColor(idleColor, idleColor));
        }

        public Properties drawFromTopLeft() {
            if (appliedProperties.contains(Props.DRAW_TL)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            appliedProperties.add(Props.DRAW_TL);
            this.drawFromTopLeft = true;
            return (this);
        }

        public Properties checkedByDefault(boolean bool) {
            if (appliedProperties.contains(Props.CHECKED)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            appliedProperties.add(Props.CHECKED);
            checked = bool;
            return (this);
        }

        public Properties setKeySelection(int[] selectionIndex) {
            if (appliedProperties.contains(Properties.Props.KEY_SELECTIONS)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            if (selectionIndex[0] <= -1 || selectionIndex[1] <= -1) {
                Logger.FATAL("Tried to set button's selection index to invalid position");
                throw new IllegalSpriteArgumentException("setKeySelections(int[] selectionIndex) must be used where both elements of selectionIndex are 0 or greater");
            }
            appliedProperties.add(Properties.Props.KEY_SELECTIONS);
            keyboardSelectIndex = selectionIndex;
            return (this);
        }

        public CheckBox build() {
            if (idleColor == null) {
                idleColor = Color.BLACK;
                hoverColor = Color.BLACK;
            }
            return (new CheckBox(screen, x, y, w, idleColor, hoverColor, checked, drawFromTopLeft, action, keyboardSelectIndex));
        }

    }

    @Override
    public GameStates getRenderScreen() {
        return (this.screen);
    }

    @Override
    public void input() {
        checked = !checked;
        action.run();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (hitbox.checkBounds(e)) {
            input();
        }
    }

    @Override
    public void enterClicked(int[] selection) {
        if (Arrays.equals(selection, keySelectionPos)) {
            input();
        }
    }

    public void render(Graphics g, final int WINDOW_W, final int WINDOW_H) {
        int X = drawFromTopLeft? (int)(x * WINDOW_W): (int)((x - (w / 2)) * WINDOW_W);
        int Y = drawFromTopLeft? (int)(y * WINDOW_H): (int)((y * WINDOW_H) - ((w / 2) * WINDOW_W));
        int W = (int)(w * WINDOW_W);
        hitbox.setHitbox(X, Y, W, W);
        boolean hovering = hitbox.checkBounds() || (Arrays.equals(KeyboardNavMain.getSelectedIndex(), keySelectionPos) && KeyboardNavMain.keyboardHoverActive);
        g.setColor(hovering? hoverColor: idleColor);
        MoreGraphicsMethods.drawThickRect(g, X, Y, W, W, (int)((5.0 / 1080.0) * WINDOW_H));
        if (checked) {
            g.fillRect(X + (int) (W * 0.2), Y + (int) (W * 0.2), (int) (W * 0.6), (int) (W * 0.6));
        }
        g.setColor(Color.BLACK);
    }
}
