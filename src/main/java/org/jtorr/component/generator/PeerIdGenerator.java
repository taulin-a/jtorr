package org.jtorr.component.generator;

import java.time.LocalDateTime;

public class PeerIdGenerator extends HexGenerator {
    private static final String ID_PREFIX = "-JTORR2024-";

    public String generate() {
        return ID_PREFIX.concat(Long.toString(LocalDateTime.now().getNano()));
    }
}
