import java.net.*;
import java.util.Scanner;

public class UDPChatClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket client =
                new DatagramSocket();

        InetAddress server =
                InetAddress.getByName("localhost");

        Scanner sc =
                new Scanner(System.in);

        System.out.print("Nhap nickname: ");

        String name = sc.nextLine();

        send(
                client,
                server,
                "LOGIN " + name
        );

        receive(client);

        while (true) {

            String message =
                    sc.nextLine();

            send(
                    client,
                    server,
                    message
            );

            if (message.equals("QUIT")) {
                break;
            }

            if (message.equals("USERS")) {

                receive(client);
            }
        }

        client.close();
    }

    static void send(
            DatagramSocket client,
            InetAddress server,
            String message) throws Exception {

        byte[] data =
                message.getBytes();

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length,
                        server,
                        5001
                );

        client.send(packet);
    }

    static void receive(
            DatagramSocket client) throws Exception {

        byte[] data = new byte[1024];

        DatagramPacket packet =
                new DatagramPacket(
                        data,
                        data.length
                );

        client.receive(packet);

        String message =
                new String(
                        packet.getData(),
                        0,
                        packet.getLength()
                );

        System.out.println(message);
    }
}