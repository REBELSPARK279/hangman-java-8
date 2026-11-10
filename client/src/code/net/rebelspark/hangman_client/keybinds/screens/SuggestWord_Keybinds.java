package code.net.rebelspark.hangman_client.keybinds.screens;

import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.renderer.screens.SuggestWord;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;

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
        SuggestWord.receiveInput(Keyboard.VkIntToKeys(KEY));
    }
}
