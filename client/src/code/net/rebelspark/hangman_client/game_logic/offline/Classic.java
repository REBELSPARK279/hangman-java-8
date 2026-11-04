package code.net.rebelspark.hangman_client.game_logic.offline;

import code.net.rebelspark.hangman_client.game_logic.client.KeyboardKeyStates;
import code.net.rebelspark.hangman_client.renderer.GamePanel;
import code.net.rebelspark.hangman_client.renderer.screens.in_game.BaseGameScreen;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;
import code.net.rebelspark.hangman_client.util.BetterMath;

import java.util.ArrayList;

public class Classic {

    private static final byte STARTING_LIVES = 5;

    private static String currentWord;
    private static byte lives;
    private static final KeyboardKeyStates[] keyStates = new KeyboardKeyStates[26];
    private static final ArrayList<Character> guessedLetters = new ArrayList<>();
    private static final ArrayList<Character> correctLetters = new ArrayList<>();

    public static void init() {
        currentWord = OFFLINE_WORDLIST.OFFINE_WORDLIST[BetterMath.randInt(0, OFFLINE_WORDLIST.OFFINE_WORDLIST.length)];
        lives = STARTING_LIVES;
        for (byte i = 0; i < 26; i++) {
            keyStates[i] = KeyboardKeyStates.UNGUESSED;
        }
        guessedLetters.clear();
        correctLetters.clear();
        for (byte i = 0; i < currentWord.length(); i++) {
            correctLetters.add('_');
        }
    }

    public static void receiveInput(final Keyboard.KEYS keys) {
        final char key = keys.toString().charAt(0);
        final ArrayList<Integer> indexCorrect = new ArrayList<>();
        int lastIndex = 0;
        for (byte i = 0; i < 12; i++) {
            final int hit = currentWord.indexOf(key, lastIndex);
            if (hit == -1) {
                break;
            }
            indexCorrect.add(hit);
        }
        if (indexCorrect.isEmpty()) {
            lives--;
            if (lives == 0) {
                //handleGameover();
            }
        } else {
            for (int index: indexCorrect) {
                correctLetters.set(index, key);
            }
        }
        pushRender();
    }

    public static void pushRender() {
        BaseGameScreen.renderGame((byte)currentWord.length(), lives);
    }

}
