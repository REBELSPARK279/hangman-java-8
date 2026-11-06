package code.net.rebelspark.hangman_client.game_settings;

import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.screens.Settings;
import code.net.rebelspark.hangman_client.util.Logger;

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
        if (currentSettings.fullscreen != potentialNewSettings.fullscreen) {
            ResolutionHandler.toggleFullscreen();
        }

        if (!potentialNewSettings.fullscreen && (currentSettings.resolution != potentialNewSettings.resolution)) {
            ResolutionHandler.setResolution(potentialNewSettings.resolution);
        }

        currentSettings = potentialNewSettings.clone();
    }

    public static GameSettings currentSettings = new GameSettings(ResolutionHandler.Resolutions.P480, false);
    public static GameSettings potentialNewSettings = new GameSettings();
}
