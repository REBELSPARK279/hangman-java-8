package code.net.rebelspark.hangman_client.keybinds.keyboardNav.overlays.in_game;

import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavKeyHelper;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavMain;

public class EndOverlayKeyNav implements KeyboardNavKeyHelper {
    @Override
    public void handleLeft() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        pos[0] = 0;
        KeyboardNavMain.setSelectedIndex(pos);
    }

    @Override
    public void handleRight() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        pos[0] = 1;
        KeyboardNavMain.setSelectedIndex(pos);
    }

    @Override
    public void handleUp() {}

    @Override
    public void handleDown() {}
}
