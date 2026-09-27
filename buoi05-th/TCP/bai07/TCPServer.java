import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.time.*;
import java.time.format.*;

public class TCPServer {

    static final int PORT = 6000;

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(PORT);

        System.out.println("TCP Log Server dang chay...");

        while (true) {

            Socket socket = server.accept();

            new Thread(() -> xuLyClient(socket)).start();
        }
    }

    static void xuLyClient(Socket socket) {

        String clientId = null;

        try {

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

            // Nhan HELLO
            String hello = in.readLine();

            if (hello == null ||
                    !hello.startsWith("HELLO ")) {

                out.println("ERR INVALID_HELLO");
                socket.close();
                return;
            }

            clientId = hello.substring(6).trim();

            // Kiem tra clientId
            if (!clientId.matches("[A-Za-z0-9_-]+")) {

                out.println("ERR INVALID_CLIENT_ID");
                socket.close();
                return;
            }

            out.println("OK");

            System.out.println(
                    "Client " + clientId +
                    " ket noi: " +
                    socket.getRemoteSocketAddress()
            );

            String message;

            while ((message = in.readLine()) != null) {

                if (message.equals("QUIT")) {

                    out.println("BYE");
                    break;
                }

                ghiLog(
                        clientId,
                        socket.getRemoteSocketAddress().toString(),
                        message
                );

                out.println("SAVED");
            }

        } catch (Exception e) {

            System.out.println(
                    "Client ngat ket noi."
            );

        } finally {

            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }

    static void ghiLog(
            String clientId,
            String remote,
            String message) throws Exception {

        Path folder =
                Paths.get("data", "logs");

        Files.createDirectories(folder);

        Path file =
                folder.resolve(
                        clientId + ".txt"
                );

        String time =
                LocalDateTime.now().format(
                        DateTimeFormatter.ofPattern(
                                "yyyy-MM-dd HH:mm:ss"
                        )
                );

        String line =
                time +
                " | " +
                remote +
                " | " +
                message +
                System.lineSeparator();

        Files.write(
                file,
                line.getBytes("UTF-8"),
                StandardOpenOption.CREATE,
                StandardOpenOption.APPEND
        );
    }
}