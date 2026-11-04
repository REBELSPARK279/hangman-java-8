package code.net.rebelspark.hangman_client.state;

public class GameStateHandler {

    private static GameStates gameStates = GameStates.MAIN_MENU;
    private static GameStates delayedState;

    public static GameStates getGameState() {
        return (gameStates);
    }

    public static void setGameState(GameStates gameStates) {
        GameStateHandler.gameStates = gameStates;
    }

    public static void triggerDelayedSetGameState(GameStates gameStates) {
        delayedState = gameStates;
    }

    public static void setDelayedState() {
        if (delayedState != null) {
            gameStates = delayedState;
            delayedState = null;
        }
    }

}
