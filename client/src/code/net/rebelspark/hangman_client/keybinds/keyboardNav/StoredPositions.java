package code.net.rebelspark.hangman_client.keybinds.keyboardNav;

import code.net.rebelspark.hangman_client.state.GameStates;

import java.util.HashMap;

public class StoredPositions {
    public static HashMap<GameStates, int[]> storedPositions = new HashMap<>();
    static {
        storedPositions.put(GameStates.MAIN_MENU, new int[]{0, 0});
        storedPositions.put(GameStates.MAIN_MENU__EXIT_SCREEN, new int[]{1, 0});
        storedPositions.put(GameStates.SETTINGS, new int[]{0, 0});
        storedPositions.put(GameStates.GAME_MENU, new int[]{0, 0});
        storedPositions.put(GameStates.IN_GAME_BASE, new int[]{0, 0});
        storedPositions.put(GameStates.END_SCREEN, new int[]{1, 0});
        storedPositions.put(GameStates.SHOP, new int[]{0, 0});
        storedPositions.put(GameStates.SUGGEST_WORD, new int[]{0, 0});
    }
}
