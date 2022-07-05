package info.kgeorgiy.ja.lymar.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Pavel Lymar
 */
public class HelloUDPServer implements HelloServer {
    private DatagramSocket socket;
    private ExecutorService senders, receivers;

    @Override
    public void start(int port, int threads) {
        try {
            this.socket = new DatagramSocket(port);
            this.senders = Executors.newFixedThreadPool(threads);
            this.receivers = Executors.newSingleThreadExecutor();
        } catch (SocketException e) {
            System.err.println("Broken socket: " + e.getMessage());
            return;
        }

        receivers.submit(() -> {
            while (!socket.isClosed()) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[socket.getReceiveBufferSize()], socket.getReceiveBufferSize());
                    socket.receive(request);
                    senders.submit(() -> {
                        String helloString = "Hello, " +
                                new String(request.getData(), request.getOffset(), request.getLength());
                        try {
                            socket.send(
                                    new DatagramPacket(
                                            helloString.getBytes(),
                                            helloString.length(),
                                            request.getSocketAddress()
                                    )
                            );
                        } catch (IOException e) {
                            System.err.println("Exception occurred while sending socket: " + e.getMessage());
                        }
                    });
                } catch (IOException e) {
                    System.err.println("Exception occurred while receiving socket: " + e.getMessage());
                }
            }
        });
    }

    @Override
    public void close() {
        socket.close();
        Utils.close(senders);
        Utils.close(receivers);
    }
}
