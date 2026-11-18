package code.net.rebelspark.hangman_client.keybinds.keyboardNav;

import code.net.rebelspark.hangman_client.keybinds.keyboardNav.overlays.in_game.EndOverlayKeyNav;
import code.net.rebelspark.hangman_client.keybinds.keyboardNav.screens.*;
import code.net.rebelspark.hangman_client.renderer.features.interfaces.InputtableFeature;
import code.net.rebelspark.hangman_client.renderer.features.util.InputtableFeatures;
import code.net.rebelspark.hangman_client.renderer.overlay.in_game.EndOverlay;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.Logger;

import java.util.ArrayList;
import java.util.HashMap;

public class KeyboardNavMain {

    public enum NAV_KEYS {
        LEFT,
        RIGHT,
        UP,
        DOWN,
        ENTER
    }

    private static KeyboardNavKeyHelper currentScreen;
    private static HashMap<GameStates, KeyboardNavKeyHelper> pairings = new HashMap<>();
    static {
        pairings.put(GameStates.MAIN_MENU, new MainMenuKeyNav());
        pairings.put(GameStates.SETTINGS, new SettingsKeyNav());
        pairings.put(GameStates.MAIN_MENU__EXIT_SCREEN, new ExitGameKeyNav());
        pairings.put(GameStates.GAME_MENU, new GameMenuKeyNav());
    }
    public static void updateScreen(GameStates screen) {
        currentScreen = pairings.getOrDefault(screen, new EMPTY_KeyNav());
    }

    public static void keyClick(NAV_KEYS key) {
        if (EndOverlay.getState() && (GameStateHandler.getGameState().equals(GameStates.IN_GAME_BASE))) {
            currentScreen = new EndOverlayKeyNav();
        }
        keyboardHoverActive = true;
        switch (key) {
            case LEFT:
                currentScreen.handleLeft();
                break;
            case RIGHT:
                currentScreen.handleRight();
                break;
            case UP:
                currentScreen.handleUp();
                break;
            case DOWN:
                currentScreen.handleDown();
                break;
            case ENTER:
                for (InputtableFeature clickable: new ArrayList<>(InputtableFeatures.INPUTTABLE_FEATURES)) {
                    if (clickable.getRenderScreen().equals(GameStateHandler.getGameState()) ||
                            (EndOverlay.getState() && clickable.getRenderScreen() == GameStates.END_SCREEN &&
                            (GameStateHandler.getGameState() == GameStates.IN_GAME_BASE))) {
                        clickable.enterClicked(selectedIndex);
                    }
                }
                GameStateHandler.setDelayedState();
                break;
        }
        Logger.DEBUG(key);
        Logger.DEBUG("{" + selectedIndex[0] + ", " + selectedIndex[1] + "}");
    }

    private static int[] selectedIndex = {0, 0};
    public static boolean keyboardHoverActive = false;

    public static int[] getSelectedIndex() {
        return (selectedIndex);
    }

    public static void setSelectedIndex(int[] index) {
        selectedIndex = index;
    }

}
