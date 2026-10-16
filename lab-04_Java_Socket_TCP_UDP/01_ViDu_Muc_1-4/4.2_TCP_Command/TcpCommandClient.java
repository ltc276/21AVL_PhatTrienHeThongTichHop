// Bai vi du 4.2: TCP client gui lenh va doc phan hoi tu server.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TcpCommandClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;
        try (Socket socket = new Socket(host, port);
             BufferedReader banPhim = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Nhap PING, TIME, UPPER xin chao hoac QUIT:");
            String lenh;
            while ((lenh = banPhim.readLine()) != null) {
                out.println(lenh);
                System.out.println("Server: " + in.readLine());
                if (lenh.equalsIgnoreCase("QUIT")) break;
            }
        } catch (IOException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}
