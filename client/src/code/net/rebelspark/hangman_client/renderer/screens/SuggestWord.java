package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.OFFLINE_WORDLIST;
import code.net.rebelspark.hangman_client.renderer.util.Button;
import code.net.rebelspark.hangman_client.renderer.util.Keyboard;
import code.net.rebelspark.hangman_client.renderer.util.Label;
import code.net.rebelspark.hangman_client.state.GameStateHandler;
import code.net.rebelspark.hangman_client.state.GameStates;
import code.net.rebelspark.hangman_client.util.CONSTANTS;
import code.net.rebelspark.hangman_client.util.Logger;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

public class SuggestWord {

    private static HashSet<String> words = new HashSet<>();

    private static String typed = "";

    private static final Button BACK = new Button.Properties(GameStates.SUGGEST_WORD, "back", 0.05f, 0.05f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU);
        if (!words.isEmpty()) {
            try {
                Files.createDirectories(CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH);
                String time = "" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
                Path path = Paths.get(CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH.toString(), time.replace(":", ".") + "_suggestions.hangman");
                int dupeProt = 0;
                while (Files.exists(path)) {
                    dupeProt++;
                    path = CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH.resolve(time.replace(":", ".") + "_" + dupeProt + "_suggestions.hangman");
                }
                Files.createFile(path);
                StringBuilder output = new StringBuilder();
                for (String word: words) {
                    output.append("\"").append(word).append("\",");
                }
                Files.write(path, output.toString().getBytes(StandardCharsets.UTF_8));
                words.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }).setText("<--", 65).setTextColor(Color.BLACK, Color.BLUE).build();

    private static final Label LABEL = new Label(0.5f, 0.15f, "", 70);

    private static final Keyboard BOARD = new Keyboard(GameStates.SUGGEST_WORD, 0.25f, 0.5f, SuggestWord::receiveInput);

    public static void receiveInput(final Keyboard.KEYS keys) {
        if (keys.equals(Keyboard.KEYS.UNKNOWN)) {
            Logger.DEBUG(Keyboard.KEYS.UNKNOWN);
            return;
        }
        final String key = keys.toString().toLowerCase().substring(0, 1);
        if (typed.length() < 12) {
            typed += key;
            LABEL.setText(typed);
        }
    }

    public static void enter() {
        if (Arrays.stream(OFFLINE_WORDLIST.OFFINE_WORDLIST).noneMatch(typed::equals)) {
            if (typed.length() >= 5 && typed.length() <= 12) {
                words.add(typed);
                typed = "";
                LABEL.setText(typed);
            } else {
                LABEL.setText("word not between 5 and 12 letters");
            }
        } else {
            LABEL.setText("Word already present");
        }
    }

    public static void backspace() {
        if (!typed.isEmpty()) {
            typed = typed.substring(0, typed.length() - 1);
            LABEL.setText(typed);
        }
    }

    public static void render(Graphics g) {
        BOARD.render(g);
        BACK.render(g);
        LABEL.render(g);
    }
}
