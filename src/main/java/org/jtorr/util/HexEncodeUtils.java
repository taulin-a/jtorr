package org.jtorr.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public final class HexEncodeUtils {
    private static final char HEX_ENCODED_SEPARATOR = '%';

    public static String encodeHexStr(String hexString) {
        int len = hexString.length();
        char[] output = new char[len + len / 2];
        int i = 0;
        int j = 0;
        while (i < len) {
            output[j++] = HEX_ENCODED_SEPARATOR;
            output[j++] = hexString.charAt(i++);
            output[j++] = hexString.charAt(i++);
        }
        return new String(output);
    }
}
