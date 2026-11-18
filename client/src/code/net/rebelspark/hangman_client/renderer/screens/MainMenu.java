package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_settings.FpsLimiter;
import code.net.rebelspark.hangman_client.game_settings.GameSettings;
import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.features.Button;
import code.net.rebelspark.hangman_client.renderer.features.FilledRect;
import code.net.rebelspark.hangman_client.renderer.features.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class MainMenu {

    private static final Button settingsButton = new Button.Properties(GameStates.MAIN_MENU, "settings", 0.667f, 0.5f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.SETTINGS);
        GameSettings.potentialNewSettings = GameSettings.currentSettings.clone();
        if (Settings.FULLSCREEN_CHECKBOX.checked) {
            Settings.RESOLUTION_LABEL.setText("Fullscreen");
        } else {
            Settings.RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
        FpsLimiter.initFpsLimitHandling();
        Settings.FULLSCREEN_CHECKBOX.checked = GameSettings.potentialNewSettings.fullscreen;
        Settings.RESOLUTION_LABEL.setText(GameSettings.currentSettings.fullscreen? "Fullscreen": ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        Settings.FPS_LIMIT_LABEL.setText(FpsLimiter.getFormattedFpsLim());
    }).setText("Settings", 65, Color.BLACK, Color.BLUE).setKeySelections(new int[]{1, 0}).build();

    private static final Button playButton = new Button.Properties(GameStates.MAIN_MENU, "play", 0.322f/*creo is everywhere*/, 0.5f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.GAME_MENU);
    }).setBounds(0.2f, 0.1f).setBgColor(new Color(65, 65, 65), new Color(115, 115, 115)).setKeySelections(new int[]{0, 0})
            .setText("Play", 65, Color.BLACK, Color.BLUE).build();

    private static final Button suggestWordButton = new Button.Properties(GameStates.MAIN_MENU, "suggest_word", 0.5f, 0.9f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.SUGGEST_WORD);
    }).setText("Suggest Words", 40, Color.BLACK).setBgColor(Color.DARK_GRAY, Color.GRAY).setBounds(0.2f, 0.1f).setKeySelections(new int[]{0, 1})
            .appendBorder(8, Color.BLACK).build();

    public static void render(Graphics g, int width, int height) {

        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(Fonts.RUSSO_ONE.deriveFont((float)((100.0 / 1080.0) * height)));
        FontMetrics fontMetrics = g2d.getFontMetrics();

        g2d.drawString("HANGMAN", (width / 2) - (fontMetrics.stringWidth("HANGMAN") / 2),
                (int)((100.0 / 1080.0) * height) + (int)((75.0 / 1080.0) * height));
        g2d.setFont(Fonts.RUBIK.deriveFont((float)(65.0 / 1080.0) * height));
        fontMetrics = g2d.getFontMetrics();

        playButton.render(g);
        settingsButton.render(g);
        suggestWordButton.render(g);

    }

    public static class ExitScreen {

        private static final FilledRect BANNER = new FilledRect(0.5f, 0.5f, 1.0f, 0.4f, Color.DARK_GRAY);

        private static final Label LABEL = new Label.Properties(0.5f, 0.37f, "Exit Game?").textSize(95).build();

        private static final Button EXIT = new Button.Properties(GameStates.MAIN_MENU__EXIT_SCREEN, "exit", 0.33f, 0.55f, () -> {
            System.exit(0);
        }).setBounds(0.125f, 0.1f).setBgColor(new Color(65, 65, 65), new Color(105, 105, 105)).setText("Exit", 70,
                Color.BLACK).appendBorder(7, Color.BLACK, Color.GRAY).setKeySelections(new int[]{0, 0}).build();

        private static final Button BACK = new Button.Properties(GameStates.MAIN_MENU__EXIT_SCREEN, "back", 0.66f, 0.55f, () -> {
            GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU);
        }).setBounds(0.125f, 0.1f).setBgColor(new Color(65, 65, 65), new Color(105, 105, 105)).setText("Return", 70,
                Color.BLACK).appendBorder(7, Color.BLACK).setKeySelections(new int[]{1, 0}).build();

        public static void render(Graphics g, int w, int h) {
            BANNER.render(g);
            LABEL.render(g);
            EXIT.render(g);
            BACK.render(g);
        }
    }

}
