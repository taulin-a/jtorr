package org.jtorr;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.jtorr.component.generator.InfoHashGenerator;
import org.jtorr.component.factory.impl.BencodeFactoryImpl;
import org.jtorr.component.generator.PeerIdGenerator;
import org.jtorr.service.impl.HttpTrackerServiceImpl;
import org.jtorr.service.impl.UdpTrackerServiceImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        try {
            var torrFileByteArr = Files.readAllBytes(Path.of("/home/paulo_alves/Downloads/movie.torrent"));
            var bencode = new Bencode();
            var bencodeData = new BencodeFactoryImpl().createBencodeData(
                    bencode.decode(torrFileByteArr, Type.DICTIONARY));

            var infoHash = new InfoHashGenerator().generate(bencodeData.info());

            var peerId = new PeerIdGenerator().generate(bencodeData.info().pieces());

            var trackerService = new HttpTrackerServiceImpl();
            trackerService.getPeersInfoFromTracker(bencodeData, peerId, infoHash);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}