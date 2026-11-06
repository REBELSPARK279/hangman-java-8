package code.net.rebelspark.hangman_client.renderer.util;

import code.net.rebelspark.hangman_client.eventHandlers.MouseMovementHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.MoreGraphicsMethods;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class CheckBox {

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

    public static ArrayList<CheckBox> checkBoxes = new ArrayList<>();

    private GameStates screen;
    public boolean checked;
    private float x;
    private float y;
    private float w;
    private Color idleColor;
    private Color hoverColor;
    private Type type;
    private Hitbox hitbox;
    private Runnable action;

    public CheckBox(GameStates screen, float x, float y, float w, Color idleColor, Color hoverColor, boolean checkedByDefault, Runnable action) {
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
        checkBoxes.add(this);
    }

    public CheckBox(GameStates screen, float x, float y, float w, Color idleColor, Color hoverColor, Runnable action) {
        this(screen, x, y, w, idleColor, hoverColor, false, action);
    }

    public CheckBox(GameStates screen, float x, float y, float w, Color idleColor, boolean checkedByDefault, Runnable action) {
        this.screen = screen;
        this.x = x;
        this.y = y;
        this.w = w;
        this.idleColor = idleColor;
        this.checked = checkedByDefault;
        this.type = Type.STATIC;
        hitbox = new Hitbox();
        this.action = action;
        checkBoxes.add(this);
    }

    public CheckBox(GameStates screen, float x, float y, float w, Color idleColor, Runnable action) {
        this(screen, x, y, w, idleColor, false, action);
    }

    public GameStates getRenderScreen() {
        return (this.screen);
    }

    public void mouseClicked(MouseEvent e) {
        if (hitbox.checkBounds(e)) {
            checked = !checked;
            action.run();
        }
    }

    public void render(Graphics g, final int WINDOW_W, final int WINDOW_H) {
        int X = (int)(x * WINDOW_W);
        int Y = (int)(y * WINDOW_H);
        int W = (int)(w * WINDOW_W);
        hitbox.setHitbox(X, Y, W, W);
        MoreGraphicsMethods.drawThickRect(g, X, Y, W, W, (int)((5.0 / 1080.0) * WINDOW_H));
        if (checked) {
            g.fillRect(X + (int) (W * 0.2), Y + (int) (W * 0.2), (int) (W * 0.6), (int) (W * 0.6));
        }
    }
}
