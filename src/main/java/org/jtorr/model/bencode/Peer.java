package org.jtorr.model.bencode;

import java.net.InetAddress;

public record Peer(InetAddress address, Integer port) {
}
