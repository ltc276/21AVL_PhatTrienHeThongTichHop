// Bai 7 UDP: server gan clientId cho endpoint sau lenh HELLO|id va ghi nhat ky.
import java.net.*; import java.nio.charset.StandardCharsets; import java.nio.file.*; import java.time.LocalDateTime; import java.util.*;
public class Server {
    static Map<String,String> ids = new HashMap<>();
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5207)) {
            System.out.println("UDP log server cong 5207");
            while (true) { byte[] b = new byte[4096]; DatagramPacket p = new DatagramPacket(b, b.length); socket.receive(p); String key = p.getAddress().getHostAddress() + ":" + p.getPort(); String req = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); String res;
                if (req.startsWith("HELLO|")) { String id = req.substring(6).trim(); if (id.matches("[A-Za-z0-9_-]+")) { ids.put(key, id); res = "OK HELLO"; } else res = "ERR INVALID_CLIENT_ID"; }
                else if (req.equals("QUIT")) { ids.remove(key); res = "OK BYE"; }
                else if (req.startsWith("MSG|")) { String id = ids.get(key); if (id == null) res = "ERR HELLO_REQUIRED"; else { try { ghi(id, LocalDateTime.now() + " | " + key + " | " + req.substring(4)); res = "OK SAVED"; } catch (Exception e) { res = "ERR WRITE_FAILED"; } } }
                else res = "ERR UNKNOWN_COMMAND";
                byte[] out = res.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(out, out.length, p.getAddress(), p.getPort()));
            }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }
    static synchronized void ghi(String id, String dong) throws Exception { Path dir = Path.of("data_logs"); Files.createDirectories(dir); Files.writeString(dir.resolve(id + ".txt"), dong + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
}
