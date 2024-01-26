package org.jtorr.component.generator;

import org.jtorr.exception.InfoHashGeneratorException;
import org.jtorr.model.bencode.BencodeFile;
import org.jtorr.model.bencode.BencodeInfo;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;

public class InfoHashGenerator {
    private static final class BencodeParts {
        public static final String INFO = "4:info";
        public static final String FILES = "d5:filesld";
        public static final String LENGTH = "6:lengthi";
        public static final String PATH = "e4:pathl";
        public static final String FILES_SEPARATOR = "eed";
        public static final String FILES_END = "eee";
        public static final String NAME = "4:name";
        public static final String PIECE_LENGTH = "12:piece lengthi";
        public static final String PIECES = "e6:pieces";
        public static final String FIELD_LENGTH_SUFFIX = ":";
        public static final String END = "ee";
    }

    private static final String HASH_ALGORITHM = "SHA-1";

    private final MessageDigest hashMsgDigest;

    public InfoHashGenerator() {
        try {
            hashMsgDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new InfoHashGeneratorException("Error instantiating InfoHashGenerator: " + e.getMessage(), e);
        }
    }

    public String generate(BencodeInfo info) {
        var infoBencodeBytes = infoToBencodeStr(info).getBytes(StandardCharsets.UTF_8);

        return bencodeBytesToHash(infoBencodeBytes);
    }

    private String infoToBencodeStr(BencodeInfo info) {
        var strBuilder = new StringBuilder();

        strBuilder.append(BencodeParts.INFO);
        strBuilder.append(BencodeParts.FILES);

        String filesDefinitionStr = info.files().stream()
                .map(this::fileToDefinitionStr)
                .collect(Collectors.joining(BencodeParts.FILES_SEPARATOR));

        strBuilder.append(filesDefinitionStr);
        strBuilder.append(BencodeParts.FILES_END);
        strBuilder.append(BencodeParts.NAME);
        strBuilder.append(info.name().length());
        strBuilder.append(BencodeParts.FIELD_LENGTH_SUFFIX);
        strBuilder.append(info.name());
        strBuilder.append(BencodeParts.PIECE_LENGTH);
        strBuilder.append(info.pieceLength());
        strBuilder.append(BencodeParts.PIECES);
        strBuilder.append(info.pieces().length());
        strBuilder.append(BencodeParts.FIELD_LENGTH_SUFFIX);
        strBuilder.append(info.pieces());
        strBuilder.append(BencodeParts.END);

        return strBuilder.toString();
    }

    private String fileToDefinitionStr(BencodeFile file) {
        return BencodeParts.LENGTH
                + file.length()
                + BencodeParts.PATH
                + file.path().length()
                + BencodeParts.FIELD_LENGTH_SUFFIX
                + file.path();
    }

    private String bencodeBytesToHash(byte[] infoBencodeBytes) {
        hashMsgDigest.reset();
        hashMsgDigest.update(infoBencodeBytes);

        byte[] hash = hashMsgDigest.digest();

        var hexStrBuilder = new StringBuilder();
        for (byte b : hash) {
            hexStrBuilder.append(String.format("%02x", b));
        }

        return hexStrBuilder.toString();
    }
}
