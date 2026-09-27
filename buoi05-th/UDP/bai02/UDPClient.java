import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket client = new DatagramSocket();

        InetAddress serverAddress =
                InetAddress.getByName("localhost");

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.print("Nhap so (0-9) hoac QUIT: ");
            String input = sc.nextLine();

            byte[] data =
                    input.getBytes(StandardCharsets.UTF_8);

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length,
                            serverAddress,
                            5001
                    );

            client.send(packet);

            if (input.equals("QUIT")) {
                break;
            }

            byte[] buffer = new byte[1024];

            DatagramPacket resultPacket =
                    new DatagramPacket(
                            buffer,
                            buffer.length
                    );

            client.receive(resultPacket);

            String result = new String(
                    resultPacket.getData(),
                    resultPacket.getOffset(),
                    resultPacket.getLength(),
                    StandardCharsets.UTF_8
            );

            System.out.println("Server: " + result);
        }

        client.close();
    }
}