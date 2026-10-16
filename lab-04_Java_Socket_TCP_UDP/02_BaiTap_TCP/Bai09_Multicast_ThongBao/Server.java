// Bai 9 TCP: thay multicast bang broadcast TCP toi tat ca client dang ket noi.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets; import java.util.concurrent.CopyOnWriteArrayList;
public class Server {
    static final CopyOnWriteArrayList<PrintWriter> clients = new CopyOnWriteArrayList<>();
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5209); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            Thread acceptor = new Thread(() -> {
                while (!server.isClosed()) {
                    try {
                        Socket s = server.accept();
                        new Thread(() -> xuLy(s)).start();
                    } catch (IOException e) { break; }
                }
            }); acceptor.setDaemon(true); acceptor.start();
            System.out.println("TCP thong bao server cong 5209. Nhap noi dung de gui cho tat ca; QUIT de dung.");
            String line; while ((line = kb.readLine()) != null) { guiTat(line); if (line.equalsIgnoreCase("QUIT")) break; }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
    }
    static void xuLy(Socket s) {
        try (s; BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            clients.add(out); out.println("Da ket noi den kenh thong bao."); while (in.readLine() != null) { }
        } catch (IOException e) { } finally { }
    }
    static void guiTat(String text) { for (PrintWriter out : clients) { out.println("THONG BAO: " + text); if (out.checkError()) clients.remove(out); } }
}
