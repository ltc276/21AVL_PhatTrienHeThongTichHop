// Bai vi du 4.4: UDP server nhan mot goi va tra ve chuoi da viet hoa.
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class UdpEchoServer {
    public static void main(String[] args) {
        byte[] buffer = new byte[4096];
        try (DatagramSocket socket = new DatagramSocket(5001)) {
            System.out.println("UDP server dang nghe cong 5001");
            while (true) {
                DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                socket.receive(request);
                String text = new String(request.getData(), request.getOffset(), request.getLength(), StandardCharsets.UTF_8);
                byte[] data = ("ACK " + text.toUpperCase(Locale.ROOT)).getBytes(StandardCharsets.UTF_8);
                DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
                socket.send(response);
            }
        } catch (Exception e) {
            System.out.println("Loi UDP server: " + e.getMessage());
        }
    }
}
