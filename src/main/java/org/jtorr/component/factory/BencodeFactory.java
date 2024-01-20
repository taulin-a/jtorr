package org.jtorr.component.factory;

import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.BencodeFile;
import org.jtorr.model.bencode.BencodeInfo;

import java.util.List;
import java.util.Map;

public interface BencodeFactory {
    BencodeData createBencodeData(Map<String, Object> bencodeDict);

    BencodeInfo createInfo(Map<String, Object> infoDict);

    List<BencodeFile> createFileList(List<Map<String, Object>> fileDictList);

    BencodeFile createFile(Map<String, Object> fileDict);
}
