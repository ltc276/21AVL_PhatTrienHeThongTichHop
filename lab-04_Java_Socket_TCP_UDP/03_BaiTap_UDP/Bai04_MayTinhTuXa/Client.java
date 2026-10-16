// Bai 4 UDP: client gui lenh CALC rieng le, go QUIT de dung client.
import java.net.*; import java.nio.charset.StandardCharsets; import java.io.*;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket socket = new DatagramSocket(); BufferedReader kb = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8))) {
            socket.setSoTimeout(3000); InetAddress address = InetAddress.getByName(host); System.out.println("Vi du CALC + 100 200; go QUIT de thoat."); String cmd;
            while ((cmd = kb.readLine()) != null && !cmd.equalsIgnoreCase("QUIT")) { byte[] data = cmd.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(data, data.length, address, 5204));
                byte[] buf = new byte[2048]; DatagramPacket p = new DatagramPacket(buf, buf.length); try { socket.receive(p); System.out.println(new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8)); } catch (SocketTimeoutException e) { System.out.println("Timeout: khong co phan hoi"); } }
        } catch (Exception e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
