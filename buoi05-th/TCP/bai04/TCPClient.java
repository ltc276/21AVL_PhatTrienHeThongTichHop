import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        BufferedReader keyboard =
                new BufferedReader(
                        new InputStreamReader(System.in));

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

        PrintWriter out =
                new PrintWriter(
                        socket.getOutputStream(), true);

        while (true) {

            System.out.print("Nhap lenh: ");

            String line = keyboard.readLine();

            out.println(line);

            String result = in.readLine();

            System.out.println("Server: " + result);

            // Go QUIT de thoat client
            if (line.equals("QUIT")) {
                break;
            }
        }

        socket.close();
    }
}