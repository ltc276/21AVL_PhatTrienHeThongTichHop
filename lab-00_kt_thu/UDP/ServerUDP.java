import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ServerUDP {
    static final int PORT = 7000;
    // STT mau la so le. Doi thanh STT cua ban neu can.
    static final int STT = 25000902;

    // UDP khong co ket noi nhu TCP, nen server tu luu trang thai theo IP va port client.
    static class Session {
        int service = 0;    // Dich vu dang chon; 0 nghia la chua chon.
        int totalWords = 0; // Tong tu cho dich vu 3.
    }

    public static void main(String[] args) throws Exception {
        // DatagramSocket dung de nhan va gui cac goi tin UDP tren cong 7000.
        try (DatagramSocket server = new DatagramSocket(PORT)) {
            Map<String, Session> sessions = new HashMap<>();
            byte[] buffer = new byte[4096];
            System.out.println("UDP Server dang chay tai cong 7000...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                server.receive(packet); // Cho mot goi tin UDP den.

                String message = new String(packet.getData(), packet.getOffset(),
                        packet.getLength(), StandardCharsets.UTF_8);
                String key = packet.getAddress().getHostAddress() + ":" + packet.getPort();
                Session session = sessions.get(key);

                // Client gui HELLO de bat dau va nhan danh sach dich vu.
                if (message.equalsIgnoreCase("HELLO")) {
                    sessions.put(key, new Session());
                    send(server, packet, getMenu());
                    continue;
                }

                // Client gui EXIT. de ket thuc phien UDP.
                if (message.trim().equalsIgnoreCase("EXIT.") || message.trim().equalsIgnoreCase("EXIT")) {
                    sessions.remove(key);
                    send(server, packet, "Server da ket thuc phien UDP.");
                    continue;
                }

                if (session == null) {
                    send(server, packet, "ERROR: Hay khoi dong client truoc (HELLO).");
                    continue;
                }

                // Lenh chon dich vu duoc gui duoi dang SERVICE:1, SERVICE:3...
                if (message.startsWith("SERVICE:")) {
                    try {
                        int service = Integer.parseInt(message.substring(8).trim());
                        if (!isAllowed(service)) {
                            send(server, packet, "ERROR: STT nay khong duoc phep dung dich vu nay.");
                        } else {
                            session.service = service;
                            session.totalWords = 0;
                            send(server, packet, "OK: Da chon dich vu " + service
                                    + ". Nhap tung dong; nhap . de ket thuc.");
                        }
                    } catch (NumberFormatException e) {
                        send(server, packet, "ERROR: So dich vu khong hop le.");
                    }
                    continue;
                }

                if (session.service == 0) {
                    send(server, packet, "ERROR: Hay chon dich vu truoc.");
                    continue;
                }

                // Dau cham ket thuc phan nhap cua dich vu hien tai.
                if (message.trim().equals(".")) {
                    if (session.service == 3) {
                        send(server, packet, "Tong so tu cua tat ca cac dong: " + session.totalWords);
                    } else {
                        send(server, packet, "Da ket thuc dich vu " + session.service + ".");
                    }
                    session.service = 0; // Cho client chon dich vu tiep theo.
                    session.totalWords = 0;
                    continue;
                }

                // Xu ly tung goi tin noi dung.
                if (session.service == 1) {
                    send(server, packet, reverseWholeString(message));
                } else if (session.service == 2) {
                    send(server, packet, reverseEachWord(message));
                } else if (session.service == 3) {
                    // Dich vu 3 khong phan hoi tung dong, chi cong don so tu.
                    session.totalWords += countWords(message);
                } else if (session.service == 4) {
                    send(server, packet, "So tu trong dong nay: " + countWords(message));
                }
            }
        }
    }

    // Gui phan hoi ve dung dia chi IP va port cua goi tin vua nhan.
    static void send(DatagramSocket server, DatagramPacket request, String text) throws Exception {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        DatagramPacket reply = new DatagramPacket(data, data.length,
                request.getAddress(), request.getPort());
        server.send(reply);
    }

    static boolean isAllowed(int service) {
        if (STT % 2 == 0) return service == 2 || service == 4;
        return service == 1 || service == 3;
    }

    static String getMenu() {
        if (STT % 2 == 0) {
            return "STT " + STT + " la so CHAN. Dich vu duoc chon: 2, 4.";
        }
        return "STT " + STT + " la so LE. Dich vu duoc chon: 1, 3.";
    }

    static int countWords(String text) {
        if (text == null || text.trim().isEmpty()) return 0;
        return text.trim().split("\\s+").length;
    }

    // Dich vu 1: dao thu tu cac tu va dao ky tu trong tung tu.
    static String reverseWholeString(String text) {
        if (text.trim().isEmpty()) return "";
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = words.length - 1; i >= 0; i--) {
            if (result.length() > 0) result.append(" ");
            result.append(reverseAndCapitalize(words[i]));
        }
        return result.toString();
    }

    // Dich vu 2: giu nguyen thu tu tu, chi dao ky tu trong tung tu.
    static String reverseEachWord(String text) {
        if (text.trim().isEmpty()) return "";
        String[] words = text.trim().split("\\s+");
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < words.length; i++) {
            if (i > 0) result.append(" ");
            result.append(reverseAndCapitalize(words[i]));
        }
        return result.toString();
    }

    static String reverseAndCapitalize(String word) {
        String reversed = new StringBuilder(word.toLowerCase(Locale.ROOT)).reverse().toString();
        return reversed.substring(0, 1).toUpperCase(Locale.ROOT) + reversed.substring(1);
    }
}
