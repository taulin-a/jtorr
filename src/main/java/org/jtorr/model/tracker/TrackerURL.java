package org.jtorr.model.tracker;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Builder
public class TrackerURL {
    private final String announce;
    private final String infoHash;
    private final String peerId;
    private final String port;
    private final String uploaded = "0";
    private final String downloaded = "0";
    private final String compact = "1";
    private final String left;

    @Override
    public String toString() {
        return null;
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
