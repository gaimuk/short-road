package com.gaimuk.shortroad.common.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

/**
 * Utility to convert base10 number to base58 string and vice versa
 */
@Component
@Scope(value = "singleton")
public class Base58Util {

    private final String ALPHABETS_STRING;
    private final char[] ALPHABETS;
    private final int BASE;

    private final int[] ALPHABETS_INDEX;

    public Base58Util() {
        ALPHABETS_STRING = "123456789ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnopqrstuvwxyz";
        ALPHABETS = ALPHABETS_STRING.toCharArray();
        BASE = ALPHABETS.length;

        // Translate char array into an array with ASCII code as the index, for faster lookup
        ALPHABETS_INDEX = new int[128];
        for (int i = 0; i < ALPHABETS.length; i++) {
            final int ascii = ALPHABETS[i];
            ALPHABETS_INDEX[ascii] = i;
        }
    }

    public String encode(long input) {
        if (input <= 0) {
            return null;
        }

        StringBuilder result = new StringBuilder();

        while (input > 0) {
            final int remainder = (int) (input % BASE);
            result.insert(0, ALPHABETS[remainder]);

            input = (long) Math.floor(input / BASE);
        }

        return result.toString();
    }

    public Long decode(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }

        long result = 0;
        final int inputLength = input.length();

        for (int pos = 0; pos < inputLength; pos++) {
            final int alphabetIndex = ALPHABETS_INDEX[input.charAt(pos)];
            result += alphabetIndex * Math.pow(BASE, inputLength - pos - 1);
        }

        return result;
    }
}
