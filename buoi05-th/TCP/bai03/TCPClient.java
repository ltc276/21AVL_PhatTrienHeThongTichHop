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

        String command;

        while (true) {

            System.out.print("Nhap lenh: ");

            command = keyboard.readLine();

            // Gui lenh cho Server
            out.println(command);

            // Nhan ket qua
            String result = in.readLine();

            System.out.println("Server: " + result);

            // QUIT thi dung
            if (command.equals("QUIT")) {
                break;
            }
        }

        socket.close();
    }
}
