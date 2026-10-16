// Bai 6 TCP: server tra ACK cho tung thong diep de client do thoi gian round-trip.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5206)) {
            System.out.println("TCP benchmark server cong 5206");
            while (true) try (Socket s = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                String line; while ((line = in.readLine()) != null) out.println("ACK|" + line);
            }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
    }
}
