
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 5000);

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            // Nhap ten
            System.out.print("Nhap ten cua ban: ");
            String name = sc.nextLine();
            out.println(name);

            // Luong nhan tin nhan tu server
            Thread receive = new Thread(() -> {
                try {
                    String message;

                    while ((message = in.readLine()) != null) {
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    // Ket thuc khi mat ket noi
                }
            });

            receive.start();

            // Gui tin nhan den server
            while (sc.hasNextLine()) {
                String message = sc.nextLine();

                out.println(message);

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

            socket.close();
            sc.close();

        } catch (IOException e) {
            System.out.println("Khong ket noi duoc server!");
        }
    }
}
