package code.net.rebelspark.hangman_client.util.exceptions;

import code.net.rebelspark.hangman_client.util.Logger;

public class WordListException extends RuntimeException {
    public WordListException(String message) {
        super(message);
        Logger.WARN("Error in word list");
    }
}
