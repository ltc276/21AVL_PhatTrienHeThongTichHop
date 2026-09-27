import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.security.MessageDigest;
import java.util.Scanner;

public class TCPClient {

    static final String HOST = "localhost";
    static final int PORT = 7000;

    public static void main(String[] args) throws Exception {

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

        Socket socket =
                new Socket(HOST, PORT);

        DataOutputStream out =
                new DataOutputStream(
                        socket.getOutputStream()
                );

        DataInputStream in =
                new DataInputStream(
                        socket.getInputStream()
                );

        // Gui metadata
        out.writeUTF(fileName);
        out.writeLong(fileSize);
        out.write(hash);

        // Gui du lieu file
        FileInputStream fis =
                new FileInputStream(
                        path.toFile()
                );

        byte[] buffer =
                new byte[4096];

        int n;

        while ((n = fis.read(buffer)) != -1) {

            out.write(buffer, 0, n);
        }

        out.flush();

        fis.close();

        // Nhan ket qua
        String result =
                in.readUTF();

        System.out.println(
                "Server: " + result
        );

        socket.close();
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

            md.update(buffer, 0, n);
        }

        fis.close();

        return md.digest();
    }
}