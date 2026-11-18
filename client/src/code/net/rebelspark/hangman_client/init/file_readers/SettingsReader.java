package code.net.rebelspark.hangman_client.init.file_readers;

import code.net.rebelspark.hangman_client.game_settings.FpsLimiter;
import code.net.rebelspark.hangman_client.game_settings.GameSettings;
import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;


public class SettingsReader {
    public enum SETTINGS {
        RESOLUTION,
        FULLSCREEN,
        FPS_LIMIT
    }
    public static void readSettings() {
        if (!Files.exists(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH)) {
            Logger.INFO("Attempting to create user profile settings");
            try {
                Files.createDirectories(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH.getParent());
                Files.createFile(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH);
                Logger.INFO("Successfully created user profile settings file");
                Files.write(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH, "\"window_size\": '480p'\n\"fullscreen\": 'false'\n\"fps_limit\": 'fnone'".getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        final List<String> settingsContents;
        try {
            settingsContents = Files.readAllLines(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        final String SETTINGS_CONTENTS_STRING = validateSettings(settingsContents.toString().toLowerCase());
        final HashMap<SETTINGS, String> settingsMap = new HashMap<SETTINGS, String>(){
            @Override
            public String put(final SETTINGS KEY, final String VALUE) {
                Logger.INFO("Reading setting \"" + KEY + "\"");
                if (this.containsKey(KEY)) {
                    throw new IllegalArgumentException("Settings hashap cannot put a key more than once");
                }
                return super.put(KEY, VALUE.toLowerCase());
            }
        };
        settingsMap.put(SETTINGS.RESOLUTION, extractSetting("window_size", SETTINGS_CONTENTS_STRING));
        settingsMap.put(SETTINGS.FULLSCREEN, extractSetting("fullscreen", SETTINGS_CONTENTS_STRING));
        settingsMap.put(SETTINGS.FPS_LIMIT, extractSetting("fps_limit", SETTINGS_CONTENTS_STRING));

        RedSettings.storeSettings(settingsMap);
        FpsLimiter.setFpsLimit(RedSettings.parseFpsLimit());
    }

    private static String extractSetting(final String NAME, final String CONTENTS) {
        if (!CONTENTS.contains(NAME)) {
            Logger.CRITICAL("unknown setting \"" + NAME + "\"");
        }
        final int INDEX_KEY_START = CONTENTS.indexOf("'", CONTENTS.indexOf("\"" + NAME + "\""));
        return (CONTENTS.substring(INDEX_KEY_START + 1, CONTENTS.indexOf("'", INDEX_KEY_START + 1)));
    }

    private static String validateSettings(final String CONTENTS) {
        LinkedHashMap<String, String> knownSettings = new LinkedHashMap<>();
        knownSettings.put("window_size", "720p");
        knownSettings.put("fullscreen", "false");
        knownSettings.put("fps_limit", "fnone");
        StringBuilder builder = new StringBuilder();
        for (final String SETTING: knownSettings.keySet()) {
            Logger.DEBUG(SETTING);
            if (CONTENTS.contains(SETTING)) {
                builder.append("\"").append(SETTING).append("\": '").append(extractSetting(SETTING, CONTENTS)).append("'\n");
            } else {
                if (knownSettings.containsKey(SETTING)) {
                    builder.append("\"").append(SETTING).append("\": '").append(knownSettings.get(SETTING)).append("'\n");
                } else {
                    throw new IllegalArgumentException("unknown setting \"" + SETTING + "\"");
                }
            }
        }
        try {
            Files.write(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH, builder.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return (builder.toString());
    }

    public static class RedSettings /*past tense version of read, chill*/ {
        private static HashMap<SETTINGS, String> storedSettings = new HashMap<>();
        private static void storeSettings(HashMap<SETTINGS, String> in) {
            storedSettings = new HashMap<>(in);
        }
        public static HashMap<SETTINGS, String> getStoredSettings() {
            return (new HashMap<>(storedSettings));
        }

        public static ResolutionHandler.Resolutions parseResolution() {
            ResolutionHandler.Resolutions res = null;
            switch (getStoredSettings().get(SETTINGS.RESOLUTION)) {
                case "360p":
                    res = ResolutionHandler.Resolutions.P360;
                    break;
                case "480p":
                    res = ResolutionHandler.Resolutions.P480;
                    break;
                case "720p":
                    res = ResolutionHandler.Resolutions.P720;
                    break;
                case "1080p":
                    res = ResolutionHandler.Resolutions.P1080;
                    break;
                case "1440p":
                    res = ResolutionHandler.Resolutions.P1440;
                    break;
                case "2160p" :
                    res = ResolutionHandler.Resolutions.P2160;
                    break;
            };
            if (res == null) {
                Logger.CRITICAL("Malformed user settings file!");
                res = ResolutionHandler.Resolutions.P480;
            }
            return (res);
        }

        public static boolean parseFullscreen() {
            if (getStoredSettings().get(SETTINGS.FULLSCREEN).equalsIgnoreCase("true")) {
                return (true);
            } else if (getStoredSettings().get(SETTINGS.FULLSCREEN).equalsIgnoreCase("false")) {
                return (false);
            } else {
                Logger.CRITICAL("Malformed user settings file!");
                return (false);
            }
        }

        public static FpsLimiter.FPS_LIMS parseFpsLimit() {
            FpsLimiter.FPS_LIMS out = null;
            switch (getStoredSettings().get(SETTINGS.FPS_LIMIT).toUpperCase()) {
                case "F24":
                    out = FpsLimiter.FPS_LIMS.F24;
                    break;
                case "F30":
                    out = FpsLimiter.FPS_LIMS.F30;
                    break;
                case "F48":
                    out = FpsLimiter.FPS_LIMS.F48;
                    break;
                case "F60":
                    out = FpsLimiter.FPS_LIMS.F60;
                    break;
                case "F75":
                    out = FpsLimiter.FPS_LIMS.F75;
                    break;
                case "F100":
                    out = FpsLimiter.FPS_LIMS.F100;
                    break;
                case "F144":
                    out = FpsLimiter.FPS_LIMS.F144;
                    break;
                case "F180":
                    out = FpsLimiter.FPS_LIMS.F180;
                    break;
                case "F240":
                    out = FpsLimiter.FPS_LIMS.F240;
                    break;
                case "F300":
                    out = FpsLimiter.FPS_LIMS.F300;
                    break;
                case "F360":
                    out = FpsLimiter.FPS_LIMS.F360;
                    break;
                case "F512":
                    out = FpsLimiter.FPS_LIMS.F512;
                    break;
                case "FNONE":
                    out = FpsLimiter.FPS_LIMS.FNONE;
                    break;
            }
            if (out == null) {
                Logger.CRITICAL("Malformed user settings file!");
                out = FpsLimiter.FPS_LIMS.FNONE;
            }
            return (out);
        }
    }
}
