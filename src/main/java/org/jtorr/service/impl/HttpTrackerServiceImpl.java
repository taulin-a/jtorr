package org.jtorr.service.impl;

import org.jtorr.exception.TrackerServiceException;
import org.jtorr.model.bencode.BencodeData;
import org.jtorr.model.bencode.TrackerResponse;
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

    public HttpTrackerServiceImpl() {
        httpClient = HttpClient.newBuilder()
                .proxy(ProxySelector.getDefault())
                .build();
    }

    @Override
    public TrackerResponse getPeersInfoFromTracker(BencodeData bencodeData, String peerId, String infoHash) {
        try {
            var httpRequest = HttpRequest.newBuilder()
                    .uri(buildTrackerUri(bencodeData, peerId, infoHash))
                    .GET()
                    .build();

            httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofByteArray());
            return null;
        } catch (IOException e) {
            throw new TrackerServiceException("Error getting response from tracker: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            throw new TrackerServiceException("Error connecting to tracker: " + e.getMessage(), e);
        }
    }

    private URI buildTrackerUri(BencodeData bencodeData, String peerId, String infoHash) {
        try {
            var strBuilder = new StringBuilder();

            return new URI(strBuilder.toString());
        } catch (URISyntaxException e) {
            throw new TrackerServiceException("Error building tracker URL: " + e.getMessage(), e);
        }
    }
}
