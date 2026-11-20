package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_settings.FpsLimiter;
import code.net.rebelspark.hangman_client.game_settings.GameSettings;
import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.init.file_readers.SettingsReader;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.features.button.Button;
import code.net.rebelspark.hangman_client.renderer.features.CheckBox;
import code.net.rebelspark.hangman_client.renderer.features.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class Settings {

    private static final float RESOLUTION_Y = 0.2f;
    private static final float FULLSCREEN_Y = 0.35f;
    private static final float FPS_LIMITERS = 0.5f;

    private static final Button MAIN_MENU_BUTTON = new Button.Properties(GameStates.SETTINGS, "menu", 0.06f, 0.06f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU);
    }).setText("<--", 55, Color.BLACK, Color.BLUE).build();

    private static final Label HEADER_LABEL = new Label.Properties(0.5f, 0.075f, "SETTINGS").textSize(100).build();

    public static final Label RESOLUTION_LABEL = new Label.Properties(0.5f, RESOLUTION_Y, ResolutionHandler.getFormattedResolution(SettingsReader.RedSettings.parseResolution())).build();

    public static final CheckBox FULLSCREEN_CHECKBOX = new CheckBox.Properties(GameStates.SETTINGS, 0.6f, FULLSCREEN_Y, 0.05f, () -> {
        GameSettings.potentialNewSettings.fullscreen = !GameSettings.potentialNewSettings.fullscreen;
        if (GameSettings.potentialNewSettings.fullscreen) {
            RESOLUTION_LABEL.setText("Fullscreen");
        } else {
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    }).setColor(Color.BLACK, Color.GRAY).checkedByDefault(SettingsReader.RedSettings.parseFullscreen()).setKeySelection(new int[]{0, 1}).build();

    private static final Button RESOLUTION_DOWN = new Button.Properties(GameStates.SETTINGS, "res_down", 0.35f, RESOLUTION_Y, () -> {
        if (!FULLSCREEN_CHECKBOX.checked) {
            ResolutionHandler.attemptDownRes();
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    }).setBounds(0.07f, 0.07f).setBgColor(new Color(45, 45, 45), new Color(100, 100, 100)).setText("<", 70).setKeySelections(new int[]{0, 0}).build();

    private static final Button RESOLUTION_UP = new Button.Properties(GameStates.SETTINGS, "res_up", 0.65f, RESOLUTION_Y, () -> {
        if (!FULLSCREEN_CHECKBOX.checked) {
            ResolutionHandler.attemptUpRes();
            RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
    }).setBounds(0.07f, 0.07f).setBgColor(new Color(45, 45, 45), new Color(100, 100, 100)).setText(">", 70).setKeySelections(new int[]{1, 0}).build();

    private static final Label FULLSCREEN_LABEL = new Label.Properties(0.4f, FULLSCREEN_Y, "Fullscreen").textSize(50).build();


    public static final Label FPS_LIMIT_LABEL = new Label.Properties(0.5f, FPS_LIMITERS, "-").textSize(60).build();

    private static final Button FPS_LIMIT_DOWN = new Button.Properties(GameStates.SETTINGS, "fps_lim_down", 0.35f, FPS_LIMITERS, () -> {
        FpsLimiter.attemptDownFpsLim();
        FPS_LIMIT_LABEL.setText(FpsLimiter.getFormattedPotentialNewFpsLim());
    }).setBounds(0.07f, 0.07f).setBgColor(new Color(45, 45, 45), new Color(100, 100, 100)).setText("<", 70).setKeySelections(new int[]{0, 2}).build();

    private static final Button FPS_LIMIT_UP = new Button.Properties(GameStates.SETTINGS, "fps_lim_up", 0.65f, FPS_LIMITERS, () -> {
        FpsLimiter.attemptUpFpsLim();
        FPS_LIMIT_LABEL.setText(FpsLimiter.getFormattedPotentialNewFpsLim());
    }).setBounds(0.07f, 0.07f).setBgColor(new Color(45, 45, 45), new Color(100, 100, 100)).setText(">", 70).setKeySelections(new int[]{1, 2}).build();



    private static final Button SETTINGS_APPLY = new Button.Properties(GameStates.SETTINGS, "apply", 0.5f, 0.94f, GameSettings::pushNewSettings)
            .setBounds(0.2f, 0.1f).setBgColor(Color.DARK_GRAY, Color.GRAY).setText("Apply", 60, Color.BLACK).setKeySelections(new int[]{0, 3}).build();


    public static void render(Graphics g, int w, int h) {

        MAIN_MENU_BUTTON.render(g);
        HEADER_LABEL.render(g);

        RESOLUTION_LABEL.render(g);
        RESOLUTION_DOWN.render(g);
        RESOLUTION_UP.render(g);

        FULLSCREEN_LABEL.render(g);
        FULLSCREEN_CHECKBOX.render(g, w, h);

        FPS_LIMIT_LABEL.render(g);
        FPS_LIMIT_DOWN.render(g);
        FPS_LIMIT_UP.render(g);

        SETTINGS_APPLY.render(g);


        g.drawLine(0, (int)(FULLSCREEN_Y * GamePanel.panel.getHeight()), GamePanel.panel.getWidth(), (int)(FULLSCREEN_Y * GamePanel.panel.getHeight()));
        g.drawLine(0, (int)(RESOLUTION_Y * GamePanel.panel.getHeight()), GamePanel.panel.getWidth(), (int)(RESOLUTION_Y * GamePanel.panel.getHeight()));
    }

}
