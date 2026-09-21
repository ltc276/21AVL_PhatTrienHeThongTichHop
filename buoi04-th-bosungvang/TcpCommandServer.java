import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

public class TcpCommandServer {

    private static final int PORT = 5000;

    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP server listening on port " + PORT);
            while (true) {
                try (Socket socket = server.accept()) {
                    serve(socket);
                } catch (IOException e) {
                    System.err.println("Lỗi phiên client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Không mở được server: " + e.getMessage());
        }
    }

    static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                    socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(
                    socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

            String request;
            while ((request = in.readLine()) != null) {
                String response = process(request);
                out.println(response);
                if (request.equalsIgnoreCase("QUIT")) break;
            }
        }
    }

    static String process(String request) {
        String trimmed = request.trim();
        if (trimmed.equalsIgnoreCase("PING")) return "OK PONG";
        if (trimmed.equalsIgnoreCase("TIME")) {
            return "OK " + LocalDateTime.now();
        }
        if (trimmed.equalsIgnoreCase("QUIT")) return "OK BYE";
        if (trimmed.regionMatches(true, 0, "UPPER ", 0, 6)) {
            return "OK " + trimmed.substring(6).toUpperCase(Locale.ROOT);
        }
        return "ERR UNKNOWN_COMMAND";
    }
}
