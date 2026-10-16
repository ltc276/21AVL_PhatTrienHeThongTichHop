// Bai 2 TCP: client gui nhieu chu so/du lieu sai tren cung mot ket noi.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket socket = new Socket(host, 5202);
             BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Nhap 0-9, thu ca chuoi rong/10/a/khoang trang; QUIT de thoat.");
            String s;
            while ((s = kb.readLine()) != null) { out.println(s); System.out.println(in.readLine()); if (s.equals("QUIT")) break; }
        } catch (IOException e) { System.out.println("Loi ket noi: " + e.getMessage()); }
    }
}
