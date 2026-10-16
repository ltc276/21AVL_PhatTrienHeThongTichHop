// Bai vi du 4.3: TCP server dung thread pool de phuc vu nhieu client.
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiClientTcpServer {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(20);
        try (ServerSocket server = new ServerSocket(5000)) {
            System.out.println("TCP server nhieu client dang nghe cong 5000");
            while (true) {
                Socket socket = server.accept();
                pool.submit(() -> {
                    try (socket) {
                        System.out.println("Client ket noi: " + socket.getRemoteSocketAddress());
                        TcpCommandServer.serve(socket);
                    } catch (IOException e) {
                        System.out.println("Loi client: " + e.getMessage());
                    }
                });
            }
        } catch (IOException e) {
            System.out.println("Loi server: " + e.getMessage());
        } finally {
            pool.shutdown();
        }
    }
}
