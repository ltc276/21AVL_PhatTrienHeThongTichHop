import java.net.*;
import java.util.concurrent.*;

public class UDPChatServer {

    static final int PORT = 5001;

    static ConcurrentHashMap<String, Client> clients =
            new ConcurrentHashMap<>();

    static ExecutorService pool =
            Executors.newFixedThreadPool(10);

    static DatagramSocket server;

    public static void main(String[] args) throws Exception {

        server = new DatagramSocket(PORT);

        System.out.println("UDP Server dang chay...");

        while (true) {

            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(data, data.length);

            server.receive(packet);

            pool.execute(
                    new Client(packet)
            );
        }
    }

    static void send(
            String message,
            InetAddress address,
            int port) throws Exception {

        byte[] data = message.getBytes();

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length,
                        address,
                        port);

        server.send(packet);
    }

    static void broadcast(
            String message,
            String except) throws Exception {

        for (Client c : clients.values()) {

            if (!c.name.equals(except)) {

                send(
                        message,
                        c.address,
                        c.port
                );
            }
        }
    }

    static class Client implements Runnable {

        DatagramPacket packet;

        String name;

        InetAddress address;

        int port;

        Client(DatagramPacket packet) {

            this.packet = packet;

            this.address = packet.getAddress();

            this.port = packet.getPort();
        }

        public void run() {

            try {

                String message =
                        new String(
                                packet.getData(),
                                0,
                                packet.getLength()
                        );

                if (!message.startsWith("LOGIN ")) {

                    return;
                }

                name = message.substring(6);

                if (clients.containsKey(name)) {

                    send(
                            "Nickname da ton tai",
                            address,
                            port
                    );

                    return;
                }

                clients.put(
                        name,
                        this
                );

                send(
                        "Dang nhap thanh cong",
                        address,
                        port
                );

                broadcast(
                        name + " da vao phong",
                        name
                );

                while (true) {

                    byte[] data = new byte[1024];

                    DatagramPacket p =
                            new DatagramPacket(
                                    data,
                                    data.length
                            );

                    server.receive(p);

                    String msg =
                            new String(
                                    p.getData(),
                                    0,
                                    p.getLength()
                            );

                    if (msg.equals("USERS")) {

                        send(
                                "USERS: " +
                                String.join(
                                        ", ",
                                        clients.keySet()
                                ),
                                address,
                                port
                        );
                    }

                    else if (msg.startsWith("MSG ")) {

                        broadcast(
                                name + ": " +
                                msg.substring(4),
                                name
                        );
                    }

                    else if (msg.equals("QUIT")) {

                        break;
                    }
                }

            } catch (Exception e) {

            } finally {

                if (name != null) {

                    clients.remove(name);

                    try {

                        broadcast(
                                name + " da roi phong",
                                name
                        );

                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}