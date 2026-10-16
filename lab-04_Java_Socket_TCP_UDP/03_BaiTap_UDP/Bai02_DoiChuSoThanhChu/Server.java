// Bai 2 UDP: moi datagram chua mot chu so 0-9 hoac du lieu can kiem tra.
import java.net.*;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) {
        String[] chu = {"khong", "mot", "hai", "ba", "bon", "nam", "sau", "bay", "tam", "chin"};
        try (DatagramSocket socket = new DatagramSocket(5202)) {
            System.out.println("UDP server doi chu so dang nghe cong 5202");
            while (true) {
                byte[] buffer = new byte[2048];
                DatagramPacket p = new DatagramPacket(buffer, buffer.length);
                socket.receive(p);
                String s = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8);
                String result = s.length() == 1 && s.charAt(0) >= '0' && s.charAt(0) <= '9' ? "OK " + chu[s.charAt(0)-'0'] : "ERR INVALID_DIGIT";
                byte[] data = result.getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(data, data.length, p.getAddress(), p.getPort()));
            }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }
}
