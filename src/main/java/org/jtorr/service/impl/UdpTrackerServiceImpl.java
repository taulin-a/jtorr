package org.jtorr.service.impl;

import org.jtorr.component.generator.TransactionIdGenerator;
import org.jtorr.exception.TrackerServiceException;
import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;
import org.jtorr.model.tracker.TrackerUDPConnect;
import org.jtorr.service.TrackerService;

import java.net.*;

public class UdpTrackerServiceImpl implements TrackerService {
    private final DatagramSocket datagramSocket;
    private final TransactionIdGenerator transactionIdGenerator;

    public UdpTrackerServiceImpl() {
        try {
            datagramSocket = new DatagramSocket();
            transactionIdGenerator = new TransactionIdGenerator();
        } catch (SocketException e) {
            throw new TrackerServiceException("Error instantiating service: " + e.getMessage(), e);
        }
    }

    @Override
    public TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash) {
        try {
            var transactionId = transactionIdGenerator.generate();
            var connectionMsg = TrackerUDPConnect.builder()
                    .transactionId(transactionId)
                    .build()
                    .getBytes();

            var uri = new URI(bencodeData.announce());

            var address = InetAddress.getByName(uri.getHost());

            var packet = new DatagramPacket(connectionMsg, connectionMsg.length, address, uri.getPort());

            datagramSocket.send(packet);
            return null;
        } catch (Exception e) {
            throw new TrackerServiceException("", e);
        }
    }
}
