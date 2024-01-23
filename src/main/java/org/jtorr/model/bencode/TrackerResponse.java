package org.jtorr.model.bencode;

import java.util.List;

public record TrackerResponse(Long interval, List<String> peers) {
}
