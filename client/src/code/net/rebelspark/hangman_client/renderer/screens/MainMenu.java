package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_settings.GameSettings;
import code.net.rebelspark.hangman_client.game_settings.ResolutionHandler;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.FilledRect;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.*;

public class MainMenu {

    private static final Button settingsButton = new Button(GameStates.MAIN_MENU, 0.66f, 0.5f, "Settings", 65, Color.black, Color.blue, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.SETTINGS);
        GameSettings.potentialNewSettings = GameSettings.currentSettings.clone();
        if (Settings.FULLSCREEN_CHECKBOX.checked) {
            Settings.RESOLUTION_LABEL.setText("Fullscreen");
        } else {
            Settings.RESOLUTION_LABEL.setText(ResolutionHandler.getFormattedResolution(GameSettings.potentialNewSettings.resolution));
        }
        Settings.FULLSCREEN_CHECKBOX.checked = GameSettings.potentialNewSettings.fullscreen;
    });

    private static final Button playButton = new Button(GameStates.MAIN_MENU, 0.33f, 0.5f, 0.2f, 0.1f, new Color(65, 65, 65),
            new Color(115, 115, 115), "Play", 65, Color.BLACK, Color.BLUE, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.GAME_MENU);
    });

    public static void render(Graphics graphics, int width, int height) {

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setFont(Fonts.RUSSO_ONE.deriveFont((float)((100.0 / 1080.0) * height)));
        FontMetrics fontMetrics = g2d.getFontMetrics();

        g2d.drawString("HANGMAN", (width / 2) - (fontMetrics.stringWidth("HANGMAN") / 2),
                (int)((100.0 / 1080.0) * height) + (int)((75.0 / 1080.0) * height));
        g2d.setFont(Fonts.RUBIK.deriveFont((float)(65.0 / 1080.0) * height));
        fontMetrics = g2d.getFontMetrics();

        playButton.render(graphics, width, height);
        settingsButton.render(graphics, width, height);



    }

    public static class ExitScreen {

        private static final FilledRect BANNER = new FilledRect(0.5f, 0.5f, 1.0f, 0.4f, Color.DARK_GRAY);

        private static final Label LABEL = new Label(0.5f, 0.37f, "Exit Game?", 95);

        private static final Button EXIT = new Button(GameStates.MAIN_MENU__EXIT_SCREEN, 0.33f, 0.55f, 0.125f, 0.1f, new Color(65, 65, 65), new Color(105, 105, 105),
                "Exit", 70, Color.BLACK, () -> System.exit(0)).appendBorder(7, Color.BLACK, Color.GRAY);

        private static final Button BACK = new Button(GameStates.MAIN_MENU__EXIT_SCREEN, 0.66f, 0.55f, 0.125f, 0.1f, new Color(65, 65, 65), new Color(105, 105, 105),
                "Return", 70, Color.BLACK, () -> GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU)).appendBorder(7, Color.BLACK, Color.GRAY);

        public static void render(Graphics g, int w, int h) {
            BANNER.render(g, w, h);
            LABEL.render(g, w, h);
            EXIT.render(g, w, h);
            BACK.render(g, w, h);
        }
    }

}
