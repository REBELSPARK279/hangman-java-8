package code.net.rebelspark.hangman_client.keybinds.screens;

import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.renderer.screens.in_game.EndOverlay;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.event.KeyEvent;

public class MainMenu_Keybinds implements KeybindInterface {
    @Override
    public void acceptKey(final int KEY) {
        switch (KEY) {
            case (KeyEvent.VK_ESCAPE):
                GameStateHandler.setGameState(GameStates.MAIN_MENU__EXIT_SCREEN);
        }
    }
}
