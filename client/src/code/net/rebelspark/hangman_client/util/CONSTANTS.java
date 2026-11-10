package code.net.rebelspark.hangman_client.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class CONSTANTS {
    public static final String VERSION_NUMBER = "ALPHA-0.2";
    public static final String WINDOW_NAME = "Hangman 2.0  --  " + VERSION_NUMBER + " BUILD 2026.11.10-11.56";

    public static final Path HANGMAN_PATH = Paths.get(System.getProperty("user.home"), ".hangman_rspark");
    public static final Path USER_PROFILE_SETTINGS_FILEPATH = Paths.get(HANGMAN_PATH.toString(), "settings", "user_settings.hangman");
    public static final Path USER_PROFILE_SUGGESTED_WORDS_PATH = Paths.get(HANGMAN_PATH.toString(), "suggested");
}
