// Bai 7 TCP: client gui HELLO truoc, sau do gui tin nhan; QUIT ket thuc.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket(host, 5207); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8)); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.print("Nhap clientId (chu/so/_/-): "); out.println("HELLO " + kb.readLine()); System.out.println(in.readLine()); String line;
            System.out.println("Nhap tin nhan; go QUIT de thoat."); while ((line = kb.readLine()) != null) {
                if (line.equals("QUIT")) { out.println("QUIT"); System.out.println(in.readLine()); break; }
                out.println("MSG " + line); System.out.println(in.readLine());
            }
        } catch (IOException e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
