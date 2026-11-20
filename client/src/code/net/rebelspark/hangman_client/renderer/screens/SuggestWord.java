package code.net.rebelspark.hangman_client.renderer.screens;

import code.net.rebelspark.hangman_client.game_logic.offline.OFFLINE_WORDLIST;
import code.net.rebelspark.hangman_client.init.regestries.Fonts;
import code.net.rebelspark.hangman_client.renderer.features.button.Button;
import code.net.rebelspark.hangman_client.renderer.features.Keyboard;
import code.net.rebelspark.hangman_client.renderer.features.Label;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.HashSet;

public class SuggestWord {

    private static HashSet<String> words = new HashSet<>();

    private static String typed = "";

    public static void writeSuggestions() {
        if (!words.isEmpty()) {
            try {
                Files.createDirectories(CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH);
                String time = LocalDate.now().toString().replace("-", ".") + "-" + LocalTime.now().truncatedTo(ChronoUnit.SECONDS);
                Path path = Paths.get(CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH.toString(), time.replace(":", ".") + "_suggestions.hangman");
                int dupeProt = 0;
                while (Files.exists(path)) {
                    dupeProt++;
                    path = CONSTANTS.USER_PROFILE_SUGGESTED_WORDS_PATH.resolve(time.replace(":", ".") + "_" + dupeProt + "_suggestions.hangman");
                }
                Files.createFile(path);
                StringBuilder output = new StringBuilder();
                for (String word: words) {
                    output.append("\"").append(word).append("\",\n");
                }
                Files.write(path, output.toString().getBytes(StandardCharsets.UTF_8));
                Logger.INFO("Wrote file " + path);
                words.clear();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static final Button BACK = new Button.Properties(GameStates.SUGGEST_WORD, "back", 0.05f, 0.05f, () -> {
        GameStateHandler.triggerDelayedSetGameState(GameStates.MAIN_MENU);
        writeSuggestions();
    }).setText("<--", 65, Color.BLACK, Color.BLUE).build();

    private static final Label LABEL = new Label.Properties(0.5f, 0.15f, "").textSize(70).build();

    private static final Label NOTICE1 = new Label.Properties(0.5f, 0.4f, "You must click the back button to save your wordlist").textSize(50).build();
    private static final Label NOTICE2 = new Label.Properties(0.5f, 0.45f, "This will generate a file in USER/.hangman_rspark/suggested").textSize(50).build();

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
                if (!words.add(typed)) {
                    LABEL.setText("Word already present");
                } else {
                    typed = "";
                    LABEL.setText(typed);
                }
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
        NOTICE1.render(g);
        NOTICE2.render(g);
    }
}
