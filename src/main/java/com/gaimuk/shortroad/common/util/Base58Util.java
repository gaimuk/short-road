package com.gaimuk.shortroad.common.util;

import org.springframework.stereotype.Component;

/**
 * Utility to convert base10 number to base58 string and vice versa
 */
@Component
public class Base58Util {

    private static final String ALPHABETS_STRING = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
    private static final char[] ALPHABETS = ALPHABETS_STRING.toCharArray();
    private static final int BASE = ALPHABETS.length;

    private static final int[] ALPHABETS_INDEX = new int[128];

    static {
        // Translate char array into an array with ASCII code as the index, for faster lookup
        for (int i = 0; i < ALPHABETS.length; i++) {
            final int ascii = ALPHABETS[i];
            ALPHABETS_INDEX[ascii] = i;
        }
    }

    public static String encode(long input) {
        StringBuilder result = new StringBuilder();

        while (input > 0) {
            final int remainder = (int) (input % BASE);
            result.insert(0, ALPHABETS[remainder]);

            input = (long) Math.floor(input / BASE);
        }

        return result.toString();
    }

    public static Long decode(String input) {
        long result = 0;
        final int inputLength = input.length();

        for (int pos = 0; pos < inputLength; pos++) {
            final int alphabetIndex = ALPHABETS_INDEX[input.charAt(pos)];
            result += alphabetIndex * Math.pow(BASE, inputLength - pos - 1);
        }

        return result;
    }
}
