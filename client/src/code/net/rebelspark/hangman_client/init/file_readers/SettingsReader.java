package code.net.rebelspark.hangman_client.init.file_readers;

import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;


public class SettingsReader {
    public enum SETTINGS {
        RESOLUTION,
        FULLSCREEN
    }
    public static void readSettings() {
        if (!Files.exists(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH)) {
            Logger.INFO("Attempting to create user profile settings");
            try {
                Files.createDirectories(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH.getParent());
                Files.createFile(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH);
                Logger.INFO("Successfully created user profile settings file");
                Files.write(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH, "\"window_size\": '480p'\n\"fullscreen\": 'false'".getBytes());
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
        final String SETTINGS_CONTENTS_STRING = settingsContents.toString().toLowerCase();
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

        RedSettings.storeSettings(settingsMap);
    }
    private static String extractSetting(final String NAME, final String CONTENTS) {
        if (!CONTENTS.contains(NAME)) {
            throw new IllegalArgumentException("unknown setting \"" + NAME + "\"");
        }
        final int INDEX_KEY_START = CONTENTS.indexOf("'", CONTENTS.indexOf("\"" + NAME + "\""));
        return (CONTENTS.substring(INDEX_KEY_START + 1, CONTENTS.indexOf("'", INDEX_KEY_START + 1)));
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
    }
}
