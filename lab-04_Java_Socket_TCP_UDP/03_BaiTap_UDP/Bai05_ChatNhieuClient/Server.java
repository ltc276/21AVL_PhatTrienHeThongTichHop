// Bai 5 UDP: chat datagram, quan ly client theo dia chi IP va port.
import java.net.*; import java.nio.charset.StandardCharsets; import java.util.*;
public class Server {
    static final Map<String, String> clients = new LinkedHashMap<>();
    static final Map<String, InetSocketAddress> address = new HashMap<>();
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5205)) {
            System.out.println("UDP chat server dang nghe cong 5205");
            while (true) {
                byte[] buf = new byte[4096]; DatagramPacket p = new DatagramPacket(buf, buf.length); socket.receive(p);
                String key = p.getAddress().getHostAddress() + ":" + p.getPort();
                String req = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8);
                String reply = "ERR UNKNOWN_COMMAND";
                if (req.startsWith("JOIN|")) {
                    String nick = req.substring(5).trim();
                    if (nick.isEmpty() || clients.containsValue(nick)) reply = "ERR NICKNAME_TAKEN_OR_EMPTY";
                    else { clients.put(key, nick); address.put(key, new InetSocketAddress(p.getAddress(), p.getPort())); reply = "OK JOINED " + nick; broadcast(socket, nick + " da vao chat.", key); }
                } else if (req.equals("USERS")) reply = "USERS " + String.join(", ", clients.values());
                else if (req.startsWith("MSG|")) { String nick = clients.get(key); if (nick == null) reply = "ERR JOIN_FIRST"; else { broadcast(socket, nick + ": " + req.substring(4), key); reply = "OK SENT"; } }
                else if (req.equals("QUIT")) { String nick = clients.remove(key); address.remove(key); reply = "OK BYE"; if (nick != null) broadcast(socket, nick + " da roi chat.", key); }
                send(socket, p.getAddress(), p.getPort(), reply);
            }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }
    static void send(DatagramSocket s, InetAddress ip, int port, String text) throws Exception { byte[] b = text.getBytes(StandardCharsets.UTF_8); s.send(new DatagramPacket(b, b.length, ip, port)); }
    static void broadcast(DatagramSocket s, String text, String skip) throws Exception { for (String key : address.keySet()) if (!key.equals(skip)) { InetSocketAddress a = address.get(key); send(s, a.getAddress(), a.getPort(), text); } }
}
