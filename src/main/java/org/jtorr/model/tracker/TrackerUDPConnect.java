package org.jtorr.model.tracker;

import lombok.AllArgsConstructor;
import lombok.Builder;

import java.nio.ByteBuffer;

@AllArgsConstructor
@Builder
public class TrackerUDPConnect implements UDPMessage {
    private static final int DEFAULT_MSG_SIZE = 16;

    @Builder.Default
    private long connectionId = 123456789L;
    @Builder.Default
    private int action = 0x0;
    private int transactionId;

    @Override
    public byte[] getBytes() {
        var byteBuffer = ByteBuffer.allocate(DEFAULT_MSG_SIZE);

        byteBuffer.putLong(connectionId);
        byteBuffer.putInt(action);
        byteBuffer.putInt(transactionId);

        return byteBuffer.array();
    }
}
