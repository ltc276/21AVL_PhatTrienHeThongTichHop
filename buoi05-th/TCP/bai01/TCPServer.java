import java.net.*;
import java.io.*;

public class TCPServer {
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);
        System.out.println("TCP Server dang chay...");

        Socket socket = server.accept();
        System.out.println("Client da ket noi!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

        // Nhan hostname va URI rieng
        String hostname = in.readLine();
        String uriString = in.readLine();

        out.println("===== HOST =====");
        out.println("Hostname: " + hostname);

        // Xu ly hostname
        try {
            InetAddress[] list = InetAddress.getAllByName(hostname);

            for (InetAddress ip : list) {
                out.println("IP: " + ip.getHostAddress());

                if (ip instanceof Inet4Address) {
                    out.println("Loai: IPv4");
                } else {
                    out.println("Loai: IPv6");
                }

                out.println("Loopback: " + ip.isLoopbackAddress());
                out.println("Site local: " + ip.isSiteLocalAddress());
            }

        } catch (UnknownHostException e) {
            out.println("Loi: Khong phan giai duoc hostname");
        }

        // Xu ly URI
        out.println();
        out.println("===== URI =====");

        try {
            URI uri = new URI(uriString);

            out.println("Scheme: " + uri.getScheme());
            out.println("Host: " + uri.getHost());
            out.println("Port: " + uri.getPort());
            out.println("Path: " + uri.getPath());
            out.println("Query: " + uri.getQuery());
            out.println("Fragment: " + uri.getFragment());

        } catch (URISyntaxException e) {
            out.println("Loi: URI khong hop le");
        }

        socket.close();
        server.close();
    }
}
