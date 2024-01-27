package org.jtorr.component.factory;

import org.jtorr.model.bencode.Peer;
import org.jtorr.model.bencode.TrackerResponse;

import java.util.List;
import java.util.Map;

public interface TrackerResponseFactory {
    TrackerResponse createTrackerResponse(Map<String, Object> trackerRespDict);

    List<Peer> createPeerList(Object peers);

    Peer createPeer(Map<String, Object> peersDict);
}
