// Bai 7 TCP: nhan HELLO clientId, ghi tung tin nhan vao data_logs/clientId.txt.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets; import java.nio.file.*; import java.time.LocalDateTime; import java.util.concurrent.*;
public class Server {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        try (ServerSocket server = new ServerSocket(5207)) {
            System.out.println("TCP log server cong 5207");
            while (true) { Socket s = server.accept(); pool.submit(() -> xuLy(s)); }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); } finally { pool.shutdown(); }
    }
    static void xuLy(Socket s) {
        try (s; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String hello = in.readLine(); if (hello == null || !hello.startsWith("HELLO ")) { out.println("ERR HELLO_REQUIRED"); return; }
            String id = hello.substring(6).trim(); if (!id.matches("[A-Za-z0-9_-]+")) { out.println("ERR INVALID_CLIENT_ID"); return; }
            out.println("OK HELLO"); String line;
            while ((line = in.readLine()) != null) {
                if (line.equals("QUIT")) { out.println("OK BYE"); break; }
                String text = line.startsWith("MSG ") ? line.substring(4) : line;
                ghi(id, LocalDateTime.now() + " | " + s.getRemoteSocketAddress() + " | " + text); out.println("OK SAVED");
            }
        } catch (Exception e) { System.out.println("Loi client/log: " + e.getMessage()); }
    }
    static synchronized void ghi(String id, String dong) throws IOException { Path dir = Path.of("data_logs"); Files.createDirectories(dir); Files.writeString(dir.resolve(id + ".txt"), dong + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND); }
}
