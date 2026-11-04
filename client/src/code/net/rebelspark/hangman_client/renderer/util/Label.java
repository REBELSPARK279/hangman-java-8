package code.net.rebelspark.hangman_client.renderer.util;

import code.net.rebelspark.hangman_client.init.regestries.Fonts;

import java.awt.*;

public class Label {

    private float x;
    private float y;
    private String text;
    private float textSize;
    private Color textColor;

    public Label(float x, float y, String text, int textSize, Font font, Color textColor) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = (float)(textSize / 1080.0);
        this.textColor = textColor;
    }

    public Label(float x, float y, String text) {
        this(x, y, text, 65, Fonts.RUBIK, Color.BLACK);
    }
    public Label(float x, float y, String text, int textSize) {
        this(x, y, text, textSize, Fonts.RUBIK, Color.BLACK);
    }
    public Label(float x, float y, String text, int textSize, Color textColor) {
        this(x, y, text, textSize, Fonts.RUBIK, textColor);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void render(Graphics g, final int WINDOW_WIDTH, final int WINDOW_HEIGHT) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics;
        g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
        fontMetrics = g2d.getFontMetrics();
        int X = (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
        int Y = (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
        g2d.setColor(textColor);
        g2d.drawString(text, X, Y + fontMetrics.getAscent());
        g2d.setColor(Color.BLACK);
    }

}
