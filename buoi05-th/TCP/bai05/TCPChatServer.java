import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class TCPChatServer {

    static final int PORT = 5000;

    static ConcurrentHashMap<String, Client> clients =
            new ConcurrentHashMap<>();

    static ExecutorService pool =
            Executors.newFixedThreadPool(10);

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(PORT);

        System.out.println("TCP Server dang chay...");

        while (true) {

            Socket socket = server.accept();

            pool.execute(new Client(socket));
        }
    }

    static void sendAll(String message, String except) {

        for (Client c : clients.values()) {

            if (!c.name.equals(except)) {
                c.send(message);
            }
        }
    }

    static class Client implements Runnable {

        Socket socket;
        BufferedReader in;
        PrintWriter out;
        String name;

        Client(Socket socket) {
            this.socket = socket;
        }

        void send(String message) {
            out.println(message);
        }

        public void run() {

            try {

                in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));

                out = new PrintWriter(
                        socket.getOutputStream(), true);

                out.println("Nhap nickname:");

                while (true) {

                    name = in.readLine();

                    if (name == null) {
                        return;
                    }

                    if (name.isEmpty()) {
                        out.println("Nickname rong");
                    }
                    else if (clients.putIfAbsent(name, this) == null) {
                        break;
                    }
                    else {
                        out.println("Nickname da ton tai");
                    }
                }

                out.println("Dang nhap thanh cong");
                sendAll(name + " da vao phong", name);

                String message;

                while ((message = in.readLine()) != null) {

                    if (message.equals("USERS")) {

                        out.println(
                                "USERS: " +
                                String.join(", ", clients.keySet())
                        );
                    }

                    else if (message.startsWith("MSG ")) {

                        String text = message.substring(4);

                        sendAll(name + ": " + text, name);
                    }

                    else if (message.equals("QUIT")) {

                        break;
                    }

                    else {

                        out.println(
                                "Dung: USERS | MSG noi_dung | QUIT");
                    }
                }

            } catch (Exception e) {

            } finally {

                if (name != null) {

                    clients.remove(name);

                    sendAll(name + " da roi phong", name);
                }

                try {
                    socket.close();
                } catch (Exception e) {
                }
            }
        }
    }
}