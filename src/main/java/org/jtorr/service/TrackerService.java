package org.jtorr.service;

import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;

public interface TrackerService {
    TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash);
}
