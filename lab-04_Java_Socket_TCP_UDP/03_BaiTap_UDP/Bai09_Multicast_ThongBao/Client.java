// Bai 9 UDP: sender gui mot thong bao den multicast group 239.255.0.1.
import java.net.*; import java.nio.charset.StandardCharsets; import java.io.*;
public class Client {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            InetAddress group = InetAddress.getByName("239.255.0.1"); System.out.println("Nhap thong bao; QUIT de ket thuc receiver."); String line;
            while ((line = kb.readLine()) != null) { byte[] b = line.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(b, b.length, group, 5209)); if (line.equalsIgnoreCase("QUIT")) break; }
        } catch (Exception e) { System.out.println("Loi sender: " + e.getMessage()); }
    }
}
