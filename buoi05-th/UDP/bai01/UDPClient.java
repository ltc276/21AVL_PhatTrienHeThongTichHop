import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {

        if (args.length != 2) {
            System.out.println(
                "Cach dung: java UDPClient <hostname> <URI>"
            );
            return;
        }

        String hostname = args[0];
        String uri = args[1];

        DatagramSocket client =
                new DatagramSocket();

        InetAddress address =
                InetAddress.getByName("localhost");

        // Gui hostname
        byte[] data1 = hostname.getBytes();

        DatagramPacket packet1 =
                new DatagramPacket(
                        data1,
                        data1.length,
                        address,
                        5001);

        client.send(packet1);

        // Gui URI
        byte[] data2 = uri.getBytes();

        DatagramPacket packet2 =
                new DatagramPacket(
                        data2,
                        data2.length,
                        address,
                        5001);

        client.send(packet2);

        // Nhan ket qua
        byte[] result = new byte[4096];

        DatagramPacket resultPacket =
                new DatagramPacket(
                        result,
                        result.length);

        client.receive(resultPacket);

        String text = new String(
                resultPacket.getData(),
                0,
                resultPacket.getLength());

        System.out.println(text);

        client.close();
    }
}