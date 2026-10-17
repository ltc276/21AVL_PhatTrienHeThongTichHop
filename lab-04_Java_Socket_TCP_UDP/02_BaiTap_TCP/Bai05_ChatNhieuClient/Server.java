// Bai 5 TCP: chat nhieu client, nickname duy nhat, USERS, MSG va QUIT.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    static final int PORT = 5205;
    static final Set < String > NICKS = ConcurrentHashMap.newKeySet();
    static final CopyOnWriteArrayList < ClientHandler > CLIENTS = new CopyOnWriteArrayList < > ();
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(30);
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP chat server dang nghe cong " + PORT);
            while (true) {
                Socket s = server.accept();
                pool.submit(() -> xuLy(s));
            }
        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
    static void xuLy(Socket socket) {
        ClientHandler c = null;
        try (socket; BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String nick = in.readLine();
            if (nick == null || nick.trim().isEmpty() || !NICKS.add(nick.trim())) {
                out.println("ERR NICKNAME_TAKEN_OR_EMPTY");
                return;
            }
            c = new ClientHandler(nick.trim(), out);
            CLIENTS.add(c);
            out.println("OK JOINED " + c.nick);
            guiTat(c.nick + " da vao chat.");
            String line;
            while ((line = in.readLine()) != null) {
                if (line.equalsIgnoreCase("USERS")) {
                    out.println("USERS " + danhSach());
                } else if (line.startsWith("MSG ")) {
                    String text = line.substring(4);
                    guiTat(c.nick + ": " + text, c);
                    out.println("OK SENT");
                } else if (line.equalsIgnoreCase("QUIT")) {
                    out.println("OK BYE");
                    break;
                } else out.println("ERR UNKNOWN_COMMAND");
            }
        } catch (IOException e) {
            System.out.println("Client ngat: " + e.getMessage());
        } finally {
            if (c != null) {
                CLIENTS.remove(c);
                NICKS.remove(c.nick);
                guiTat(c.nick + " da roi chat.");
            }
        }
    }
    static String danhSach() {
        StringBuilder b = new StringBuilder();
        for (ClientHandler c: CLIENTS) {
            if (b.length() > 0) b.append(", ");
            b.append(c.nick);
        }
        return b.toString();
    }
    static void guiTat(String text) {
        guiTat(text, null);
    }
    static void guiTat(String text, ClientHandler boQua) {
        for (ClientHandler c: CLIENTS)
            if (c != boQua) c.out.println(text);
    }
    static class ClientHandler {
        String nick;
        PrintWriter out;
        ClientHandler(String n, PrintWriter o) {
            nick = n;
            out = o;
        }
    }
}