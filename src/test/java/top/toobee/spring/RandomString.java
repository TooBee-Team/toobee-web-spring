package top.toobee.spring;

import java.security.SecureRandom;

public class RandomString {
    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        SecureRandom rnd = new SecureRandom();

        while (sb.length() < length) {
            // Generate a random number between 0 (inclusive) and 36 (exclusive)
            int randomChar = rnd.nextInt(36);
            // Convert the number to a character
            char c = (char) (randomChar < 26 ? 'a' + randomChar : '0' + randomChar - 26);
            sb.append(c);
        }
        return sb.toString();
    }

}
