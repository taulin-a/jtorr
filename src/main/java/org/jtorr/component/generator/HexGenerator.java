package org.jtorr.component.generator;

public abstract class HexGenerator {
    private static final String BYTE_HEX_FORMAT = "%02x";

    protected String bytesToHexStr(byte[] bytes) {
        var hexStrBuilder = new StringBuilder();
        for (byte b : bytes) {
            hexStrBuilder.append(String.format(BYTE_HEX_FORMAT, b));
        }

        return hexStrBuilder.toString();
    }
}
