package info.kgeorgiy.ja.lymar.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * @author Pavel Lymar
 */
public class HelloUDPClient implements HelloClient {
    @Override
    public void run(String host, int port, String prefix, int threads, int requests) {
        SocketAddress socketAddress = new InetSocketAddress(host, port);
        ExecutorService receivers = Executors.newFixedThreadPool(threads);

        IntStream.range(0, threads).forEach(thread -> receivers.submit(() -> {
            try (DatagramSocket socket = new DatagramSocket()) {
                socket.setSoTimeout(Utils.DEFAULT_TIMEOUT);
                DatagramPacket response = new DatagramPacket(new byte[socket.getReceiveBufferSize()], socket.getReceiveBufferSize());
                IntStream.range(0, requests).forEach(requestId -> {
                    String request = Utils.createHelloRequest(prefix, thread, requestId);
                    String result;
                    do {
                        try {
                            socket.send(new DatagramPacket(request.getBytes(), request.length(), socketAddress));
                            socket.receive(response);
                        } catch (IOException e) {
                            System.err.println("Exception while sending request: " + e.getMessage());
                        }
                        result = new String(response.getData(), response.getOffset(), response.getLength());
                    } while (!result.contains(request));
                    System.out.println(result);
                });
            } catch (IOException e) {
                System.err.println("Broken socket: " + e.getMessage());
            }
        }));
        Utils.close(receivers);
    }
}
