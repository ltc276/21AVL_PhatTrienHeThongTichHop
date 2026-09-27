import java.io.*;
import java.net.*;

public class TCPChatClient {

    public static void main(String[] args) throws Exception {

        Socket socket =
                new Socket("localhost", 5000);

        BufferedReader in =
                new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

        PrintWriter out =
                new PrintWriter(
                        socket.getOutputStream(), true);

        BufferedReader keyboard =
                new BufferedReader(
                        new InputStreamReader(
                                System.in));

        Thread reader = new Thread(() -> {

            try {

                String message;

                while ((message = in.readLine()) != null) {
                    System.out.println(message);
                }

            } catch (Exception e) {
            }
        });

        reader.start();

        while (true) {

            String message = keyboard.readLine();

            out.println(message);

            if (message.equals("QUIT")) {
                break;
            }
        }

        socket.close();
    }
}