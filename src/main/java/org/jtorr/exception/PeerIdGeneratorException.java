package org.jtorr.exception;

public class PeerIdGeneratorException extends RuntimeException {
    public PeerIdGeneratorException(String message) {
        this(message, null);
    }

    public PeerIdGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
