import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        Socket socket =
                new Socket("localhost", 6000);

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(),
                                "UTF-8"
                        )
                );

        PrintWriter out =
                new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(),
                                "UTF-8"
                        ),
                        true
                );

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

        out.println(
                "HELLO " + clientId
        );

        System.out.println(
                "Server: " + in.readLine()
        );

        while (true) {

            System.out.print("Nhap tin nhan: ");

            String message =
                    keyboard.readLine();

            out.println(message);

            String result =
                    in.readLine();

            System.out.println(
                    "Server: " + result
            );

            if (message.equals("QUIT")) {
                break;
            }
        }

        socket.close();
    }
}