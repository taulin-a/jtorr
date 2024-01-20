package org.jtorr.model.bencode;

import java.util.List;

public record BencodeData(String announce, List<String> announceList, String createdBy, Long creationDate,
                          BencodeInfo info) {
}
