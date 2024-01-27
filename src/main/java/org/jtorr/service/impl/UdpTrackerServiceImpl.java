package org.jtorr.service.impl;

import com.dampcake.bencode.Bencode;
import org.jtorr.component.generator.TransactionIdGenerator;
import org.jtorr.exception.TrackerServiceException;
import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;
import org.jtorr.model.tracker.TrackerUDPConnect;
import org.jtorr.service.TrackerService;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;

public class UdpTrackerServiceImpl implements TrackerService {
    private static final int DEFAULT_PORT = 6969;

    private final DatagramSocket datagramSocket;
    private final TransactionIdGenerator transactionIdGenerator;
    private final Bencode bencode;

    public UdpTrackerServiceImpl() {
        try {
            datagramSocket = new DatagramSocket(DEFAULT_PORT);
            transactionIdGenerator = new TransactionIdGenerator();
            bencode = new Bencode();
        } catch (SocketException e) {
            throw new TrackerServiceException("Error instantiating service: " + e.getMessage(), e);
        }
    }

    @Override
    public TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash) {
        var uri = announceToURI(bencodeData.announce());
        createConnectionRequest(uri);

        return null;
    }

    private URI announceToURI(String announce) {
        try {
            return new URI(announce);
        } catch (URISyntaxException e) {
            throw new TrackerServiceException("Error creating URI from announce: " + e.getMessage(), e);
        }
    }

    private void createConnectionRequest(URI uri) {
        try {
            var transactionId = transactionIdGenerator.generate();
            var buff = TrackerUDPConnect.builder()
                    .transactionId(transactionId)
                    .build()
                    .getBytes();

            var address = InetAddress.getByName(uri.getHost());
            var packet = new DatagramPacket(buff, buff.length, address, uri.getPort());
            datagramSocket.send(packet);

            packet = new DatagramPacket(buff, buff.length);
            datagramSocket.receive(packet);

            var response = new String(packet.getData(), 0, packet.getLength());
            System.out.println(response);
        } catch (UnknownHostException e) {
            throw new TrackerServiceException("Error connecting to host: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new TrackerServiceException("Error creating connection with tracker: " + e.getMessage(), e);
        }
    }
}
