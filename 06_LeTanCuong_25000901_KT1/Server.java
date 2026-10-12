
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class Server {
    static ArrayList<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(5000);
            System.out.println("Server dang chay tai cong 5000...");

            while (true) {
                Socket socket = server.accept();
                new ClientHandler(socket).start();
            }
        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
        }
    }

    // Them client
    static synchronized void addClient(ClientHandler c) {
        clients.add(c);
    }

    // Xoa client
    static synchronized void removeClient(ClientHandler c) {
        clients.remove(c);
    }

    // Gui tin nhan den client trong cung phong
    static synchronized void sendToRoom(
            String room, String message, ClientHandler sender) {

        for (ClientHandler c : clients) {
            if (c != sender && room.equals(c.room)) {
                c.out.println(message);
            }
        }
    }
}

class ClientHandler extends Thread {
    Socket socket;
    BufferedReader in;
    PrintWriter out;

    String name;
    volatile String room = "";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(
                socket.getOutputStream(), true);

            // Nhan ten client
            name = in.readLine();

            if (name == null) {
                return;
            }

            Server.addClient(this);

            boolean exit = false;

            while (!exit) {
                // Hien thi danh sach phong
                out.println("\n===== DANH SACH PHONG =====");
                out.println("Study");
                out.println("Game");
                out.println("TraiNghiem");
                out.println("Nhap ten phong hoac /exit:");

                String choice = in.readLine();

                if (choice == null || choice.equalsIgnoreCase("/exit")) {
                    break;
                }

                // Kiem tra phong
                String selectedRoom = checkRoom(choice);

                if (selectedRoom == null) {
                    out.println("Phong khong hop le! Vui long chon lai.");
                    continue;
                }

                room = selectedRoom;

                out.println("Ban da vao phong " + room);
                out.println("Nhap tin nhan de chat.");
                out.println("/quit: roi phong | /exit: thoat");

                Server.sendToRoom(
                    room, name + " da vao phong.", this);

                // Chat trong phong
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

                    Server.sendToRoom(
                        room, name + ": " + message, this);
                }
            }

        } catch (IOException e) {
            System.out.println(name + " da ngat ket noi.");
        } finally {
            if (!room.isEmpty()) {
                Server.sendToRoom(
                    room, name + " da roi phong.", this);

                room = "";
            }

            Server.removeClient(this);

            try {
                socket.close();
            } catch (IOException e) {
                // Dong ket noi
            }

            System.out.println(name + " da thoat.");
        }
    }

    // Kiem tra ten phong
    String checkRoom(String choice) {
        if (choice.equalsIgnoreCase("Study")) {
            return "Study";
        }

        if (choice.equalsIgnoreCase("Game")) {
            return "Game";
        }

        if (choice.equalsIgnoreCase("TraiNghiem")) {
            return "TraiNghiem";
        }

        return null;
    }
}
