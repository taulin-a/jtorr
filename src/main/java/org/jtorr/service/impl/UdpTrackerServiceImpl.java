package org.jtorr.service.impl;

import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;
import org.jtorr.service.TrackerService;

public class UdpTrackerServiceImpl implements TrackerService {
    @Override
    public TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash) {
        return null;
    }
}
