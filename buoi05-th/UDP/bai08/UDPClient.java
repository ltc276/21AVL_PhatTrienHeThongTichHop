import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class UDPClient {

    static final String HOST = "localhost";
    static final int PORT = 7001;

    static final int CHUNK_SIZE = 1000;

    public static void main(String[] args)
            throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print(
                "Nhap duong dan file can gui: "
        );

        String pathString =
                sc.nextLine();

        Path path =
                Paths.get(pathString);

        if (!Files.exists(path)) {

            System.out.println(
                    "File khong ton tai."
            );

            return;
        }

        long fileSize =
                Files.size(path);

        byte[] hash =
                sha256(path);

        System.out.print(
                "Nhap ten file luu tren server: "
        );

        String fileName =
                sc.nextLine();

        DatagramSocket client =
                new DatagramSocket();

        client.setSoTimeout(3000);

        InetAddress server =
                InetAddress.getByName(HOST);

        // =========================
        // GUI METADATA
        // =========================
        ByteArrayOutputStream metaBytes =
                new ByteArrayOutputStream();

        DataOutputStream meta =
                new DataOutputStream(
                        metaBytes
                );

        meta.writeUTF(fileName);
        meta.writeLong(fileSize);
        meta.write(hash);

        meta.flush();

        byte[] metaData =
                metaBytes.toByteArray();

        DatagramPacket metaPacket =
                new DatagramPacket(
                        metaData,
                        metaData.length,
                        server,
                        PORT
                );

        client.send(metaPacket);

        // =========================
        // GUI CAC CHUNK
        // =========================
        FileInputStream fis =
                new FileInputStream(
                        path.toFile()
                );

        byte[] buffer =
                new byte[CHUNK_SIZE];

        int seq = 0;

        int n;

        while ((n = fis.read(buffer)) != -1) {

            ByteArrayOutputStream outBytes =
                    new ByteArrayOutputStream();

            DataOutputStream out =
                    new DataOutputStream(
                            outBytes
                    );

            out.writeInt(seq);
            out.writeInt(n);
            out.write(
                    buffer,
                    0,
                    n
            );

            out.flush();

            byte[] data =
                    outBytes.toByteArray();

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length,
                            server,
                            PORT
                    );

            while (true) {

                client.send(packet);

                byte[] ackBuffer =
                        new byte[1024];

                DatagramPacket ack =
                        new DatagramPacket(
                                ackBuffer,
                                ackBuffer.length
                        );

                try {

                    client.receive(ack);

                    String result =
                            new String(
                                    ack.getData(),
                                    0,
                                    ack.getLength()
                            );

                    if (result.equals(
                            "ACK " + seq)) {

                        break;
                    }

                } catch (SocketTimeoutException e) {

                    System.out.println(
                            "Gui lai chunk " + seq
                    );
                }
            }

            seq++;
        }

        fis.close();

        // =========================
        // DOI KET QUA
        // =========================
        byte[] resultBuffer =
                new byte[1024];

        DatagramPacket resultPacket =
                new DatagramPacket(
                        resultBuffer,
                        resultBuffer.length
                );

        client.receive(resultPacket);

        String result =
                new String(
                        resultPacket.getData(),
                        0,
                        resultPacket.getLength()
                );

        System.out.println(
                "Server: " + result
        );

        client.close();
    }

    static byte[] sha256(Path path)
            throws Exception {

        MessageDigest md =
                MessageDigest.getInstance(
                        "SHA-256"
                );

        FileInputStream fis =
                new FileInputStream(
                        path.toFile()
                );

        byte[] buffer =
                new byte[4096];

        int n;

        while ((n = fis.read(buffer)) != -1) {

            md.update(
                    buffer,
                    0,
                    n
            );
        }

        fis.close();

        return md.digest();
    }
}