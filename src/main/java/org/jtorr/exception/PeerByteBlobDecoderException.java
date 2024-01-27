package org.jtorr.exception;

public class PeerByteBlobDecoderException extends RuntimeException {
    public PeerByteBlobDecoderException(String message) {
        this(message, null);
    }

    public PeerByteBlobDecoderException(String message, Throwable cause) {
        super(message, cause);
    }
}
