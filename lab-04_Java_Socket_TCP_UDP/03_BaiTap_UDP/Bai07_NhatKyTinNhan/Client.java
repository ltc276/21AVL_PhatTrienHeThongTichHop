// Bai 7 UDP: client gui HELLO|id, MSG|noi dung va QUIT.
import java.net.*; import java.nio.charset.StandardCharsets; import java.io.*;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket socket = new DatagramSocket(); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            socket.setSoTimeout(3000); InetAddress ip = InetAddress.getByName(host); System.out.print("Nhap clientId: "); send(socket, ip, "HELLO|" + kb.readLine()); System.out.println(receive(socket));
            String line; System.out.println("Nhap tin nhan; QUIT de thoat."); while ((line = kb.readLine()) != null) { if (line.equals("QUIT")) { send(socket, ip, "QUIT"); System.out.println(receive(socket)); break; } send(socket, ip, "MSG|" + line); System.out.println(receive(socket)); }
        } catch (Exception e) { System.out.println("Loi UDP client: " + e.getMessage()); }
    }
    static void send(DatagramSocket s, InetAddress ip, String msg) throws Exception { byte[] b = msg.getBytes(StandardCharsets.UTF_8); s.send(new DatagramPacket(b,b.length,ip,5207)); }
    static String receive(DatagramSocket s) throws Exception { byte[] b = new byte[4096]; DatagramPacket p = new DatagramPacket(b,b.length); s.receive(p); return new String(p.getData(),p.getOffset(),p.getLength(),StandardCharsets.UTF_8); }
}
