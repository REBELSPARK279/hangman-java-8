package code.net.rebelspark.hangman_client.util;

import java.util.ArrayList;
//My previously made BetterMath class, imported for ease of use.
public class BetterMath {

    public static int randInt(int min, int max) {
        return((int)(Math.random() * (max - min) + min));
    }

    public static double randDouble(double min, double max, int decimals) {

        decimals = (int)Math.pow(10, decimals);
        int temp = (int)(Math.random() * ((max * (decimals)) - (min * (decimals))) + (min * decimals));
        return ((double)temp / decimals);

    }

    public static <T> ArrayList<T> randomizeList(ArrayList<T> in) {
        ArrayList<T> input = new ArrayList<>(in);
        ArrayList<T> output = new ArrayList<>();
        while (!input.isEmpty()) {
            int randIndex = randInt(0, input.size());
            output.add(input.get(randIndex));
            input.remove(randIndex);
            System.out.println(output.toArray());
        }
        return (output);
    }



}
