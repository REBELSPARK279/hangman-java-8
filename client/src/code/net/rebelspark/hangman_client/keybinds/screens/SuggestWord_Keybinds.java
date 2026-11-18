package code.net.rebelspark.hangman_client.keybinds.screens;

import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.renderer.screens.SuggestWord;
import code.net.rebelspark.hangman_client.renderer.features.Keyboard;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;

import java.awt.event.KeyEvent;

public class SuggestWord_Keybinds implements KeybindInterface {
    @Override
    public void acceptKey(int KEY) {
        if (KEY == KeyEvent.VK_BACK_SPACE) {
            SuggestWord.backspace();
        }
        if (KEY == KeyEvent.VK_ENTER) {
            SuggestWord.enter();
        }
        if (KEY == KeyEvent.VK_ESCAPE) {
            SuggestWord.writeSuggestions();
            GameStateHandler.setGameState(GameStates.MAIN_MENU);
        }
        SuggestWord.receiveInput(Keyboard.VkIntToKeys(KEY));
    }
}
