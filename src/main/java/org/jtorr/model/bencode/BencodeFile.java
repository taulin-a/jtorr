package org.jtorr.model.bencode;

import java.util.List;

public record BencodeFile(Long length, List<String> path) {
}
