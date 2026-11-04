package code.net.rebelspark.hangman_client.util;

import java.awt.*;

public class MoreGraphicsMethods {

    public static void drawThickRect(Graphics g, int x, int y, int w, int h, int t) {
        int x1 = 0;
        int y1 = 0;
        int w1 = 0;
        int h1 = 0;
        for (int i = 0; i < t; i++) {
            g.drawRect(x - x1, y + y1, w - w1, h - h1);
            x++;
            y++;
            w -= 2;
            h -= 2;
        }
    }

}
