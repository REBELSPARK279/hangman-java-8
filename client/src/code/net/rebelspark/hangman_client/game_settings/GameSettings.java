package code.net.rebelspark.hangman_client.game_settings;

import code.net.rebelspark.hangman_client.init.file_readers.SettingsReader;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

import java.io.IOException;
import java.nio.file.Files;

public class GameSettings {

    public ResolutionHandler.Resolutions resolution;
    public boolean fullscreen;

    public GameSettings(ResolutionHandler.Resolutions resolution, boolean fullscreen) {
        this.resolution = resolution;
        this.fullscreen = fullscreen;
    }

    private GameSettings() {}

    public GameSettings(GameSettings other) {
        this.fullscreen = other.fullscreen;
        this.resolution = other.resolution;
    }

    public GameSettings clone() {
        return new GameSettings(this);
    }

    public String toString() {
        return ("GameSettings [fullscreen " + fullscreen + ", resolution " + resolution + "]");
    }

    @Deprecated
    public static void pushNewSettings(int x) {
        ResolutionHandler.setResolution(potentialNewSettings.resolution);
        Logger.DEBUG(currentSettings);
        Logger.DEBUG(potentialNewSettings);
        if (currentSettings.fullscreen != potentialNewSettings.fullscreen) {
            ResolutionHandler.toggleFullscreen();
        }
        currentSettings = potentialNewSettings.clone();
    }

    public static void pushNewSettings() {

        final String formattedSettings =
                "\"window_size\": '" + potentialNewSettings.resolution.toString().substring(1) + "p'\n" +
                "\"fullscreen\": '" + potentialNewSettings.fullscreen + "'\n";

        try {
            Files.write(CONSTANTS.USER_PROFILE_SETTINGS_FILEPATH, formattedSettings.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (currentSettings.fullscreen != potentialNewSettings.fullscreen) {
            ResolutionHandler.toggleFullscreen();
        }

        if (!potentialNewSettings.fullscreen && (currentSettings.resolution != potentialNewSettings.resolution)) {
            ResolutionHandler.setResolution(potentialNewSettings.resolution);
        }

        currentSettings = potentialNewSettings.clone();
    }

    public static GameSettings currentSettings = new GameSettings(SettingsReader.RedSettings.parseResolution(), SettingsReader.RedSettings.parseFullscreen());
    public static GameSettings potentialNewSettings = new GameSettings();
}
