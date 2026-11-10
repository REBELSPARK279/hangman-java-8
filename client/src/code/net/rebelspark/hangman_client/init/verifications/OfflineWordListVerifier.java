package code.net.rebelspark.hangman_client.init.verifications;

import code.net.rebelspark.hangman_client.game_logic.offline.OFFLINE_WORDLIST;
import code.net.rebelspark.hangman_client.util.exceptions.WordListException;

import java.util.Arrays;
import java.util.HashSet;

public class OfflineWordListVerifier {

    public static void verifyOfflineWordList() {
        HashSet<String> list = new HashSet<>();
        HashSet<String> dupeList = new HashSet<>();
        HashSet<String> tooLongList = new HashSet<>();
        HashSet<String> tooShortList = new HashSet<>();
        HashSet<String> badCasingList = new HashSet<>();
        for (String str: OFFLINE_WORDLIST.OFFINE_WORDLIST) {
            if (!list.add(str)) {
                dupeList.add(str);
            }
            if (str.length() > 12) {
                tooLongList.add(str);
            }
            if (str.length() < 5) {
                tooShortList.add(str);
            }
            if (!str.equals(str.toLowerCase())) {
                badCasingList.add(str);
            }
        }
        if (!dupeList.isEmpty() || !tooLongList.isEmpty() || !tooShortList.isEmpty() || !badCasingList.isEmpty()) {
            throw new WordListException("Wordlist malformed!\n" +
                    "Dupes [" + dupeList.size() + "]" + (dupeList.isEmpty()? "": ": \"" + Arrays.toString(dupeList.toArray()) + "\"\n") +
                    "Too long words [" + tooLongList.size() + "]" + (tooLongList.isEmpty()? "": ": \"" + Arrays.toString(tooLongList.toArray()) + "\"\n") +
                    "Too short words [" + tooShortList.size() + "]" + (tooShortList.isEmpty()? "": ": \"" + Arrays.toString(tooShortList.toArray()) + "\"\n") +
                    "Bad cased [" + badCasingList.size() + "]" + (badCasingList.isEmpty()? "": ": \"" + Arrays.toString(badCasingList.toArray()) + "\"\n")
            );
        }
    }
}
