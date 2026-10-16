// Bai 8 TCP: nhan ten file, kich thuoc va SHA-256 truoc khi doc dung so byte.
import java.io.*; import java.net.*; import java.nio.file.*; import java.security.*;
public class Server {
    static final int PORT = 5208;
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP file server cong 5208; file nhan luu trong uploads");
            while (true) { Socket s = server.accept(); xuLy(s); }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
    }
    static void xuLy(Socket s) {
        try (s; DataInputStream in = new DataInputStream(new BufferedInputStream(s.getInputStream())); DataOutputStream out = new DataOutputStream(new BufferedOutputStream(s.getOutputStream()))) {
            String name = in.readUTF(); long size = in.readLong(); String hash = in.readUTF();
            if (!tenHopLe(name) || size < 0) { out.writeUTF("ERR INVALID_METADATA"); out.flush(); return; }
            Path dir = Path.of("uploads").toAbsolutePath().normalize(); Files.createDirectories(dir); Path dest = dir.resolve(name).normalize();
            if (!dest.getParent().equals(dir)) { out.writeUTF("ERR INVALID_PATH"); out.flush(); return; }
            try (OutputStream file = Files.newOutputStream(dest)) {
                byte[] buf = new byte[4096]; long conLai = size;
                while (conLai > 0) { int n = in.read(buf, 0, (int)Math.min(buf.length, conLai)); if (n == -1) throw new EOFException("File bi ngat giua chung"); file.write(buf, 0, n); conLai -= n; }
            }
            String actual = sha256(dest); String answer = actual.equalsIgnoreCase(hash) ? "OK SAVED " + dest.getFileName() : "ERR HASH_MISMATCH";
            out.writeUTF(answer); out.flush();
        } catch (Exception e) { System.out.println("Loi nhan file: " + e.getMessage()); }
    }
    static boolean tenHopLe(String name) { return name != null && name.matches("[A-Za-z0-9._-]+") && !name.equals(".") && !name.equals(".."); }
    static String sha256(Path path) throws Exception { MessageDigest md = MessageDigest.getInstance("SHA-256"); try (InputStream in = Files.newInputStream(path)) { byte[] b = new byte[4096]; int n; while ((n = in.read(b)) != -1) md.update(b, 0, n); } StringBuilder s = new StringBuilder(); for (byte x : md.digest()) s.append(String.format("%02x", x)); return s.toString(); }
}
