package org.jtorr.model.tracker;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@AllArgsConstructor
@Builder
public class TrackerURL {
    private static final class UrlParts {
        public static final Character START_CHAR = '?';
        public static final String QUERY_PARAMS_START = "?";
        public static final String QUERY_PARAMS_SEPARATOR = "&";
        public static final String INFO_HASH_PARAM = "info_hash=%s";
        public static final String PEER_ID_PARAM = "peer_id=%s";
        public static final String PORT_PARAM = "port=%s";
        public static final String UPLOADED = "uploaded=%s";
        public static final String DOWNLOADED = "downloaded=%s";
        public static final String COMPACT = "compact=%s";
        public static final String LEFT = "left=%s";
    }

    private String announce;
    private String infoHash;
    private String peerId;
    private String port;
    @Builder.Default
    private String uploaded = "0";
    @Builder.Default
    private String downloaded = "0";
    @Builder.Default
    private String compact = "1";
    private String left;

    @Override
    public String toString() {
        var strBuilder = new StringBuilder(announce);
        strBuilder.append(UrlParts.QUERY_PARAMS_START);

        addParam(strBuilder, UrlParts.INFO_HASH_PARAM, encodeValue(infoHash));
        addParam(strBuilder, UrlParts.PEER_ID_PARAM, encodeValue(peerId));
        addParam(strBuilder, UrlParts.PORT_PARAM, encodeValue(port));
        addParam(strBuilder, UrlParts.UPLOADED, encodeValue(uploaded));
        addParam(strBuilder, UrlParts.DOWNLOADED, encodeValue(downloaded));
        addParam(strBuilder, UrlParts.COMPACT, encodeValue(compact));
        addParam(strBuilder, UrlParts.LEFT, encodeValue(left));

        return strBuilder.toString();
    }

    private void addParam(StringBuilder strBuilder, String template, String param) {
        if (Objects.nonNull(param)) {
            if (strBuilder.charAt(strBuilder.length() - 1) != UrlParts.START_CHAR) {
                strBuilder.append(UrlParts.QUERY_PARAMS_SEPARATOR);
            }

            strBuilder.append(String.format(template, param));
        }
    }

    private String encodeValue(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }
}
