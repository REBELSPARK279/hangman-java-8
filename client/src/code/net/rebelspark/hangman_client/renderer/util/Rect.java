package code.net.rebelspark.hangman_client.renderer.util;

import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.util.MoreGraphicsMethods;

import java.awt.*;

public class Rect {
    private float x;
    private float y;
    private float w;
    private float h;
    private float t;
    private Color color;

    public Rect(float x, float y, float w, float h, float t, Color color) {
        this.x = x;
        this. y = y;
        this. w = w;
        this.h = h;
        this.t = t;
        this.color = color;
    }

    public Rect(float x, float y, float w, float h, float t) {
        this(x, y, w, h, t, Color.BLACK);
    }

    public void render(Graphics g) {
        final int WINDOW_W = GamePanel.panel.getWidth();
        final int WINDOW_H = GamePanel.panel.getHeight();
        int X = (int)(x * WINDOW_W);
        int Y = (int)(y * WINDOW_H);
        int W = (int)(w * WINDOW_W);
        int H = (int)(h * WINDOW_H);
        int T = (int)((t / 1080.0) * WINDOW_H);
        g.setColor(color);
        MoreGraphicsMethods.drawThickRect(g, X, Y, W, H, T);
        g.setColor(Color.BLACK);
    }

}
