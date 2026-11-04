package code.net.rebelspark.hangman_client.renderer.util;

import java.awt.*;

public class FilledRect {
    private float x;
    private float y;
    private float w;
    private float h;
    private Color color;

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

    public void render(Graphics g, final int WINDOW_W, final int WINDOW_H) {
        int X = (int)(x * WINDOW_W);
        int Y = (int)(y * WINDOW_H);
        int W = (int)(w * WINDOW_W);
        int H = (int)(h * WINDOW_H);
        g.setColor(color);
        g.fillRect(X - (W / 2), Y - (H / 2), W, H);
        g.setColor(Color.BLACK);
    }

}
