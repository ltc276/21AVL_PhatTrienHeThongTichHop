import java.net.*;
import java.io.*;

public class UDPClient {

    public static void main(String[] args) throws Exception {

        DatagramSocket client =
                new DatagramSocket();

        InetAddress address =
                InetAddress.getByName("localhost");

        BufferedReader keyboard =
                new BufferedReader(
                        new InputStreamReader(System.in));

        while (true) {

            System.out.print("Nhap lenh: ");

            String line = keyboard.readLine();

            // Gui lenh
            byte[] data = line.getBytes();

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length,
                            address,
                            5001);

            client.send(packet);

            // Nhan ket qua
            byte[] result = new byte[1024];

            DatagramPacket resultPacket =
                    new DatagramPacket(
                            result,
                            result.length);

            client.receive(resultPacket);

            String text = new String(
                    resultPacket.getData(),
                    resultPacket.getOffset(),
                    resultPacket.getLength());

            System.out.println("Server: " + text);

            if (line.equals("QUIT")) {
                break;
            }
        }

        client.close();
    }
}