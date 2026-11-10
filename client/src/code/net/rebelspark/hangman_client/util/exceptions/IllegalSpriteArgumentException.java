package code.net.rebelspark.hangman_client.util.exceptions;

public class IllegalSpriteArgumentException extends RuntimeException {
    public IllegalSpriteArgumentException(String message) {
        super(message);
        printStackTrace();
        System.exit(-1);
    }
}
