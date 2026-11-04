package code.net.rebelspark.hangman_client.keybinds.subscreens.main_menu;

import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.event.KeyEvent;

public class MainMenu_ExitScreen_Keybinds implements KeybindInterface {
    @Override
    public void acceptKey(final int KEY) {
        if (KEY == KeyEvent.VK_ESCAPE) {
            GameStateHandler.setGameState(GameStates.MAIN_MENU);
        }
    }
}
