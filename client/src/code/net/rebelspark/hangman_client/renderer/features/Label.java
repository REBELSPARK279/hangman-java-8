package code.net.rebelspark.hangman_client.renderer.features;

import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.util.Logger;
import code.net.rebelspark.hangman_client.util.exceptions.IllegalSpriteArgumentException;

import java.awt.*;
import java.util.HashSet;

public class Label {

    private float x;
    private float y;
    private String text;
    private float textSize;
    private Color textColor;
    private Font font;
    private boolean drawFromTopLeft;

    protected Label(float x, float y, String text, float textSize, Font font, Color textColor, boolean drawFromTopLeft) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.textSize = textSize;
        this.textColor = textColor;
        this.drawFromTopLeft = drawFromTopLeft;
    }


    public static class Properties {
        private float x;
        private float y;
        private String text;
        private float textSize;
        private Color textColor;
        private Font font;
        private boolean drawFromTopLeft = false;

        protected final String PROPERTY_ALR_DEFINED_LOGGER_MSG = "Tried to set one property to a label more than once";
        protected final String PROPERTY_ALR_DEFINED_THROWED_MSG = "You cannot set the same property to a label more than once.";

        enum Props {
            SIZE,
            COLOR,
            DRAW_FROM_TOP_LEFT
        }

        private HashSet<Props> setProperties = new HashSet<>();

        public Properties(final float x, final float y, final String text) {
            this.x = x;
            this.y = y;
            this.text = text;
        }

        public Properties textSize(final int textSize) {
            if (setProperties.contains(Props.SIZE)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.SIZE);
            this.textSize = (float)(textSize / 1080.0);
            return (this);
        }

        public Properties textColor(final Color textColor) {
            if (setProperties.contains(Props.COLOR)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.COLOR);
            this.textColor = textColor;
            return (this);
        }

        public Properties drawFromTopLeft() {
            if (setProperties.contains(Props.DRAW_FROM_TOP_LEFT)) {
                Logger.FATAL(PROPERTY_ALR_DEFINED_LOGGER_MSG);
                throw new IllegalSpriteArgumentException(PROPERTY_ALR_DEFINED_THROWED_MSG);
            }
            setProperties.add(Props.DRAW_FROM_TOP_LEFT);
            drawFromTopLeft = true;
            return (this);
        }

        public Label build() {
            textSize = textSize <= 0f? ((float)(65.0 / 1080.0)): textSize;
            textColor = textColor == null? Color.BLACK: textColor;
            return (new Label(x, y, text, textSize, font, textColor, drawFromTopLeft));
        }
    }

    public Label setText(String text) {
        this.text = text;
        return (this);
    }

    public Label setFont(Font font) {
        this.font = font;
        return (this);
    }

    public void render(Graphics g) {
        final int WINDOW_WIDTH = GamePanel.panel.getWidth();
        final int WINDOW_HEIGHT = GamePanel.panel.getHeight();
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fontMetrics;
        g2d.setFont(Fonts.RUBIK.deriveFont(textSize * WINDOW_HEIGHT));
        fontMetrics = g2d.getFontMetrics();
        int X = drawFromTopLeft? (int)(x * WINDOW_WIDTH): (int)((x * WINDOW_WIDTH) - (fontMetrics.stringWidth(text) / 2.0));
        int Y = drawFromTopLeft? (int)(y * WINDOW_HEIGHT): (int)((y * WINDOW_HEIGHT) - ((fontMetrics.getDescent() + fontMetrics.getAscent()) / 2.0));
        g2d.setColor(textColor);
        g2d.drawString(text, X, Y + fontMetrics.getAscent());
        g2d.setColor(Color.BLACK);
    }

}
