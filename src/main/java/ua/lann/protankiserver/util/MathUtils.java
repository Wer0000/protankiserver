package ua.lann.protankiserver.util;

import java.util.Random;

public class MathUtils {
    public static int random(int min, int max) {
        Random rnd = new Random();
        return rnd.nextInt(max - min + 1) + min;
    }
}
