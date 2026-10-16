// Bai 3 TCP: client gui nhieu lenh ngay gio trong cung ket noi.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket(host, 5203); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Nhap DATE, TIME, DATETIME hoac QUIT:"); String cmd;
            while ((cmd = kb.readLine()) != null) { out.println(cmd); System.out.println(in.readLine()); if (cmd.equalsIgnoreCase("QUIT")) break; }
        } catch (IOException e) { System.out.println("Loi ket noi: " + e.getMessage()); }
    }
}
