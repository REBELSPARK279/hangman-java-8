package code.net.rebelspark.hangman_client.keybinds.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.Classic;
import code.net.rebelspark.hangman_client.keybinds.handlers.KeybindInterface;
import code.net.rebelspark.hangman_client.renderer.features.Keyboard;

public class InGame_Keybinds implements KeybindInterface {
    @Override
    public void acceptKey(int KEY) {
        Classic.receiveInput(Keyboard.VkIntToKeys(KEY));
    }
}
