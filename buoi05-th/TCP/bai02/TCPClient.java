import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPClient {

    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost", 5000);

        BufferedReader keyboard = new BufferedReader(
                new InputStreamReader(
                        System.in,
                        StandardCharsets.UTF_8));

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8));

        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8),
                true);

        while (true) {

            System.out.print("Nhap so: ");

            String data = keyboard.readLine();

            // Gui du lieu cho Server
            out.println(data);

            // Neu nhap QUIT thi dung
            if (data.equals("QUIT")) {
                break;
            }

            // Nhan ket qua
            String result = in.readLine();

            System.out.println("Server: " + result);
        }

        socket.close();
    }
}