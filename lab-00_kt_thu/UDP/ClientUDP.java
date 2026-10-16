import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ClientUDP {
    public static void main(String[] args) throws Exception {
        String ip = "127.0.0.1"; // IP server; dung 127.0.0.1 neu cung may.
        int port = 7000;
        InetAddress address = InetAddress.getByName(ip);

        try (DatagramSocket socket = new DatagramSocket();
             Scanner sc = new Scanner(System.in, "UTF-8")) {
            socket.setSoTimeout(30000); // Bao loi neu cho phan hoi qua lau.

            // UDP khong can bat tay ket noi; HELLO chi la goi tin khoi dong phien.
            send(socket, address, port, "HELLO");
            String menu = receive(socket);

            while (true) {
                System.out.println("\n" + menu);
                System.out.print("Nhap dich vu hoac EXIT. de thoat: ");
                String choice = sc.nextLine().trim();

                if (choice.equalsIgnoreCase("EXIT.") || choice.equalsIgnoreCase("EXIT")) {
                    send(socket, address, port, "EXIT."); // Gui dung chuoi EXIT.
                    System.out.println(receive(socket));
                    break;
                }

                // Bao server rang client muon chon dich vu nao.
                send(socket, address, port, "SERVICE:" + choice);
                String reply = receive(socket);
                System.out.println(reply);
                if (reply.startsWith("ERROR")) continue;

                int service = Integer.parseInt(choice);
                System.out.println("Nhap noi dung tung dong. Nhap mot dau cham (.) de ket thuc.");

                while (true) {
                    String line = sc.nextLine();
                    send(socket, address, port, line); // Moi dong la mot goi tin UDP.

                    if (line.trim().equals(".")) {
                        System.out.println(receive(socket)); // Nhan ket qua cuoi cung.
                        break;
                    }

                    // Dich vu 3 chi phan hoi sau dau cham; dich vu khac phan hoi tung dong.
                    if (service != 3) {
                        System.out.println(receive(socket));
                    }
                }
            }
        }
    }

    // Gui mot chuoi thanh mot goi tin UDP.
    static void send(DatagramSocket socket, InetAddress address, int port, String text) throws Exception {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
        socket.send(packet);
    }

    // Cho nhan mot goi tin UDP va chuyen bytes thanh chuoi.
    static String receive(DatagramSocket socket) throws Exception {
        byte[] buffer = new byte[4096];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
    }
}
