package jessica.utils;

import java.util.Random;

public class RandomUtils {
	public static int randomInt(int startInclusive, int endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0) {
        	int temp = startInclusive;
        	startInclusive = endInclusive;
        	endInclusive = temp;
        }
        return startInclusive + RandomUtils.getRandom().nextInt(endInclusive - startInclusive);
    }

    public static double randomDouble(double startInclusive, double endInclusive) {
        if (startInclusive == endInclusive || endInclusive - startInclusive <= 0.0) {
        	double temp = startInclusive;
        	startInclusive = endInclusive;
        	endInclusive = temp;
        }
        return startInclusive + (endInclusive - startInclusive) * Math.random();
    }

    public static float randomFloat(float f2, float f3) {
        if (f2 == f3 || f3 - f2 <= 0.0f) {
            return f2;
        }
        return (float)((double)f2 + (double)(f3 - f2) * Math.random());
    }

    public static long randomLong(int n2, int n3) {
        return (long)(Math.random() * (double)(1000 / n2 - 1000 / n3 + 1) + (double)(1000 / n3));
    }

    public static String randomStringNumber(int length) {
        return RandomUtils.randomStringFromSourceString(length, "0123456789");
    }

    public static String randomString(int length) {
        return RandomUtils.randomStringFromSourceString(length, "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
    }

    public static String randomStringFromSourceString(int length, String chars) {
        return RandomUtils.randomStringFromChars(length, chars.toCharArray());
    }

    public static String randomStringFromChars(int length, char[] chars) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; ++i) {
            stringBuilder.append(chars[RandomUtils.getRandom().nextInt(chars.length)]);
        }
        return stringBuilder.toString();
    }

    public static Random getRandom() {
        return new Random();
    }
}
