package org.jtorr.model.bencode;

import java.util.List;

public record BencodeInfo(List<BencodeFile> files, String name, Long pieceLength, String pieces) {
}
