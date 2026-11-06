package code.net.rebelspark.hangman_client.keybinds.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.renderer.screens.in_game.BaseGameScreen;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;

import java.awt.event.KeyEvent;

public class InGame_Keybinds implements KeybindInterface {
    @Override
    public void acceptKey(int KEY) {
        Classic.receiveInput(Keyboard.VkIntToKeys(KEY));
        if (KEY == KeyEvent.VK_TAB) {
            //debug screen
        }
    }
}
