package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_settings.GameSettings;
import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.CheckBox;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class Settings {

    public static final Button MAIN_MENU_BUTTON = new Button(GameStates.SETTINGS, 0.06f, 0.06f, "<--", 55, Color.BLACK, Color.BLUE, () -> GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU));
    private static final Label HEADER_LABEL = new Label(0.5f, 0.075f, "SETTINGS", 100);

    public static final Label RESOLUTION_LABEL = new Label(0.5f, 0.3f, ResolutionHandler.getFormattedResolution(ResolutionHandler.Resolutions.P480));

    public static final CheckBox FULLSCREEN_CHECKBOX = new CheckBox(GameStates.SETTINGS, 0.6f, 0.425f, 0.05f, Color.BLACK, Color.GRAY, () -> {
        GameSettings.potentialNewSettings.fullscreen = !GameSettings.potentialNewSettings.fullscreen;
        if (GameSettings.potentialNewSettings.fullscreen) {
            RESOLUTION_LABEL.setText("Fullscreen");
        } else {
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    });

    private static final Button RESOLUTION_DOWN = new Button(GameStates.SETTINGS, 0.35f, 0.3f, 0.07f, 0.07f, new Color(45, 45, 45),
            new Color(100, 100, 100), "<", 70, () -> {
        if (!FULLSCREEN_CHECKBOX.checked) {
            ResolutionHandler.attemptDownRes();
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    });

    private static final Button RESOLUTION_UP = new Button(GameStates.SETTINGS, 0.65f, 0.3f, 0.07f, 0.07f, new Color(45, 45, 45),
            new Color(100, 100, 100), ">", 70, () -> {
        if (!FULLSCREEN_CHECKBOX.checked) {
            ResolutionHandler.attemptUpRes();
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    });

    private static final Label FULLSCREEN_LABEL = new Label(0.4f, 0.425f, "Fullscreen", 50);

    public static final Button SETTINGS_APPLY = new Button(GameStates.SETTINGS, 0.5f, 0.95f, 0.15f, 0.1f, Color.DARK_GRAY, Color.GRAY,
            "Apply", 60, Color.BLACK, GameSettings::pushNewSettings);



    public static void render(Graphics g, int w, int h) {

        MAIN_MENU_BUTTON.render(g);
        HEADER_LABEL.render(g);

        RESOLUTION_LABEL.render(g);
        RESOLUTION_DOWN.render(g);
        RESOLUTION_UP.render(g);

        FULLSCREEN_LABEL.render(g);
        FULLSCREEN_CHECKBOX.render(g, w, h);

        SETTINGS_APPLY.render(g);
    }

}
