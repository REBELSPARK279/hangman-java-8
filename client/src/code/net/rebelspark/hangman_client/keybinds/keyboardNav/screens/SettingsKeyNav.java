package code.net.rebelspark.hangman_client.keybinds.keyboardNav.screens;

import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavKeyHelper;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavMain;

public class SettingsKeyNav implements KeyboardNavKeyHelper {
    @Override
    public void handleLeft() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        pos[0] = 0;
        KeyboardNavMain.setSelectedIndex(pos);
    }

    @Override
    public void handleRight() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        if (pos[1] == 0 || pos[1] == 2) {
            pos[0] = 1;
        }
        KeyboardNavMain.setSelectedIndex(pos);
    }

    @Override
    public void handleUp() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        if (pos[1] > 0) {
            pos[1]--;
            pos[0] = 0;
        }
        KeyboardNavMain.setSelectedIndex(pos);
    }

    @Override
    public void handleDown() {
        int[] pos = KeyboardNavMain.getSelectedIndex();
        if (pos[1] < 3) {
            pos[1]++;
            pos[0] = 0;
        }
        KeyboardNavMain.setSelectedIndex(pos);
    }
}
