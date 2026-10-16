// Bai 8 TCP: gui metadata truoc, sau do gui dung so byte cua file.
import java.io.*; import java.net.*; import java.nio.file.*; import java.security.*;
public class Client {
    public static void main(String[] args) {
        if (args.length < 1) { System.out.println("Cach dung: java Client <duong_dan_file> [host]"); return; }
        Path file = Path.of(args[0]); String host = args.length > 1 ? args[1] : "localhost";
        try {
            if (!Files.isRegularFile(file)) { System.out.println("File khong ton tai"); return; }
            String name = file.getFileName().toString(); long size = Files.size(file); String hash = sha256(file);
            try (Socket s = new Socket(host, 5208); DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream())); DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream())); InputStream f = Files.newInputStream(file)) {
                out.writeUTF(name); out.writeLong(size); out.writeUTF(hash); out.flush();
                byte[] buf = new byte[4096]; int n; while ((n = f.read(buf)) != -1) out.write(buf, 0, n); out.flush();
                System.out.println("Server: " + in.readUTF());
            }
        } catch (Exception e) { System.out.println("Loi gui file: " + e.getMessage()); }
    }
    static String sha256(Path path) throws Exception { MessageDigest md = MessageDigest.getInstance("SHA-256"); try (InputStream in = Files.newInputStream(path)) { byte[] b = new byte[4096]; int n; while ((n = in.read(b)) != -1) md.update(b, 0, n); } StringBuilder s = new StringBuilder(); for (byte x : md.digest()) s.append(String.format("%02x", x)); return s.toString(); }
}
