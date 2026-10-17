// Bai 10 TCP: client tim dich vu qua TCP discovery, sau do ket noi cong dich vu.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class Server {
    static final int DISCOVERY_PORT = 5210, SERVICE_PORT = 5220;
    public static void main(String[] args) {
        Thread service = new Thread(() -> chayDichVu());
        service.setDaemon(true);
        service.start();
        try (ServerSocket server = new ServerSocket(DISCOVERY_PORT)) {
            System.out.println("TCP discovery dang nghe cong " + DISCOVERY_PORT + "; dich vu cong " + SERVICE_PORT);
            while (true) try (Socket s = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                String req = in.readLine();
                if ("DISCOVER_SERVICE".equals(req)) out.println("SERVICE EchoService " + SERVICE_PORT + " 1.0");
                else out.println("ERR UNKNOWN_COMMAND");
            }
        } catch (IOException e) {
            System.out.println("Loi discovery server: " + e.getMessage());
        }
    }
    static void chayDichVu() {
        try (ServerSocket server = new ServerSocket(SERVICE_PORT)) {
            while (true) try (Socket s = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                String q = in.readLine();
                out.println("PING".equals(q) ? "OK PONG" : "OK " + q);
            }
        } catch (IOException e) {
            System.out.println("Loi service: " + e.getMessage());
        }
    }
}