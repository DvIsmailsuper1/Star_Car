package com.example.myapplication;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RandomString {

    private static final int STRING_LENGTH = 6;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%&*";

    public static String generate() {
        StringBuilder sb = new StringBuilder(STRING_LENGTH);
        Random random = new Random();
        Set<String> usedStrings = new HashSet<>();
        String currentString;

        do {
            for (int i = 0; i < STRING_LENGTH; i++) {
                int randomIndex = random.nextInt(CHARACTERS.length());
                char randomChar = CHARACTERS.charAt(randomIndex);
                sb.append(randomChar);
            }
            currentString = sb.toString();
            sb.setLength(0);
        } while (usedStrings.contains(currentString));

        usedStrings.add(currentString);
        return currentString;
    }
}

