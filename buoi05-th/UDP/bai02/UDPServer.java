import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket server = new DatagramSocket(5001);

        System.out.println("UDP Server dang chay...");

        while (true) {

            // Nhan du lieu
            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(data, data.length);

            server.receive(packet);

            String input = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength(),
                    StandardCharsets.UTF_8);

            // QUIT
            if (input.equals("QUIT")) {
                break;
            }

            String result;

            if (input.equals("0")) {
                result = "khong";
            } else if (input.equals("1")) {
                result = "mot";
            } else if (input.equals("2")) {
                result = "hai";
            } else if (input.equals("3")) {
                result = "ba";
            } else if (input.equals("4")) {
                result = "bon";
            } else if (input.equals("5")) {
                result = "nam";
            } else if (input.equals("6")) {
                result = "sau";
            } else if (input.equals("7")) {
                result = "bay";
            } else if (input.equals("8")) {
                result = "tam";
            } else if (input.equals("9")) {
                result = "chin";
            } else {
                result = "ERR INVALID_DIGIT";
            }

            // Gui ket qua
            byte[] resultData =
                    result.getBytes(StandardCharsets.UTF_8);

            DatagramPacket resultPacket =
                    new DatagramPacket(
                            resultData,
                            resultData.length,
                            packet.getAddress(),
                            packet.getPort());

            server.send(resultPacket);
        }

        server.close();

        System.out.println("UDP Server ket thuc.");
    }
}