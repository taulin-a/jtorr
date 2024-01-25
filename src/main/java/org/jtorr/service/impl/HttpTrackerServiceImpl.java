package org.jtorr.service.impl;

import com.dampcake.bencode.Bencode;
import com.dampcake.bencode.Type;
import org.jtorr.exception.TrackerServiceException;
import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;
import org.jtorr.model.tracker.TrackerURL;
import org.jtorr.service.TrackerService;

import java.io.IOException;
import java.net.ProxySelector;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpTrackerServiceImpl implements TrackerService {
    private final HttpClient httpClient;
    private final Bencode bencode;

    public HttpTrackerServiceImpl() {
        httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();
        bencode = new Bencode();
    }

    @Override
    public TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash) {
        try {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(buildTrackerUri(bencodeData, peerId, infoHash))
                    .GET()
                    .build();

            var bytes = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray()).body();
            var bencodeDict = bencode.decode(bytes, Type.DICTIONARY);

            return null;
        } catch (IOException e) {
            throw new TrackerServiceException("Error getting response from tracker: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new TrackerServiceException("Error connecting to tracker: " + e.getMessage(), e);
        }
    }

    private URI buildTrackerUri(BencodeData bencodeData, String peerId, String infoHash) {
        try {
            var trackerUrl = TrackerURL.builder()
                    .announce(bencodeData.announce())
                    .infoHash(infoHash)
                    .peerId(peerId)
                    .port("")
                    .left(Long.toString(bencodeData.info().pieceLength()))
                    .build();

            return new URI(trackerUrl.toString());
        } catch (URISyntaxException e) {
            throw new TrackerServiceException("Error building tracker URL: " + e.getMessage(), e);
        }
    }
}
