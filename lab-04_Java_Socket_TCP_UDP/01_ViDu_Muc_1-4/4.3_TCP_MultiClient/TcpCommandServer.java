// Bai vi du 4.2: TCP server nhan lenh PING, UPPER, TIME va QUIT.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

public class TcpCommandServer {
    public static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP server dang nghe cong " + PORT);
            while (true) {
                Socket socket = server.accept();
                try (socket) {
                    serve(socket);
                } catch (IOException e) {
                    System.out.println("Loi client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Khong mo duoc server: " + e.getMessage());
        }
    }

    public static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String request;
            while ((request = in.readLine()) != null) {
                String lenh = request.trim();
                String answer;
                if (lenh.equalsIgnoreCase("PING")) answer = "OK PONG";
                else if (lenh.equalsIgnoreCase("TIME")) answer = "OK " + LocalDateTime.now();
                else if (lenh.equalsIgnoreCase("QUIT")) answer = "OK BYE";
                else if (lenh.regionMatches(true, 0, "UPPER ", 0, 6)) answer = "OK " + lenh.substring(6).toUpperCase(Locale.ROOT);
                else answer = "ERR UNKNOWN_COMMAND";
                out.println(answer);
                if (lenh.equalsIgnoreCase("QUIT")) break;
            }
        }
    }
}
