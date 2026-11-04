package code.net.rebelspark.hangman_client.renderer;

import code.net.rebelspark.hangman_client.eventHandlers.KeyListnerHandler;
import code.net.rebelspark.hangman_client.eventHandlers.MouseListenerHandler;
import code.net.rebelspark.hangman_client.eventHandlers.MouseMovementHandler;
import code.net.rebelspark.hangman_client.renderer.screens.GameMenu;
import code.net.rebelspark.hangman_client.renderer.screens.MainMenu;
import code.net.rebelspark.hangman_client.renderer.screens.Settings;
import code.net.rebelspark.hangman_client.renderer.screens.in_game.BaseGameScreen;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.util.CONSTANTS;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    public static JFrame window;
    public static GamePanel panel;

    public GamePanel() {
        setFocusable(true);
        addMouseMotionListener(new MouseMovementHandler());
        addMouseListener(new MouseListenerHandler());
        addKeyListener(new KeyListnerHandler());
    }

    public static void init() {
        SwingUtilities.invokeLater(() -> {
            window = new JFrame(CONSTANTS.WINDOW_NAME);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setSize(CONSTANTS.WINDOW_INIT_SIZE_W, CONSTANTS.WINDOW_INIT_SIZE_H);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            panel = new GamePanel();
            window.add(panel);

            window.setVisible(true);
            panel.requestFocusInWindow();
        });

    }

    public static float matchHeightToWidth(float width) {
        return ((width / 9.0f) * 16.0f);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (GameStateHandler.getGameState()) {
            case MAIN_MENU:
                MainMenu.render(g, getWidth(), getHeight());
                break;
            case MAIN_MENU__EXIT_SCREEN:
                MainMenu.ExitScreen.render(g, getWidth(), getHeight());
                break;
            case SETTINGS:
                Settings.render(g, getWidth(), getHeight());
                break;
            case GAME_MENU:
                GameMenu.render(g, getWidth(), getHeight());
                break;
            case IN_GAME_BASE:
                BaseGameScreen.render(g, getWidth(), getHeight());
                break;
            case SHOP:
                break;
        }
    }

}
