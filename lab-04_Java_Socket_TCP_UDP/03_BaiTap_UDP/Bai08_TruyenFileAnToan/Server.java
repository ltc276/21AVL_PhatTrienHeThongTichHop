// Bai 8 UDP: nhan metadata, nhan chunk theo so thu tu va tra ACK de client gui lai neu mat goi.
import java.net.*; import java.nio.*; import java.nio.charset.StandardCharsets; import java.nio.file.*; import java.io.*; import java.security.*;
public class Server {
    static final int PORT = 5208;
    static InetAddress lastIp; static int lastPort = -1; static int lastSeq = -2; static String lastReply = "";
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            System.out.println("UDP file server cong 5208; file nhan luu trong uploads");
            while (true) {
                DatagramPacket first = receive(socket, 4096);
                String text = new String(first.getData(), first.getOffset(), first.getLength(), StandardCharsets.UTF_8);
                if (!text.startsWith("META|")) {
                    if (lastIp != null && first.getAddress().equals(lastIp) && first.getPort() == lastPort && first.getLength() >= 4 && ByteBuffer.wrap(first.getData(), first.getOffset(), 4).getInt() == lastSeq) send(socket, first.getAddress(), first.getPort(), lastReply);
                    else send(socket, first.getAddress(), first.getPort(), "ERR EXPECT_META");
                    continue;
                }
                String[] fields = text.split("\\|", -1);
                if (fields.length != 4) { send(socket, first.getAddress(), first.getPort(), "ERR INVALID_METADATA"); continue; }
                String name = fields[1], expectedHash = fields[3]; long size;
                try { size = Long.parseLong(fields[2]); } catch (NumberFormatException e) { send(socket, first.getAddress(), first.getPort(), "ERR INVALID_SIZE"); continue; }
                if (!tenHopLe(name) || size < 0) { send(socket, first.getAddress(), first.getPort(), "ERR INVALID_METADATA"); continue; }
                Path dir = Path.of("uploads").toAbsolutePath().normalize(); Files.createDirectories(dir); Path dest = dir.resolve(name).normalize();
                if (!dest.getParent().equals(dir)) { send(socket, first.getAddress(), first.getPort(), "ERR INVALID_PATH"); continue; }
                send(socket, first.getAddress(), first.getPort(), "READY"); long received = 0; int expectedSeq = 0; boolean failed = false;
                socket.setSoTimeout(5000);
                try (OutputStream out = Files.newOutputStream(dest)) {
                    while (received < size) {
                        DatagramPacket part;
                        try { part = receive(socket, 1200); } catch (SocketTimeoutException e) { failed = true; break; }
                        if (!part.getAddress().equals(first.getAddress()) || part.getPort() != first.getPort() || part.getLength() < 4) continue;
                        int seq = ByteBuffer.wrap(part.getData(), part.getOffset(), 4).getInt();
                        int len = part.getLength() - 4;
                        if (seq < expectedSeq) { send(socket, part.getAddress(), part.getPort(), "ACK|" + seq); continue; }
                        if (seq != expectedSeq || received + len > size) { send(socket, part.getAddress(), part.getPort(), "ERR BAD_SEQUENCE"); continue; }
                        out.write(part.getData(), part.getOffset() + 4, len); received += len; expectedSeq++;
                        send(socket, part.getAddress(), part.getPort(), "ACK|" + seq);
                    }
                }
                socket.setSoTimeout(0);
                if (failed || received != size) { send(socket, first.getAddress(), first.getPort(), "ERR INCOMPLETE"); continue; }
                String actual = sha256(dest); String result = actual.equalsIgnoreCase(expectedHash) ? "DONE|" + (expectedSeq - 1) + "|OK" : "DONE|" + (expectedSeq - 1) + "|ERR HASH_MISMATCH";
                lastIp = first.getAddress(); lastPort = first.getPort(); lastSeq = expectedSeq - 1; lastReply = result;
                send(socket, first.getAddress(), first.getPort(), result);
            }
        } catch (Exception e) { System.out.println("Loi UDP file server: " + e.getMessage()); }
    }
    static DatagramPacket receive(DatagramSocket s, int size) throws IOException { byte[] b = new byte[size]; DatagramPacket p = new DatagramPacket(b, b.length); s.receive(p); return p; }
    static void send(DatagramSocket s, InetAddress ip, int port, String text) throws IOException { byte[] b = text.getBytes(StandardCharsets.UTF_8); s.send(new DatagramPacket(b, b.length, ip, port)); }
    static boolean tenHopLe(String n) { return n != null && n.matches("[A-Za-z0-9._-]+") && !n.equals(".") && !n.equals(".."); }
    static String sha256(Path path) throws Exception { MessageDigest md = MessageDigest.getInstance("SHA-256"); try (InputStream in = Files.newInputStream(path)) { byte[] b = new byte[4096]; int n; while ((n=in.read(b))!=-1) md.update(b,0,n); } StringBuilder s=new StringBuilder(); for(byte x:md.digest()) s.append(String.format("%02x",x)); return s.toString(); }
}
