import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientTCP {
    public static void main(String[] args) throws Exception {
        // Dia chi server: chay tren cung may thi dung 127.0.0.1.
        //Cau 2
        String ip = "127.0.0.1";
        int port = 7000;

        try (
            Socket socket = new Socket(ip, port); // Ket noi TCP den server.
            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
            PrintWriter out = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true);
            Scanner sc = new Scanner(System.in, "UTF-8")
        ) {
            System.out.println("Da ket noi TCP den server.");

            while (true) {
                // Nhan menu dich vu tu server.
                String menu = in.readLine();
                if (menu == null) break;
                System.out.println("\n" + menu);
                System.out.print("Nhap dich vu hoac EXIT. de thoat: ");

                String choice = sc.nextLine().trim();
                if (choice.equalsIgnoreCase("EXIT.") || choice.equalsIgnoreCase("EXIT")) {
                    out.println("EXIT."); // Gui dung chuoi EXIT. theo yeu cau de bai.
                    System.out.println(in.readLine());
                    break;
                }

                out.println(choice); // Gui so dich vu cho server.
                String reply = in.readLine();
                System.out.println(reply);
                if (reply == null) break;
                if (reply.startsWith("ERROR")) continue; // Chon lai neu server bao loi.

                int service = Integer.parseInt(choice);
                System.out.println("Nhap noi dung tung dong. Nhap mot dau cham (.) de ket thuc.");

                while (true) {
                    String line = sc.nextLine();
                    out.println(line); // Gui mot dong sang server.

                    if (line.trim().equals(".")) {
                        // Dau cham ket thuc; server gui ket qua cuoi cung.
                        System.out.println(in.readLine());
                        break;
                    }

                    // Dich vu 3 chi tra ket qua tong khi gap dau cham.
                    // Cac dich vu 1, 2, 4 tra ket qua cho tung dong.
                    if (service != 3) {
                        System.out.println(in.readLine());
                    }
                }
            }
        }
    }
}
