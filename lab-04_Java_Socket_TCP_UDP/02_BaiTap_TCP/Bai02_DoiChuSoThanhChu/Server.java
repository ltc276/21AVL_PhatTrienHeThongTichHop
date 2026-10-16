// Bai 2 TCP: nhan dung mot chu so 0-9 va tra cach doc bang tieng Viet khong dau.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    static final int PORT = 5202;
    static String[] chu = {"khong", "mot", "hai", "ba", "bon", "nam", "sau", "bay", "tam", "chin"};
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP server doi chu so dang nghe cong " + PORT);
            while (true) {
                try (Socket socket = server.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                     PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
                    String s;
                    while ((s = in.readLine()) != null) {
                        if (s.equals("QUIT")) { out.println("OK BYE"); break; }
                        if (s.length() == 1 && s.charAt(0) >= '0' && s.charAt(0) <= '9') out.println("OK " + chu[s.charAt(0) - '0']);
                        else out.println("ERR INVALID_DIGIT");
                    }
                } catch (IOException e) { System.out.println("Loi client: " + e.getMessage()); }
            }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
    }
}
