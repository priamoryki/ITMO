package info.kgeorgiy.ja.lymar.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.stream.IntStream;

/**
 * @author Pavel Lymar
 */
public class HelloUDPNonblockingClient implements HelloClient {
    private void request(SelectionKey key) {
        if (key.isWritable()) {
            try {
                DatagramChannel channel = (DatagramChannel) key.channel();
                DataWrapper data = (DataWrapper) key.attachment();
                channel.write(
                        data.getBuffer().clear().put(
                                Utils.createHelloRequest(
                                        data.getPrefix(), data.getThread(), data.getRequestId()
                                ).getBytes(StandardCharsets.UTF_8)
                        ).flip()
                );
            } catch (IOException e) {
                System.err.println("Exception occurred while sending: " + e.getMessage());
                return;
            }
            key.interestOps(SelectionKey.OP_READ);
        }
    }

    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        SocketAddress address = new InetSocketAddress(host, port);
        try (Selector selector = Selector.open()) {
            IntStream.range(0, threads).forEach(
                    thread -> {
                        try {
                            DatagramChannel.open().connect(address)
                                    .configureBlocking(false)
                                    .register(selector, SelectionKey.OP_WRITE, new DataWrapper(prefix, thread, requests));
                        } catch (IOException e) {
                            System.err.println();
                        }
                    }
            );

            while (!selector.keys().isEmpty()) {
                try {
                    selector.select(Utils.DEFAULT_TIMEOUT);
                } catch (IOException e) {
                    System.err.println("Selector exception occurred: " + e.getMessage());
                }
                if (selector.selectedKeys().isEmpty()) {
                    selector.keys().forEach(this::request);
                    continue;
                }
                Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                while (iter.hasNext()) {
                    SelectionKey key = iter.next();
                    request(key);
                    if (key.isReadable()) {
                        try {
                            DatagramChannel datagramChannel = (DatagramChannel) key.channel();
                            DataWrapper data = (DataWrapper) key.attachment();
                            datagramChannel.receive(data.getBuffer().clear());
                            String response = StandardCharsets.UTF_8.decode(data.getBuffer().flip()).toString();
                            String request = Utils.createHelloRequest(prefix, data.getThread(), data.getRequestId());
                            if (response.contains(request)) {
                                data.nextRequest();
                                System.out.println(response);
                            }
                            if (data.isDone()) {
                                try {
                                    datagramChannel.close();
                                } catch (IOException e) {
                                    System.err.println("Can't close channel: " + e.getMessage());
                                }
                                continue;
                            }
                        } catch (IOException e) {
                            System.err.println("Exception occurred while receiving: " + e.getMessage());
                        }
                        key.interestOps(SelectionKey.OP_WRITE);
                    }
                    iter.remove();
                }
            }
        } catch (IOException e) {
            System.err.println("Exception occurred while initializing: " + e.getMessage());
        }
    }
}
