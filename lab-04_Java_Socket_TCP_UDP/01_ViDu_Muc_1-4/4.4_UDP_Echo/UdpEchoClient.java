// Bai vi du 4.4: UDP client gui mot thong diep va cho phan hoi toi da 3 giay.
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UdpEchoClient {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5001;
        String message = args.length > 2 ? args[2] : "xin chao UDP";
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(3000);
            InetAddress address = InetAddress.getByName(host);
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            socket.send(new DatagramPacket(data, data.length, address, port));
            byte[] buffer = new byte[4096];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            socket.receive(response);
            System.out.println("Server: " + new String(response.getData(), response.getOffset(), response.getLength(), StandardCharsets.UTF_8));
        } catch (SocketTimeoutException e) {
            System.out.println("Het 3 giay nhung chua nhan phan hoi");
        } catch (Exception e) {
            System.out.println("Loi UDP client: " + e.getMessage());
        }
    }
}
