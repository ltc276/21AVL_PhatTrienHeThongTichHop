import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UDPServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket server =
                new DatagramSocket(5001);

        System.out.println("UDP Server dang chay...");

        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("dd MM yyyy");

        DateTimeFormatter timeFormat =
                DateTimeFormatter.ofPattern("HH mm ss");

        while (true) {

            // Nhan lenh
            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length);

            server.receive(packet);

            String command = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength());

            String result;

            if (command.equals("DATE")) {

                LocalDateTime now =
                        LocalDateTime.now();

                result = now.format(dateFormat);

            } else if (command.equals("TIME")) {

                LocalDateTime now =
                        LocalDateTime.now();

                result = now.format(timeFormat);

            } else if (command.equals("DATETIME")) {

                LocalDateTime now =
                        LocalDateTime.now();

                result =
                    now.format(dateFormat)
                    + " "
                    + now.format(timeFormat);

            } else {

                result = "ERR INVALID_COMMAND";
            }

            // Gui ket qua
            byte[] resultData =
                    result.getBytes();

            DatagramPacket resultPacket =
                    new DatagramPacket(
                            resultData,
                            resultData.length,
                            packet.getAddress(),
                            packet.getPort());

            server.send(resultPacket);
        }
    }
}
