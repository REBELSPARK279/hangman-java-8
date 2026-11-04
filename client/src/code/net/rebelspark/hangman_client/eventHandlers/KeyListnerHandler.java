package code.net.rebelspark.hangman_client.eventHandlers;

import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.keybinds.screens.*;
import code.net.rebelspark.hangman_client.keybinds.subscreens.main_menu.MainMenu_ExitScreen_Keybinds;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyListnerHandler implements KeyListener {

    @Override
    public void keyPressed(KeyEvent e) {
        Logger.DEBUG("key");
        final int KEY = e.getKeyCode();
        KeybindInterface target = null;
        switch (GameStateHandler.getGameState()) {
            case MAIN_MENU:
                target = new MainMenu_Keybinds();
                break;
            case MAIN_MENU__EXIT_SCREEN:
                target = new MainMenu_ExitScreen_Keybinds();
                break;
            case SETTINGS:
                target = new Settings_Keybinds();
                break;
            case GAME_MENU:
                target = new GameMenu_Keybinds();
                break;
            case IN_GAME_BASE:
                target = new InGame_Keybinds();
                break;
            case SHOP:
                target = new Shop_Keybinds();
                break;
        }
        if (target == null) {
            Logger.CRITICAL("Gamestate " + GameStateHandler.getGameState() + " has not been registered with 'KeyListenerHandler.java'");
            throw new RuntimeException("A GAME STATE HAS NOT BEEN REGISTERED WITH 'KeyListenerHandler.java'!");
        }
        target.acceptKey(KEY);
        GamePanel.panel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
