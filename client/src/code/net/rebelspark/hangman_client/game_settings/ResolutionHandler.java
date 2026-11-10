package code.net.rebelspark.hangman_client.game_settings;

import code.net.rebelspark.hangman_client.init.file_readers.SettingsReader;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.util.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class ResolutionHandler {
    public enum Resolutions {
        P360,
        P480,
        P720,
        P1080,
        P1440,
        P2160
    }

    private  static final HashMap<Resolutions, int[]> RES_CONV = new HashMap<>();

    static {
        RES_CONV.put(Resolutions.P360, new int[]{360, 640});
        RES_CONV.put(Resolutions.P480, new int[]{480, 854});
        RES_CONV.put(Resolutions.P720, new int[]{720, 1280});
        RES_CONV.put(Resolutions.P1080, new int[]{1080, 1920});
        RES_CONV.put(Resolutions.P1440, new int[]{1440, 2560});
        RES_CONV.put(Resolutions.P2160, new int[]{2160, 3840});
    }

    public static void attemptDownRes() {
        switch (GameSettings.potentialNewSettings.resolution) {
            case P360:
                break;
            case P480:
                GameSettings.potentialNewSettings.resolution = Resolutions.P360;
                break;
            case P720:
                GameSettings.potentialNewSettings.resolution = Resolutions.P480;
                break;
            case P1080:
                GameSettings.potentialNewSettings.resolution = Resolutions.P720;
                break;
            case P1440:
                GameSettings.potentialNewSettings.resolution = Resolutions.P1080;
                break;
            case P2160:
                GameSettings.potentialNewSettings.resolution = Resolutions.P1440;
                break;
        }
        Logger.DEBUG(GameSettings.potentialNewSettings.resolution);
    }

    public static void attemptUpRes() {
        switch (GameSettings.potentialNewSettings.resolution) {
            case P360:
                GameSettings.potentialNewSettings.resolution = Resolutions.P480;
                break;
            case P480:
                GameSettings.potentialNewSettings.resolution = Resolutions.P720;
                break;
            case P720:
                GameSettings.potentialNewSettings.resolution = Resolutions.P1080;
                break;
            case P1080:
                GameSettings.potentialNewSettings.resolution = Resolutions.P1440;
                break;
            case P1440:
                GameSettings.potentialNewSettings.resolution = Resolutions.P2160;
                break;
            case P2160:
                break;

        }
        Logger.DEBUG(GameSettings.potentialNewSettings.resolution);
    }

    private static boolean fullscreen;

    private static Rectangle windowedBounds;

    public static void toggleFullscreen() {
        boolean enteringFullscreen = !GamePanel.window.isUndecorated();

        if (enteringFullscreen) {
            windowedBounds = GamePanel.window.getBounds();
        }

        Rectangle screenBounds = GamePanel.window.getGraphicsConfiguration().getBounds();

        GamePanel.window.dispose();

        GamePanel.window.setExtendedState(JFrame.NORMAL);
        GamePanel.window.setUndecorated(enteringFullscreen);

        if (enteringFullscreen) {
            GamePanel.window.setBounds(screenBounds);
        } else if (windowedBounds != null) {
            GamePanel.window.setBounds(windowedBounds);
        }

        GamePanel.window.setVisible(true);
        fullscreen = enteringFullscreen;

        Logger.DEBUG("Fullscreen: " + fullscreen);
    }

    public static void setResolution(Resolutions res) {
        GamePanel.window.setResizable(true);
        Logger.DEBUG("set  res to " + RES_CONV.get(res)[1] + ", " + RES_CONV.get(res)[0]);
        GamePanel.panel.setPreferredSize(new Dimension/*CREO REFERENCE!??!?*/(RES_CONV.get(res)[1], RES_CONV.get(res)[0]));
        GamePanel.window.pack();
        GamePanel.window.setResizable(false);
    }

    public static void startStartupResolution() {
        Resolutions res = SettingsReader.RedSettings.parseResolution();
        int[] dimensions /*creo?*/ = RES_CONV.get(res);
        GamePanel.panel.setPreferredSize(new Dimension/*Im convinced its a Creo reference*/(dimensions[1], dimensions[0]));

    }

    public static void startStartupFullscreen() {
        String fullscreen = SettingsReader.RedSettings.getStoredSettings().get(SettingsReader.SETTINGS.FULLSCREEN);
        if (fullscreen.equalsIgnoreCase("true")) {
            toggleFullscreen();
        }
    }

    public static String getFormattedResolution(Resolutions res) {
        return (RES_CONV.get(res)[0] + "p");
    }

}
