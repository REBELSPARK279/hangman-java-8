package code.net.rebelspark.hangman_client.renderer.features;

import code.net.rebelspark.hangman_client.renderer.GamePanel;

import java.awt.*;

public class FilledRect {
    private float x;
    private float y;
    private float w;
    private float h;
    private Color color;
    private boolean drawFromTopLeft = false;

    public FilledRect(float x, float y, float w, float h, Color color) {
        this.x = x;
        this. y = y;
        this. w = w;
        this.h = h;
        this.color = color;
    }

    public FilledRect(float x, float y, float w, float h) {
        this(x, y, w, h, Color.BLACK);
    }

    public FilledRect drawFromTopLeft() {
        drawFromTopLeft = true;
        return (this);
    }

    public void render(Graphics g) {
        final int WINDOW_W = GamePanel.panel.getWidth();
        final int WINDOW_H = GamePanel.panel.getHeight();
        int W = (int)(w * WINDOW_W);
        int H = (int)(h * WINDOW_H);
        int X = drawFromTopLeft? (int)(x * WINDOW_W): (int)((x * WINDOW_W) - (W / 2d));
        int Y = drawFromTopLeft? (int)(y * WINDOW_H): (int)((y * WINDOW_H) - (H / 2d));
        g.setColor(color);
        g.fillRect(X, Y, W, H);
        g.setColor(Color.BLACK);
    }

}
