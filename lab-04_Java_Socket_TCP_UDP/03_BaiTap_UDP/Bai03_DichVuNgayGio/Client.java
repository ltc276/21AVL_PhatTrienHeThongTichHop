// Bai 3 UDP: client gui tung lenh bang datagram, moi lenh la mot lan request/response.
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.io.*;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket socket = new DatagramSocket(); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            socket.setSoTimeout(3000); InetAddress address = InetAddress.getByName(host); System.out.println("Nhap DATE, TIME, DATETIME; QUIT de dung client.");
            String cmd; while ((cmd = kb.readLine()) != null && !cmd.equalsIgnoreCase("QUIT")) {
                byte[] data = cmd.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(data, data.length, address, 5203));
                byte[] buf = new byte[1024]; DatagramPacket p = new DatagramPacket(buf, buf.length);
                try { socket.receive(p); System.out.println(new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8)); }
                catch (SocketTimeoutException e) { System.out.println("Timeout: khong co phan hoi"); }
            }
        } catch (Exception e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
