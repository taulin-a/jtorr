package org.jtorr.component.encode;

import org.jtorr.exception.PeerByteBlobDecoderException;
import org.jtorr.model.bencode.Peer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PeerByteBlobDecoder {
    private static final int PEER_BYTES_SIZE = 6;
    private static final int IP_BYTES_LENGTH = 4;

    public List<Peer> decode(String bytesStr) {
        var bytes = peersBytesStrToBytes(bytesStr);

        try {
            var numOfPeers = bytes.length / PEER_BYTES_SIZE;
            int offset = 0;
            List<Peer> peerList = new ArrayList<>();
            while (offset < bytes.length) {
                var ipEndOffset = offset + IP_BYTES_LENGTH;

                var address = bytesToAddress(bytes, offset, ipEndOffset);
                var port = bytesToPortNumber(bytes, ipEndOffset, ipEndOffset + 1);

                peerList.add(new Peer(address, port));

                offset += numOfPeers;
            }

            return peerList;
        } catch (UnknownHostException e) {
            throw new PeerByteBlobDecoderException("Failed to decode IP address from peer bytes: " + e.getMessage(), e);
        } catch (Exception e) {
            throw new PeerByteBlobDecoderException("Error when decoding peers byte blob: " + e.getMessage(), e);
        }
    }

    private byte[] peersBytesStrToBytes(String bytesStr) {
        byte[] bytes = bytesStr.getBytes();
        if (bytes.length % PEER_BYTES_SIZE != 0) {
            throw new PeerByteBlobDecoderException("Invalid or malformed peers byte blob");
        }

        return bytes;
    }

    private InetAddress bytesToAddress(byte[] bytes, int origin, int limit) throws UnknownHostException {
        var ipBytes = Arrays.copyOfRange(bytes, origin, limit);
        return InetAddress.getByAddress(ipBytes);
    }

    private int bytesToPortNumber(byte[] bytes, int firstByteIndex, int secondByteIndex) {
        return ((bytes[firstByteIndex] & 0xFF) << 8) | (bytes[secondByteIndex] & 0xFF);
    }
}
