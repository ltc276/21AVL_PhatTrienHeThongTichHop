// Bai 3 TCP: server xu ly DATE, TIME, DATETIME va QUIT.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Server {
    static final int PORT = 5203;
    static final DateTimeFormatter NGAY = DateTimeFormatter.ofPattern("dd MM yyyy");
    static final DateTimeFormatter GIO = DateTimeFormatter.ofPattern("HH mm ss");
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP dich vu ngay gio dang nghe cong " + PORT);
            while (true) try (Socket s = server.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                String cmd;
                while ((cmd = in.readLine()) != null) {
                    LocalDateTime now = LocalDateTime.now(); String kq;
                    switch (cmd.trim().toUpperCase()) {
                        case "DATE": kq = "OK " + now.format(NGAY); break;
                        case "TIME": kq = "OK " + now.format(GIO); break;
                        case "DATETIME": kq = "OK " + now.format(NGAY) + " " + now.format(GIO); break;
                        case "QUIT": kq = "OK BYE"; break;
                        default: kq = "ERR UNKNOWN_COMMAND";
                    }
                    out.println(kq); if (cmd.trim().equalsIgnoreCase("QUIT")) break;
                }
            }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
    }
}
