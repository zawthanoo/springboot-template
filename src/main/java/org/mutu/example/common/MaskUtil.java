package org.mutu.example.common;

public class MaskUtil {
    /**
     * Mask a token by showing the first N characters and replacing the rest with a fixed number of '*'.
     * Example: abcd***** (no matter how long the token is)
     */
    public static String maskToken(String token, int visibleLength, int maskLength) {
        if (token == null || token.isEmpty()) {
            return token;
        }

        if (token.length() <= visibleLength) {
            return token;
        }

        String visiblePart = token.substring(0, visibleLength);
        String maskedPart = "*".repeat(maskLength);
        return visiblePart + maskedPart;
    }

    // Default: show first 4 characters, mask with 5 asterisks
    public static String maskToken(String token) {
        return maskToken(token, 4, 5);
    }
}

