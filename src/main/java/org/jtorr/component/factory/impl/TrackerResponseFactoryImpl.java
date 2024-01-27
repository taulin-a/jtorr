package org.jtorr.component.factory.impl;

import org.jtorr.component.encode.PeerByteBlobDecoder;
import org.jtorr.component.factory.TrackerResponseFactory;
import org.jtorr.exception.TrackerResponseFactoryException;
import org.jtorr.model.bencode.Peer;
import org.jtorr.model.bencode.TrackerResponse;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TrackerResponseFactoryImpl implements TrackerResponseFactory {
    private static final class Fields {
        public static final String INTERVAL = "interval";
        public static final String PEERS = "peers";
        public static final String IP = "ip";
        public static final String PORT = "port";
    }

    private final PeerByteBlobDecoder peerByteBlobDecoder;

    public TrackerResponseFactoryImpl() {
        this.peerByteBlobDecoder = new PeerByteBlobDecoder();
    }

    public TrackerResponse createTrackerResponse(Map<String, Object> trackerRespDict) {
        return new TrackerResponse((Long) trackerRespDict.get(Fields.INTERVAL),
                createPeerList(trackerRespDict.get(Fields.PEERS)));
    }

    @SuppressWarnings("unchecked")
    public List<Peer> createPeerList(Object peers) {
        if (Objects.isNull(peers)) {
            return null;
        } else if (peers instanceof String byteBlobStr) {
            return peerByteBlobDecoder.decode(byteBlobStr);
        } else {
            return ((List<Map<String, Object>>) peers).stream()
                    .map(this::createPeer)
                    .toList();
        }
    }

    public Peer createPeer(Map<String, Object> peersDict) {
        try {
            // TODO: handle other formats for IP field
            var address = (String) peersDict.get(Fields.IP); // IP can actually be IPv6 (hexed) or IPv4 (dotted quad) or DNS name (string)

            return new Peer(InetAddress.getByName(address), (Integer) peersDict.get(Fields.PORT));
        } catch (UnknownHostException e) {
            throw new TrackerResponseFactoryException("Failed to get IP address from peers dictionary: "
                    + e.getMessage(), e);
        }
    }
}
