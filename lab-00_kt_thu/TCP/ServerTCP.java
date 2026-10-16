import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class ServerTCP {
    //Cau1 a)
    static final int PORT = 7000;
    // STT mau la so le. Doi thanh STT cua ban neu can.
    static final int STT = 25000901;

    public static void main(String[] args) throws Exception {
        // Server TCP mo cong 7000 va cho client ket noi.
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP Server dang chay tai cong 7000...");
            //Cau1 b)

            while (true) {
                Socket socket = server.accept(); // Dung lai den khi co client ket noi.
                System.out.println("Client ket noi: " + socket.getInetAddress());

                // Moi client duoc xu ly boi mot thread rieng.
                new ClientHandler(socket).start();
            }
        }
    }
    //Cau 3:

    // Kiem tra dich vu nao duoc phep theo STT chan/le.
    static boolean isAllowed(int service) {
        if (STT % 2 == 0) {
            return service == 2 || service == 4;
        }
        return service == 1 || service == 3;
    }

    // Menu duoc gui cho client sau khi ket noi va sau moi dich vu.
    static String getMenu() {
        if (STT % 2 == 0) {
            return "STT " + STT + " la so CHAN. Dich vu duoc chon: 2, 4. Nhap 2/4 hoac EXIT.";
        }
        return "STT " + STT + " la so LE. Dich vu duoc chon: 1, 3. Nhap 1/3 hoac EXIT.";
    }

    // Dem so tu: cac tu duoc tach nhau bang khoang trang.
    static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) {
            return 0;
        }
        return text.trim().split("\\s+").length;
    }

    // Dao thu tu cac tu, dao nguoc ky tu trong tung tu, viet hoa chu dau.
    // Vi du: PHAT TRIEN -> Neirt Tahp (neu dao ca thu tu tu).
    static String reverseWholeString(String text) {
        if (text.trim().isEmpty()) {
            return "";
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            if (result.length() > 0) result.append(" ");
            result.append(reverseAndCapitalize(words[i]));
        }
        return result.toString();
    }

    // Giu nguyen thu tu cac tu, chi dao nguoc ky tu trong moi tu.
    static String reverseEachWord(String text) {
        if (text.trim().isEmpty()) {
            return "";
        }
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < words.length; i++) {
            if (i > 0) result.append(" ");
            result.append(reverseAndCapitalize(words[i]));
        }
        return result.toString();
    }

    // Dao mot tu va viet hoa ky tu dau, cac ky tu con lai viet thuong.
    static String reverseAndCapitalize(String word) {
        String reversed = new StringBuilder(word.toLowerCase(Locale.ROOT)).reverse().toString();
        return reversed.substring(0, 1).toUpperCase(Locale.ROOT) + reversed.substring(1);
    }
    //Cau 1 b)

    // Moi ket noi chay trong mot thread rieng.
    static class ClientHandler extends Thread {
        private final Socket socket;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try (
                Socket s = socket;
                BufferedReader in = new BufferedReader(
                    new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)
            ) {
                while (true) {
                    out.println(getMenu()); // Gui danh sach dich vu cho client.
                    String choice = in.readLine();
                    if (choice == null) break; // Client ngat ket noi.

                    // Client gui EXIT. de dong ket noi.
                    if (choice.trim().equalsIgnoreCase("EXIT.") || choice.trim().equalsIgnoreCase("EXIT")) {
                        out.println("Server da dong ket noi voi client.");
                        break;
                    }

                    int service;
                    try {
                        service = Integer.parseInt(choice.trim());
                    } catch (NumberFormatException e) {
                        out.println("ERROR: Hay nhap so dich vu hop le.");
                        continue;
                    }

                    if (!isAllowed(service)) {
                        out.println("ERROR: STT nay khong duoc phep dung dich vu vua chon.");
                        continue;
                    }

                    out.println("OK: Da chon dich vu " + service + ". Nhap tung dong; nhap . de ket thuc.");
                    int totalWords = 0; // Bien dem tong so tu cho dich vu 3.

                    while (true) {
                        String line = in.readLine();
                        if (line == null) return;

                        // Chi mot dau cham tren mot dong moi ket thuc dich vu.
                        if (line.trim().equals(".")) {
                            if (service == 3) {
                                out.println("Tong so tu cua tat ca cac dong: " + totalWords);
                            } else {
                                out.println("Da ket thuc dich vu " + service + ".");
                            }
                            break;
                        }

                        // Xu ly theo dich vu client da chon.
                        if (service == 1) {
                            out.println(reverseWholeString(line));
                        } else if (service == 2) {
                            out.println(reverseEachWord(line));
                        } else if (service == 3) {
                            // Dich vu 3 chi tra tong ket qua sau khi nhan dau cham.
                            totalWords += countWords(line);
                        } else if (service == 4) {
                            // Dich vu 4 tra so tu cua tung dong ngay lap tuc.
                            out.println("So tu trong dong nay: " + countWords(line));
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Client da ngat ket noi hoac co loi: " + e.getMessage());
            }
        }
    }
}
