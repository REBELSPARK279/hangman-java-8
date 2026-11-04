package code.net.rebelspark.hangman_client.util;

import java.time.LocalTime;

public class Logger {
    public static void INFO(Object msg) {
        System.out.println("(" + LocalTime.now() + ") [Info] " + msg);
    }

    public static void DEBUG(Object msg) {
        System.out.println("(" + LocalTime.now() + ") [Debug] " + msg);
    }

    public static void WARN(Object msg) {
        System.out.println("(" + LocalTime.now() + ") [WARN] " + msg);
    }

    public static void CRITICAL(Object msg) {
        System.out.println("(" + LocalTime.now() + ") [CRITICAL] " + msg);
    }

    public static void FATAL(Object msg) {
        System.out.println("(" + LocalTime.now() + ") [FATAL] " + msg);
    }
}
