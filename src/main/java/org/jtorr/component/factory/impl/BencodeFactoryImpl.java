package org.jtorr.component.factory.impl;

import org.jtorr.component.factory.BencodeFactory;
import org.jtorr.exception.BencodeFactoryException;
import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.BencodeFile;
import org.jtorr.model.bencode.BencodeInfo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class BencodeFactoryImpl implements BencodeFactory {
    private static final class Fields {
        public static final String ANNOUNCE = "announce";
        public static final String ANNOUNCE_LIST = "announce-list";
        public static final String CREATED_BY = "created by";
        public static final String CREATION_DATE = "creation date";
        public static final String INFO = "info";
        public static final String FILES = "files";
        public static final String NAME = "name";
        public static final String PIECE_LENGTH = "piece length";
        public static final String PIECES = "pieces";
        public static final String LENGTH = "length";
        public static final String PATH = "path";
    }

    @Override
    public BencodeData createBencodeData(Map<String, Object> bencodeDict) {
        try {
            List<String> announceList = getAnnounceListFromNestList(
                    (List<List<String>>) bencodeDict.get(Fields.ANNOUNCE_LIST));

            return new BencodeData(
                    (String) bencodeDict.get(Fields.ANNOUNCE),
                    announceList,
                    (String) bencodeDict.get(Fields.CREATED_BY),
                    (Long) bencodeDict.get(Fields.CREATION_DATE),
                    createInfo((Map<String, Object>) bencodeDict.get(Fields.INFO))
            );
        } catch (ClassCastException | NullPointerException e) {
            throw new BencodeFactoryException("Error creating BencodeData object: " + e.getMessage(), e);
        }
    }

    private List<String> getAnnounceListFromNestList(List<List<String>> announceListOfLists) {
        if (Objects.nonNull(announceListOfLists) && !announceListOfLists.isEmpty()) {
            return announceListOfLists.stream()
                    .map(l -> l.get(0))
                    .toList();
        } else {
            return null;
        }
    }

    @Override
    public BencodeInfo createInfo(Map<String, Object> infoDict) {
        try {
            return new BencodeInfo(
                    createFileList((List<Map<String, Object>>) infoDict.get(Fields.FILES)),
                    (String) infoDict.get(Fields.NAME),
                    (Long) infoDict.get(Fields.PIECE_LENGTH),
                    (String) infoDict.get(Fields.PIECES)
            );
        } catch (ClassCastException | NullPointerException e) {
            throw new BencodeFactoryException("Error creating BencodeInfo object: " + e.getMessage(), e);
        }
    }

    @Override
    public List<BencodeFile> createFileList(List<Map<String, Object>> fileDictList) {
        if (Objects.isNull(fileDictList)) {
            return null;
        }

        return fileDictList.stream()
                .map(this::createFile)
                .toList();
    }

    @Override
    public BencodeFile createFile(Map<String, Object> fileDict) {
        try {
            List<String> pathList = (List<String>) fileDict.get(Fields.PATH);
            var path = Objects.nonNull(pathList) && !pathList.isEmpty()
                    ? pathList.get(0)
                    : null;

            return new BencodeFile((Long) fileDict.get(Fields.LENGTH), path);
        } catch (ClassCastException | NullPointerException e) {
            throw new BencodeFactoryException("Error creating BencodeFile object: " + e.getMessage(), e);
        }
    }
}
