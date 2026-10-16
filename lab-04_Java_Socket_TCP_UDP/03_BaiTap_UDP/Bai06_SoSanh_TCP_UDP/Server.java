// Bai 6 UDP: server tra lai tung datagram kem ACK.
import java.net.*; import java.nio.charset.StandardCharsets;
public class Server {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5206)) {
            System.out.println("UDP benchmark server cong 5206");
            while (true) { byte[] b = new byte[2048]; DatagramPacket p = new DatagramPacket(b, b.length); socket.receive(p);
                String msg = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); byte[] data = ("ACK|" + msg).getBytes(StandardCharsets.UTF_8);
                socket.send(new DatagramPacket(data, data.length, p.getAddress(), p.getPort())); }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }
}
