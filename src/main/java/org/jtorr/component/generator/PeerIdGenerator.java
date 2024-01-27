package org.jtorr.component.generator;

import org.jtorr.exception.PeerIdGeneratorException;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class PeerIdGenerator extends HexGenerator {
    private static final int PEER_ID_SIZE = 20;

    public String generate(String pieces) {
        var bytes = pieces.getBytes();
        if (bytes.length < PEER_ID_SIZE) {
            throw new PeerIdGeneratorException("Invalid pieces field in Bencode");
        }

        return new String(Arrays.copyOfRange(bytes, 0, PEER_ID_SIZE), StandardCharsets.UTF_8);
    }
}
