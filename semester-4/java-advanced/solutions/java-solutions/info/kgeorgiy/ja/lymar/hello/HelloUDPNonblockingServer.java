package info.kgeorgiy.ja.lymar.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pavel Lymar
 */
public class HelloUDPNonblockingServer implements HelloServer {
    private ExecutorService workers;
    private DatagramChannel channel;
    private Selector selector;

    @Override
    public void start(int port, int threads) {
        try {
            this.workers = Executors.newSingleThreadExecutor();
            this.channel = DatagramChannel.open();
            this.selector = Selector.open();
            this.channel.bind(new InetSocketAddress(port))
                    .configureBlocking(false)
                    .register(selector, SelectionKey.OP_READ, new DataWrapper("", 0, 0));
        } catch (IOException e) {
            System.err.println("Exception occurred while initializing: " + e.getMessage());
            return;
        }
        workers.submit(() -> {
            while (channel.isOpen()) {
                try {
                    selector.select(Utils.DEFAULT_TIMEOUT);
                } catch (IOException e) {
                    System.err.println("Selector exception occurred: " + e.getMessage());
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    if (key.isReadable()) {
                        try {
                            DataWrapper data = (DataWrapper) key.attachment();
                            SocketAddress socketAddress = channel.receive(data.getBuffer().clear());
                            String helloString = "Hello, " + StandardCharsets.UTF_8.decode(data.getBuffer().flip());
                            try {
                                channel.send(ByteBuffer.wrap(helloString.getBytes(StandardCharsets.UTF_8)), socketAddress);
                            } catch (IOException e) {
                                System.err.println("Exception occurred while sending: " + e.getMessage());
                            }
                        } catch (IOException e) {
                            System.err.println("Exception occurred while receiving: " + e.getMessage());
                        }
                    }
                    iter.remove();
                }
            }
        });
    }

    @Override
    public void close() {
        try {
            channel.close();
            selector.close();
        } catch (IOException e) {
            System.err.println("Can't close channel or selector: " + e.getMessage());
        }
        Utils.close(workers);
    }
}
