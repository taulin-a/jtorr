package org.jtorr.component.generator;

import org.jtorr.exception.PeerIdGeneratorException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

public class PeerIdGenerator {
    private static final String HASH_ALGORITHM = "SHA-3";
    private static final int PEER_ID_SIZE = 20;

    private final MessageDigest hashMsgDigest;

    public PeerIdGenerator() {
        try {
            hashMsgDigest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new PeerIdGeneratorException("Error instantiating PeerIdGenerator: " + e.getMessage(), e);
        }
    }

    public String generate(String pieces) {
        if (Objects.isNull(pieces) || pieces.length() < PEER_ID_SIZE) {
            throw new PeerIdGeneratorException("Invalid pieces field in Bencode");
        }

        String piecesSlice = pieces.substring(0, PEER_ID_SIZE);

        hashMsgDigest.reset();
        hashMsgDigest.update(piecesSlice.getBytes(StandardCharsets.UTF_8));

        return new String(hashMsgDigest.digest());
    }
}
