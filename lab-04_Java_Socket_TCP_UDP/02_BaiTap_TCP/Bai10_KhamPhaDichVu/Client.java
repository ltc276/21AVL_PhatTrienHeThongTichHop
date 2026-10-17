// Bai 10 TCP: gui DISCOVER_SERVICE bang TCP, doc port dich vu va ket noi toi dich vu.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try {
            String reply;
            try (Socket s = new Socket(host, 5210); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                out.println("DISCOVER_SERVICE");
                reply = in.readLine();
            }
            System.out.println("Discovery: " + reply);
            String[] p = reply.split("\\s+");
            if (p.length != 4 || !p[0].equals("SERVICE")) return;
            int port = Integer.parseInt(p[2]);
            try (Socket s = new Socket(host, port); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                out.println("PING");
                System.out.println("Service: " + in.readLine());
            }
        } catch (Exception e) {
            System.out.println("Loi client: " + e.getMessage());
        }
    }
}