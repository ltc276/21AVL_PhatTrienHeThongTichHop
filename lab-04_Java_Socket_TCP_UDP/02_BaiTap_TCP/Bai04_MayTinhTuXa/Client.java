// Bai 4 TCP: client gui lenh CALC theo dang CALC toan_tu so1 so2.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket(host, 5204); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Vi du CALC + 100 200; go QUIT de thoat."); String cmd; while ((cmd = kb.readLine()) != null) { out.println(cmd); System.out.println(in.readLine()); if (cmd.equalsIgnoreCase("QUIT")) break; }
        } catch (IOException e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
