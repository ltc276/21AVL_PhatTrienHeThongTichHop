import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {

    static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);

            System.out.println("Server dang chay tai cong 5000");

            while (true) {
                Socket socket = server.accept();
                new ClientHandler(socket).start();
            }

        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
        }
    }

    static synchronized void addClient(ClientHandler c) {
        clients.add(c);
    }

    static synchronized void removeClient(ClientHandler c) {
        clients.remove(c);
    }

    static synchronized void sendToRoom(
            String room, String message, ClientHandler sender) {

        for (ClientHandler c : clients) {
            if (c != sender && room.equals(c.room)) {
                c.out.println(message);
            }
        }
    }

    static class ClientHandler extends Thread {

        Socket socket;
        BufferedReader in;
        PrintWriter out;

        String name;
        volatile String room = "";

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream(), "UTF-8"));

                out = new PrintWriter(
                        new OutputStreamWriter(
                                socket.getOutputStream(), "UTF-8"), true);

                // Nhan ten client
                name = in.readLine();

                if (name == null || name.trim().isEmpty()) {
                    return;
                }

                name = name.trim();

                Server.addClient(this);

                boolean exit = false;

                while (!exit) {

                    // Hien thi danh sach phong
                    out.println("\n== Danh sach phong ==");
                    out.println("AmNhac");
                    out.println("HoiHoa");
                    out.println("TheThao");
                    out.println("Nhap ten phong hoac /exit de thoat");

                    String choice = in.readLine();

                    if (choice == null
                            || choice.equalsIgnoreCase("/exit")) {
                        break;
                    }

                    String selectedRoom = checkRoom(choice.trim());

                    if (selectedRoom == null) {
                        out.println(
                                "Phong khong hop le! Vui long chon lai.");
                        continue;
                    }

                    room = selectedRoom;

                    out.println("Ban da vao phong: " + room);
                    out.println("Nhap tin nhan de chat.");
                    out.println("/quit: Roi phong | /exit: Thoat chuong trinh");

                    Server.sendToRoom(
                            room, name + " da vao phong.", this);

                    // Vong lap chat trong phong
                    while (true) {
                        String message = in.readLine();

                        if (message == null) {
                            exit = true;
                            break;
                        }

                        if (message.equalsIgnoreCase("/quit")) {
                            Server.sendToRoom(
                                    room, name + " da roi phong.", this);

                            room = "";
                            break;
                        }

                        if (message.equalsIgnoreCase("/exit")) {
                            exit = true;
                            break;
                        }

                        if (message.trim().isEmpty()) {
                            continue;
                        }

                        Server.sendToRoom(
                                room, name + ": " + message, this);
                    }
                }

            } catch (IOException e) {
                System.out.println(
                        name + " da ngat ket noi: " + e.getMessage());

            } finally {
                if (!room.isEmpty()) {
                    Server.sendToRoom(
                            room, name + " da roi phong.", this);
                    room = "";
                }

                Server.removeClient(this);

                try {
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                    }
                } catch (IOException e) {
                    System.out.println(
                            "Loi dong ket noi: " + e.getMessage());
                }

                System.out.println(name + " da thoat.");
            }
        }

        String checkRoom(String choice) {
            if (choice.equalsIgnoreCase("AmNhac")) {
                return "AmNhac";
            }

            if (choice.equalsIgnoreCase("HoiHoa")) {
                return "HoiHoa";
            }

            if (choice.equalsIgnoreCase("TheThao")) {
                return "TheThao";
            }

            return null;
        }
    }
}