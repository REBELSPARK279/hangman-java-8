package code.net.rebelspark.hangman_client.util;

import java.util.ArrayDeque;

public class FPSCalc {
    private static final ArrayDeque<Long> frameTimes = new ArrayDeque<>();
    public static void tickFPS() {
        long current = System.currentTimeMillis();
        frameTimes.add(current);
        while (frameTimes.getFirst() + 1000 < current) {
            frameTimes.removeFirst();
        }
    }
    public static int getFPS() {
        return (frameTimes.size());
    }
}
