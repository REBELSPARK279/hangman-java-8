package code.net.rebelspark.hangman_client.renderer.overlay.common;

import code.net.rebelspark.hangman_client.game_settings.FpsLimiter;
import code.net.rebelspark.hangman_client.renderer.features.FilledRect;
import code.net.rebelspark.hangman_client.renderer.features.Label;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.FPSCalc;

import java.awt.*;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DebugOverlay {

    public static void startClass() {}

    private static final Label VERSION_LABEL = new Label.Properties(0.02f, 0.02f, "Version: " + CONSTANTS.VERSION_NUMBER).textSize(60).drawFromTopLeft().build();
    private static final Label FPS_LABEL = new Label.Properties(0.02f, 0.07f, "FPS: ").textSize(60).drawFromTopLeft().build();
    private static final Label TIME_LABEL = new Label.Properties(0.02f, 0.12f, "Time: ").textSize(60).drawFromTopLeft().build();
    private static final Label PROGRAM_TIME_LABEL = new Label.Properties(0.02f, 0.17f, "Program Time: ").textSize(60).drawFromTopLeft().build();
    private static final long PROGRAM_START_TIME = System.currentTimeMillis();

    private static final FilledRect DIMMER_LEFT = new FilledRect(0.175f, 0.1f, 0.35f, 0.3f, new Color(0, 0, 0, 100));


    public static void render(Graphics g) {

        DIMMER_LEFT.render(g);

        VERSION_LABEL.render(g);
        FPS_LABEL.setText("FPS: " + FPSCalc.getFPS() + "   Lim " + (FpsLimiter.getFpsLim() == 0? "inf": FpsLimiter.getFpsLim())).render(g);
        TIME_LABEL.setText("Time: " + LocalTime.now().truncatedTo(ChronoUnit.SECONDS)).render(g);
        PROGRAM_TIME_LABEL.setText("Program Time: " + ((System.currentTimeMillis() - PROGRAM_START_TIME) / 1000)).render(g);
    }
}
