import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.MessageDigest;

public class TCPServer {

    static final int PORT = 7000;

    public static void main(String[] args) throws Exception {

        ServerSocket server =
                new ServerSocket(PORT);

        System.out.println("TCP File Server dang chay...");

        while (true) {

            Socket socket = server.accept();

            new Thread(() -> xuLyClient(socket)).start();
        }
    }

    static void xuLyClient(Socket socket) {

        try {

            DataInputStream in =
                    new DataInputStream(
                            socket.getInputStream()
                    );

            DataOutputStream out =
                    new DataOutputStream(
                            socket.getOutputStream()
                    );

            // =========================
            // Nhan ten file
            // =========================
            String fileName = in.readUTF();

            // =========================
            // Kiem tra ten file
            // =========================
            if (!tenFileHopLe(fileName)) {

                out.writeUTF(
                        "ERR INVALID_FILENAME"
                );

                out.flush();

                socket.close();

                return;
            }

            // =========================
            // Nhan kich thuoc
            // =========================
            long fileSize = in.readLong();

            // =========================
            // Nhan SHA-256
            // =========================
            byte[] clientHash = new byte[32];

            in.readFully(clientHash);

            // =========================
            // Thu muc upload co dinh
            // =========================
            Path uploadDir =
                    Paths.get("upload");

            Files.createDirectories(uploadDir);

            Path file =
                    uploadDir.resolve(fileName);

            // =========================
            // Ghi file
            // =========================
            FileOutputStream fos =
                    new FileOutputStream(file.toFile());

            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            long remaining = fileSize;

            byte[] buffer =
                    new byte[4096];

            while (remaining > 0) {

                int need =
                        (int) Math.min(
                                buffer.length,
                                remaining
                        );

                int n =
                        in.read(buffer, 0, need);

                if (n == -1) {
                    throw new IOException(
                            "Khong du byte"
                    );
                }

                fos.write(buffer, 0, n);

                md.update(buffer, 0, n);

                remaining -= n;
            }

            fos.close();

            // =========================
            // Tinh lai SHA-256
            // =========================
            byte[] serverHash =
                    md.digest();

            // =========================
            // So sanh
            // =========================
            if (MessageDigest.isEqual(
                    clientHash,
                    serverHash)) {

                out.writeUTF("OK");
                out.flush();

                System.out.println(
                        "Nhan file thanh cong: "
                                + fileName
                );

            } else {

                out.writeUTF(
                        "ERR HASH_MISMATCH"
                );

                out.flush();

                Files.deleteIfExists(file);

                System.out.println(
                        "Sai SHA-256: "
                                + fileName
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Loi: " + e.getMessage()
            );

        } finally {

            try {
                socket.close();
            } catch (Exception e) {
            }
        }
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