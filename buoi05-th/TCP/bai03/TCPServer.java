import java.io.*;
import java.net.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("TCP Server dang chay...");

        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        // Dinh dang ngay
        DateTimeFormatter dateFormat =
                DateTimeFormatter.ofPattern("dd MM yyyy");

        // Dinh dang gio
        DateTimeFormatter timeFormat =
                DateTimeFormatter.ofPattern("HH mm ss");

        String command;

        while ((command = in.readLine()) != null) {

            if (command.equals("DATE")) {

                LocalDateTime now = LocalDateTime.now();

                out.println(now.format(dateFormat));

            } else if (command.equals("TIME")) {

                LocalDateTime now = LocalDateTime.now();

                out.println(now.format(timeFormat));

            } else if (command.equals("DATETIME")) {

                LocalDateTime now = LocalDateTime.now();

                out.println(
                    now.format(dateFormat)
                    + " "
                    + now.format(timeFormat)
                );

            } else if (command.equals("QUIT")) {

                out.println("BYE");
                break;

            } else {

                out.println("ERR INVALID_COMMAND");
            }
        }

        socket.close();
        server.close();

        System.out.println("TCP Server ket thuc.");
    }
}