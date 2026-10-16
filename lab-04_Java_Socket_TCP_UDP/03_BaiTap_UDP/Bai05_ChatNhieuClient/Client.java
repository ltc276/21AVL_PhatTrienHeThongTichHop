// Bai 5 UDP: client gui JOIN, USERS, MSG|noi dung va QUIT bang datagram.
import java.net.*; import java.nio.charset.StandardCharsets; import java.io.*;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket socket = new DatagramSocket(); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            socket.setSoTimeout(3000); InetAddress ip = InetAddress.getByName(host); System.out.print("Nhap nickname: "); String nick = kb.readLine(); send(socket, ip, "JOIN|" + nick); String answer = receive(socket); System.out.println(answer); if (!answer.startsWith("OK JOINED")) return;
            Thread listener = new Thread(() -> { try { while (!socket.isClosed()) { byte[] b = new byte[4096]; DatagramPacket p = new DatagramPacket(b, b.length); socket.receive(p); System.out.println(new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8)); } } catch (Exception e) { } }); listener.setDaemon(true); listener.start();
            System.out.println("Lenh: USERS, MSG noi dung, QUIT"); String line; while ((line = kb.readLine()) != null) { if (line.equalsIgnoreCase("USERS")) send(socket, ip, line); else if (line.equalsIgnoreCase("QUIT")) { send(socket, ip, "QUIT"); break; } else if (line.startsWith("MSG ")) send(socket, ip, "MSG|" + line.substring(4)); else send(socket, ip, line); }
        } catch (Exception e) { System.out.println("Loi UDP client: " + e.getMessage()); }
    }
    static void send(DatagramSocket s, InetAddress ip, String text) throws Exception { byte[] b = text.getBytes(StandardCharsets.UTF_8); s.send(new DatagramPacket(b, b.length, ip, 5205)); }
    static String receive(DatagramSocket s) throws Exception { byte[] b = new byte[4096]; DatagramPacket p = new DatagramPacket(b, b.length); s.receive(p); return new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); }
}
