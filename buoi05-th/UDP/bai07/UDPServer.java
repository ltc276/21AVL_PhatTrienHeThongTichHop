import java.net.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;
import java.util.concurrent.*;

public class UDPServer {

    static final int PORT = 6001;

    static ConcurrentHashMap<String, String> clients =
            new ConcurrentHashMap<>();

    static DatagramSocket server;

    public static void main(String[] args) throws Exception {

        server = new DatagramSocket(PORT);

        System.out.println("UDP Log Server dang chay...");

        while (true) {

            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length
                    );

            server.receive(packet);

            String message =
                    new String(
                            packet.getData(),
                            packet.getOffset(),
                            packet.getLength(),
                            "UTF-8"
                    );

            String remote =
                    packet.getAddress().getHostAddress()
                    + ":" +
                    packet.getPort();

            // HELLO
            if (message.startsWith("HELLO ")) {

                String clientId =
                        message.substring(6).trim();

                if (!clientId.matches(
                        "[A-Za-z0-9_-]+")) {

                    send(
                            "ERR INVALID_CLIENT_ID",
                            packet
                    );

                    continue;
                }

                clients.put(
                        remote,
                        clientId
                );

                send(
                        "OK",
                        packet
                );

                System.out.println(
                        "Client " +
                        clientId +
                        " ket noi: " +
                        remote
                );

                continue;
            }

            // Tim clientId
            String clientId =
                    clients.get(remote);

            if (clientId == null) {

                send(
                        "ERR SEND_HELLO_FIRST",
                        packet
                );

                continue;
            }

            // QUIT
            if (message.equals("QUIT")) {

                clients.remove(remote);

                send(
                        "BYE",
                        packet
                );

                continue;
            }

            // Ghi log
            ghiLog(
                    clientId,
                    remote,
                    message
            );

            send(
                    "SAVED",
                    packet
            );
        }
    }

    static void send(
            String message,
            DatagramPacket oldPacket)
            throws Exception {

        byte[] data =
                message.getBytes("UTF-8");

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length,
                        oldPacket.getAddress(),
                        oldPacket.getPort()
                );

        server.send(packet);
    }

    static synchronized void ghiLog(
            String clientId,
            String remote,
            String message) throws Exception {

        Path folder =
                Paths.get("data", "logs");

        Files.createDirectories(folder);

        Path file =
                folder.resolve(
                        clientId + ".txt"
                );

        String time =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss"
                        )
                );

        String line =
                time +
                " | " +
                remote +
                " | " +
                message +
                System.lineSeparator();

        Files.write(
                file,
                line.getBytes("UTF-8"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}