import java.net.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.io.*;
import java.util.*;

public class UDPServer {

    static final int PORT = 7001;
    static final int CHUNK_SIZE = 1000;

    public static void main(String[] args)
            throws Exception {

        DatagramSocket server =
                new DatagramSocket(PORT);

        System.out.println(
                "UDP File Server dang chay..."
        );

        while (true) {

            // =========================
            // Nhan metadata
            // =========================
            byte[] buffer =
                    new byte[2048];

            DatagramPacket packet =
                    new DatagramPacket(
                            buffer,
                            buffer.length
                    );

            server.receive(packet);

            DataInputStream meta =
                    new DataInputStream(
                            new ByteArrayInputStream(
                                    packet.getData(),
                                    packet.getOffset(),
                                    packet.getLength()
                            )
                    );

            String fileName =
                    meta.readUTF();

            long fileSize =
                    meta.readLong();

            byte[] clientHash =
                    new byte[32];

            meta.readFully(clientHash);

            // =========================
            // Kiem tra ten file
            // =========================
            if (!tenFileHopLe(fileName)) {

                send(
                        server,
                        "ERR INVALID_FILENAME",
                        packet.getAddress(),
                        packet.getPort()
                );

                continue;
            }

            // =========================
            // Tao thu muc upload
            // =========================
            Path uploadDir =
                    Paths.get("upload");

            Files.createDirectories(uploadDir);

            Path file =
                    uploadDir.resolve(fileName);

            FileOutputStream fos =
                    new FileOutputStream(
                            file.toFile()
                    );

            MessageDigest md =
                    MessageDigest.getInstance(
                            "SHA-256"
                    );

            long received = 0;

            int expectedSeq = 0;

            while (received < fileSize) {

                byte[] data =
                        new byte[1200];

                DatagramPacket dataPacket =
                        new DatagramPacket(
                                data,
                                data.length
                        );

                server.receive(dataPacket);

                DataInputStream din =
                        new DataInputStream(
                                new ByteArrayInputStream(
                                        dataPacket.getData(),
                                        dataPacket.getOffset(),
                                        dataPacket.getLength()
                                )
                        );

                int seq =
                        din.readInt();

                int length =
                        din.readInt();

                byte[] chunk =
                        new byte[length];

                din.readFully(chunk);

                if (seq == expectedSeq) {

                    fos.write(chunk);

                    md.update(chunk);

                    received += length;

                    send(
                            server,
                            "ACK " + seq,
                            packet.getAddress(),
                            packet.getPort()
                    );

                    expectedSeq++;
                }
            }

            fos.close();

            byte[] serverHash =
                    md.digest();

            if (MessageDigest.isEqual(
                    clientHash,
                    serverHash)) {

                send(
                        server,
                        "OK",
                        packet.getAddress(),
                        packet.getPort()
                );

                System.out.println(
                        "Nhan file thanh cong: "
                                + fileName
                );

            } else {

                send(
                        server,
                        "ERR HASH_MISMATCH",
                        packet.getAddress(),
                        packet.getPort()
                );

                Files.deleteIfExists(file);

                System.out.println(
                        "Sai SHA-256: "
                                + fileName
                );
            }
        }
    }

    static void send(
            DatagramSocket server,
            String message,
            InetAddress address,
            int port)
            throws Exception {

        byte[] data =
                message.getBytes();

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length,
                        address,
                        port
                );

        server.send(packet);
    }

    static boolean tenFileHopLe(
            String fileName) {

        if (fileName == null ||
                fileName.isEmpty()) {

            return false;
        }

        if (fileName.equals(".") ||
                fileName.equals("..")) {

            return false;
        }

        if (fileName.contains("/") ||
                fileName.contains("\\")) {

            return false;
        }

        if (fileName.contains("..")) {

            return false;
        }

        return true;
    }
}