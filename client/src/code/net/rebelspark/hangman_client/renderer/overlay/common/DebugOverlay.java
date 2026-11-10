package code.net.rebelspark.hangman_client.renderer.overlay.common;

import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.util.FilledRect;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.FPSCalc;

import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DebugOverlay {
    private static final Label VERSION_LABEL = new Label(0.15f, 0.05f, "Version: " + CONSTANTS.VERSION_NUMBER, 60);
    private static final Label FPS_LABEL = new Label(0.15f, 0.1f, "FPS: ", 60);
    private static final Label TIME_LABEL = new Label(0.15f, 0.15f, "Time: " + LocalTime.now(), 60);

    private static final FilledRect DIMMER_LEFT = new FilledRect(0.175f, 0.1f, 0.35f, 0.2f, new Color(0, 0, 0, 100));


    public static void render(Graphics g) {
        final int WINDOW_W = GamePanel.panel.getWidth();
        final int WINDOW_H = GamePanel.panel.getHeight();

        DIMMER_LEFT.render(g);

        VERSION_LABEL.render(g);
        FPS_LABEL.setText("FPS: " + FPSCalc.getFPS()).render(g);
        TIME_LABEL.setText("Time: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)).render(g);
    }
}
