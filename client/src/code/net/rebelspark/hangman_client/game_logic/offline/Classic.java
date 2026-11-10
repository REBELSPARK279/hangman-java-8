package code.net.rebelspark.hangman_client.game_logic.offline;

import code.net.rebelspark.hangman_client.game_logic.client.KeyboardKeyStates;
import code.net.rebelspark.hangman_client.renderer.screens.in_game.BaseGameScreen;
import code.net.rebelspark.hangman_client.renderer.overlay.in_game.EndOverlay;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.BetterMath;
import code.net.rebelspark.hangman_client.util.Logger;

import java.util.ArrayList;

public class Classic {

    private static final byte STARTING_LIVES = 6;
    private static String currentWord;
    private static byte lives;
    private static final KeyboardKeyStates[] keyStates = new KeyboardKeyStates[26];
    private static final ArrayList<Character> guessedLetters = new ArrayList<>();
    private static final ArrayList<Character> correctLetters = new ArrayList<>();

    public static void startGame() {
        EndOverlay.triggerDelayedEndScreenOverlay(false);
        currentWord = OFFLINE_WORDLIST.OFFINE_WORDLIST[BetterMath.randInt(0, OFFLINE_WORDLIST.OFFINE_WORDLIST.length)];
        lives = STARTING_LIVES;
        BaseGameScreen.setLivesLabel(lives + " Lives");
        BaseGameScreen.GameSlot.resetNextIndex();
        BaseGameScreen.resetGameSlotIndexes();
        for (byte i = 0; i < 26; i++) {
            keyStates[i] = KeyboardKeyStates.UNGUESSED;
        }
        guessedLetters.clear();
        correctLetters.clear();
        for (byte i = 0; i < currentWord.length(); i++) {
            correctLetters.add('_');
        }
        for (BaseGameScreen.GameSlot gameSlot: BaseGameScreen.gameSlots) {
            gameSlot.setLetter('-');
        }
        Button.enableInputsTo(GameStates.IN_GAME_BASE);
        Button.disableInputsTo(GameStates.END_SCREEN);
    }

    public static ArrayList<Character> getGuessedLetters() {
        return (new ArrayList<>(guessedLetters));
    }

    public static ArrayList<Character> getCorrectLetters() {
        return (new ArrayList<>(correctLetters));
    }

    public static byte getWordLength() {
        return ((byte)currentWord.length());
    }

    public static byte getLives() {
        return (lives);
    }

    public static void receiveInput(final Keyboard.KEYS keys) {
        if (keys.equals(Keyboard.KEYS.UNKNOWN)) {
            Logger.DEBUG(Keyboard.KEYS.UNKNOWN);
            return;
        }
        if (EndOverlay.getState()) {
            return;
        }
        final char key = keys.toString().toLowerCase().charAt(0);
        if (guessedLetters.contains(key)) {
            return;
        }
        guessedLetters.add(key);
        final ArrayList<Byte> indexCorrect = new ArrayList<>();
        byte lastIndex = 0;
        for (byte i = 0; i < 12; i++) {
            final byte hit = (byte)currentWord.indexOf(key, lastIndex);
            if (hit == -1) {
                break;
            }
            indexCorrect.add(hit);
            lastIndex = (byte)(hit + 1);
        }
        if (indexCorrect.isEmpty()) {
            lives--;
            BaseGameScreen.setLivesLabel(lives + " Lives");
            if (lives == 0) {
                Logger.DEBUG("you lose :(");
                Logger.DEBUG(currentWord);
                //loss
                EndOverlay.triggerDelayedEndScreenOverlay(true);
                Button.enableInputsTo(GameStates.END_SCREEN);
                Button.disableInputsTo(GameStates.IN_GAME_BASE);
                EndOverlay.setEndScreenTitleStatus(false, currentWord);
            }
        } else {
            for (byte index: indexCorrect) {
                correctLetters.set(index, key);
                for (BaseGameScreen.GameSlot gameSlot : BaseGameScreen.gameSlots) {
                    if (gameSlot.getIndex() == index) {
                        gameSlot.setLetter(key);
                    }
                }
            }
        }
        if (!correctLetters.contains('_')) {
            Logger.DEBUG("you win!");
            //win
            EndOverlay.triggerDelayedEndScreenOverlay(true);
            Button.enableInputsTo(GameStates.END_SCREEN);
            Button.disableInputsTo(GameStates.IN_GAME_BASE);
            EndOverlay.setEndScreenTitleStatus(true, currentWord);
        }
        Logger.DEBUG(correctLetters);
        Logger.DEBUG(lives);
    }



}
