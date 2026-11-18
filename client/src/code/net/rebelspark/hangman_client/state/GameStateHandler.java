package code.net.rebelspark.hangman_client.state;

import code.net.rebelspark.hangman_client.keybinds.keyboardNav.StoredPositions;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.KeyboardNavMain;

public class GameStateHandler {

    private static GameStates gameState = GameStates.MAIN_MENU;
    private static GameStates delayedState;

    public static GameStates getGameState() {
        return (gameState);
    }

    public static void setGameState(GameStates gameState) {
        GameStateHandler.gameState = gameState;
        KeyboardNavMain.updateScreen(gameState);
        KeyboardNavMain.setSelectedIndex(StoredPositions.storedPositions.get(gameState));
    }

    public static void triggerDelayedSetGameState(GameStates gameState) {
        delayedState = gameState;
    }

    public static void setDelayedState() {
        if (delayedState != null) {
            setGameState(delayedState);
            delayedState = null;
        }
    }

}
