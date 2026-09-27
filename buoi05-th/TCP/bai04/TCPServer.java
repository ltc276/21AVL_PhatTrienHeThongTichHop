import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("TCP Server dang chay...");

        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        String line;

        while ((line = in.readLine()) != null) {

            String[] parts = line.split(" ");

            // Kiem tra du 4 thanh phan
            if (parts.length != 4) {
                out.println("ERR INVALID_FORMAT");
                continue;
            }

            // Phai bat dau bang CALC
            if (!parts[0].equals("CALC")) {
                out.println("ERR INVALID_FORMAT");
                continue;
            }

            String operator = parts[1];
            double a;
            double b;

            // Chuyen 2 toan hang sang so
            try {
                a = Double.parseDouble(parts[2]);
                b = Double.parseDouble(parts[3]);
            } catch (NumberFormatException e) {
                out.println("ERR INVALID_NUMBER");
                continue;
            }

            double result;

            // Thuc hien phep tinh
            if (operator.equals("+")) {

                result = a + b;

            } else if (operator.equals("-")) {

                result = a - b;

            } else if (operator.equals("*")) {

                result = a * b;

            } else if (operator.equals("/")) {

                if (b == 0) {
                    out.println("ERR DIVIDE_BY_ZERO");
                    continue;
                }

                result = a / b;

            } else {

                out.println("ERR UNSUPPORTED_OPERATOR");
                continue;
            }

            // Neu ket qua la so nguyen
            if (result == (long) result) {
                out.println("OK " + (long) result);
            } else {
                out.println("OK " + result);
            }
        }

        socket.close();
        server.close();
    }
}