import java.net.*;

public class UDPServer {

    static final int PORT = 5003;

    public static void main(String[] args) throws Exception {

        DatagramSocket server =
                new DatagramSocket(PORT);

        System.out.println("UDP Benchmark Server dang chay...");

        while (true) {

            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length
                    );

            server.receive(packet);

            DatagramPacket result =
                    new DatagramPacket(
                            packet.getData(),
                            packet.getLength(),
                            packet.getAddress(),
                            packet.getPort()
                    );

            server.send(result);
        }
    }
}