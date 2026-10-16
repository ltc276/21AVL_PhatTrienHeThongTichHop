// Bai 5 TCP: client dat nickname, gui MSG/USERS/QUIT va nhan tin nhan lien tuc.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket(host, 5205); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
             PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.print("Nhap nickname: "); String nick = kb.readLine(); out.println(nick);
            String first = in.readLine(); System.out.println(first); if (first == null || !first.startsWith("OK JOINED")) return;
            Thread listener = new Thread(() -> { try { String line; while ((line = in.readLine()) != null) System.out.println(line); } catch (IOException e) { } });
            listener.setDaemon(true); listener.start();
            System.out.println("Lenh: USERS, MSG noi dung, QUIT"); String line;
            while ((line = kb.readLine()) != null) { out.println(line); if (line.equalsIgnoreCase("QUIT")) break; }
        } catch (IOException e) { System.out.println("Loi ket noi: " + e.getMessage()); }
    }
}
