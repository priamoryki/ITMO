package info.kgeorgiy.ja.lymar.hello;

import java.nio.ByteBuffer;

/**
 * @author Pavel Lymar
 */
public class DataWrapper {
    private final String prefix;
    private final int thread, requests;
    private final ByteBuffer buffer;
    private int requestId;

    public DataWrapper(String prefix, int thread, int requests) {
        this.prefix = prefix;
        this.thread = thread;
        this.requests = requests;
        this.requestId = 0;
        this.buffer = ByteBuffer.allocate(Utils.DEFAULT_CAPACITY);
    }

    public String getPrefix() {
        return prefix;
    }

    public int getThread() {
        return thread;
    }

    public int getRequestId() {
        return requestId;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }

    public boolean isDone() {
        return requestId >= requests;
    }

    public void nextRequest() {
        requestId++;
    }
}
