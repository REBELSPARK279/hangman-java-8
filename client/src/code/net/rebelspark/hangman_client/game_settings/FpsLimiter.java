package code.net.rebelspark.hangman_client.game_settings;

import code.net.rebelspark.hangman_client.util.Logger;

import java.util.LinkedHashMap;

public class FpsLimiter {

    public enum FPS_LIMS {
        F24,
        F30,
        F48,
        F60,
        F75,
        F100,
        F144,
        F180,
        F240,
        F300,
        F360,
        F512,
        FNONE
    }

    private static LinkedHashMap<FPS_LIMS, Integer> eiLinks = new LinkedHashMap<>();
    private static LinkedHashMap<Integer, FPS_LIMS> ieLinks = new LinkedHashMap<>();
    static {
        eiLinks.put(FPS_LIMS.F24, 24);
        eiLinks.put(FPS_LIMS.F30, 30);
        eiLinks.put(FPS_LIMS.F48, 48);
        eiLinks.put(FPS_LIMS.F60, 60);
        eiLinks.put(FPS_LIMS.F75, 75);
        eiLinks.put(FPS_LIMS.F100, 100);
        eiLinks.put(FPS_LIMS.F144, 144);
        eiLinks.put(FPS_LIMS.F180, 180);
        eiLinks.put(FPS_LIMS.F240, 240);
        eiLinks.put(FPS_LIMS.F300, 300);
        eiLinks.put(FPS_LIMS.F360, 360);
        eiLinks.put(FPS_LIMS.F512, 512);
        eiLinks.put(FPS_LIMS.FNONE, 0);

        ieLinks.put(24, FPS_LIMS.F24);
        ieLinks.put(30, FPS_LIMS.F30);
        ieLinks.put(48, FPS_LIMS.F48);
        ieLinks.put(60, FPS_LIMS.F60);
        ieLinks.put(75, FPS_LIMS.F75);
        ieLinks.put(100, FPS_LIMS.F100);
        ieLinks.put(144, FPS_LIMS.F144);
        ieLinks.put(180, FPS_LIMS.F180);
        ieLinks.put(240, FPS_LIMS.F240);
        ieLinks.put(300, FPS_LIMS.F300);
        ieLinks.put(360, FPS_LIMS.F360);
        ieLinks.put(512, FPS_LIMS.F512);
        ieLinks.put(0, FPS_LIMS.FNONE);
    }

    private static int fpsLimit = -2;
    private static int potentialNewFpsLimit = -2;

    public static void initFpsLimitHandling() {
        potentialNewFpsLimit = fpsLimit;
    }

    public static void setFpsLimit(FPS_LIMS lim) {
        int out = -1;
        switch (lim) {
            case F24:
                out = 24;
                break;
            case F30:
                out = 30;
                break;
            case F48:
                out = 48;
                break;
            case F60:
                out = 60;
                break;
            case F75:
                out = 75;
                break;
            case F100:
                out = 100;
                break;
            case F144:
                out = 144;
                break;
            case F180:
                out = 180;
                break;
            case F240:
                out = 240;
                break;
            case F300:
                out = 300;
                break;
            case F360:
                out = 360;
                break;
            case F512:
                out = 512;
                break;
            case FNONE:
                out = 0;
                break;
        }
        if (out == -1) {
            Logger.FATAL("Found fps limit in a bad state");
            throw new IllegalStateException("Unknown FPS limit");
        }
        fpsLimit = out;
    }

    public static void attemptDownFpsLim() {
        switch (potentialNewFpsLimit) {
            case 24:
            case 30:
                potentialNewFpsLimit = 24;
                break;
            case 48:
                potentialNewFpsLimit = 30;
                break;
            case 60:
                potentialNewFpsLimit = 48;
                break;
            case 75:
                potentialNewFpsLimit = 60;
                break;
            case 100:
                potentialNewFpsLimit = 75;
                break;
            case 144:
                potentialNewFpsLimit = 100;
                break;
            case 180:
                potentialNewFpsLimit = 144;
                break;
            case 240:
                potentialNewFpsLimit = 180;
                break;
            case 300:
                potentialNewFpsLimit = 240;
                break;
            case 360:
                potentialNewFpsLimit = 300;
                break;
            case 512:
                potentialNewFpsLimit = 360;
                break;
            case 0:
                potentialNewFpsLimit = 512;
                break;
        }
        GameSettings.potentialNewSettings.fpsLimit = ieLinks.getOrDefault(potentialNewFpsLimit, FPS_LIMS.FNONE);
    }

    public static void attemptUpFpsLim() {
        switch (potentialNewFpsLimit) {
            case 24: potentialNewFpsLimit = 30; break;
            case 30: potentialNewFpsLimit = 48; break;
            case 48: potentialNewFpsLimit = 60; break;
            case 60: potentialNewFpsLimit = 75; break;
            case 75: potentialNewFpsLimit = 100; break;
            case 100: potentialNewFpsLimit = 144; break;
            case 144: potentialNewFpsLimit = 180; break;
            case 180: potentialNewFpsLimit = 240; break;
            case 240: potentialNewFpsLimit = 300; break;
            case 300: potentialNewFpsLimit = 360; break;
            case 360: potentialNewFpsLimit = 512; break;
            case 512:
            case 0:
                potentialNewFpsLimit = 0; break;
        }
        GameSettings.potentialNewSettings.fpsLimit = ieLinks.getOrDefault(potentialNewFpsLimit, FPS_LIMS.FNONE);
    }

    public static void settingsApplied() {
        fpsLimit = potentialNewFpsLimit;
    }

    public static int getFpsLim() {
        if (fpsLimit == -2) {
            Logger.FATAL("Tried to access fps limit before it was set");
            throw new IllegalArgumentException("FPS limit has not yet been defined");
        }
        return (fpsLimit);
    }

    public static int getPotentialNewFpsLimit() {
        if (potentialNewFpsLimit == -2) {
            Logger.FATAL("Tried to access fps limit before it was set");
            throw new IllegalArgumentException("FPS limit has not yet been defined");
        }
        return (potentialNewFpsLimit);
    }

    public static String getFormattedFpsLim() {
        if (fpsLimit == -2) {
            Logger.FATAL("Tried to access fps limit before it was set");
            throw new IllegalArgumentException("FPS limit has not yet been defined");
        }
        return (fpsLimit == 0? "Unlimited FPS": fpsLimit + "FPS limit");
    }

    public static String getFormattedPotentialNewFpsLim() {
        if (potentialNewFpsLimit == -2) {
            Logger.FATAL("Tried to access fps limit before it was set");
            throw new IllegalArgumentException("FPS limit has not yet been defined");
        }
        return (potentialNewFpsLimit == 0? "Unlimited FPS": potentialNewFpsLimit + "FPS limit");
    }

    public static int parseEnum(FPS_LIMS in) {
        return (eiLinks.getOrDefault(in, 0));
    }

    public static FPS_LIMS parseInt(int in) {
        return (ieLinks.getOrDefault(in, FPS_LIMS.FNONE));
    }

}
