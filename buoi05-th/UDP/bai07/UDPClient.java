import java.net.*;
import java.io.*;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket client =
                new DatagramSocket();

        InetAddress server =
                InetAddress.getByName("localhost");

        BufferedReader keyboard =
                new BufferedReader(
                        new InputStreamReader(
                                System.in,
                                "UTF-8"
                        )
                );

        System.out.print("Nhap clientId: ");

        String clientId =
                keyboard.readLine();

        send(
                client,
                server,
                "HELLO " + clientId
        );

        System.out.println(
                "Server: " + receive(client)
        );

        while (true) {

            System.out.print("Nhap tin nhan: ");

            String message =
                    keyboard.readLine();

            send(
                    client,
                    server,
                    message
            );

            System.out.println(
                    "Server: " +
                    receive(client)
            );

            if (message.equals("QUIT")) {
                break;
            }
        }

        client.close();
    }

    static void send(
            DatagramSocket client,
            InetAddress server,
            String message) throws Exception {

        byte[] data =
                message.getBytes("UTF-8");

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length,
                        server,
                        6001
                );

        client.send(packet);
    }

    static String receive(
            DatagramSocket client)
            throws Exception {

        byte[] data = new byte[1024];

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length
                );

        client.receive(packet);

        return new String(
                packet.getData(),
                packet.getOffset(),
                packet.getLength(),
                "UTF-8"
        );
    }
}