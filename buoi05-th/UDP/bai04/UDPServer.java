import java.net.*;

public class UDPServer {

    public static void main(String[] args) throws Exception {

        DatagramSocket server =
                new DatagramSocket(5001);

        System.out.println("UDP Server dang chay...");

        while (true) {

            // Nhan du lieu
            byte[] data = new byte[1024];

            DatagramPacket packet =
                    new DatagramPacket(
                            data,
                            data.length);

            server.receive(packet);

            String line = new String(
                    packet.getData(),
                    packet.getOffset(),
                    packet.getLength());

            String result = tinhToan(line);

            // Gui ket qua
            byte[] resultData = result.getBytes();

            DatagramPacket resultPacket =
                    new DatagramPacket(
                            resultData,
                            resultData.length,
                            packet.getAddress(),
                            packet.getPort());

            server.send(resultPacket);
        }
    }

    public static String tinhToan(String line) {

        String[] parts = line.split(" ");

        // Kiem tra dinh dang
        if (parts.length != 4) {
            return "ERR INVALID_FORMAT";
        }

        if (!parts[0].equals("CALC")) {
            return "ERR INVALID_FORMAT";
        }

        String operator = parts[1];

        double a;
        double b;

        // Chuyen chuoi thanh so
        try {

            a = Double.parseDouble(parts[2]);
            b = Double.parseDouble(parts[3]);

        } catch (NumberFormatException e) {

            return "ERR INVALID_NUMBER";
        }

        double result;

        if (operator.equals("+")) {

            result = a + b;

        } else if (operator.equals("-")) {

            result = a - b;

        } else if (operator.equals("*")) {

            result = a * b;

        } else if (operator.equals("/")) {

            if (b == 0) {
                return "ERR DIVIDE_BY_ZERO";
            }

            result = a / b;

        } else {

            return "ERR UNSUPPORTED_OPERATOR";
        }

        if (result == (long) result) {
            return "OK " + (long) result;
        }

        return "OK " + result;
    }
}
