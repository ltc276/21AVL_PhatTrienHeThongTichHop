// Bai 10 UDP: server nhan DISCOVER_SERVICE bang UDP va cong bo cong TCP dich vu.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Server {
    static final int UDP_PORT = 5210, TCP_PORT = 5220;
    public static void main(String[] args) {
        Thread service = new Thread(() -> chayDichVu()); service.setDaemon(true); service.start();
        try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
            System.out.println("UDP discovery cong " + UDP_PORT + "; TCP service cong " + TCP_PORT);
            while (true) { byte[] b = new byte[1024]; DatagramPacket p = new DatagramPacket(b,b.length); socket.receive(p); String req = new String(p.getData(),p.getOffset(),p.getLength(),StandardCharsets.UTF_8); String response = req.equals("DISCOVER_SERVICE") ? "SERVICE EchoService " + TCP_PORT + " 1.0" : "ERR UNKNOWN_COMMAND"; byte[] out = response.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(out,out.length,p.getAddress(),p.getPort())); }
        } catch (Exception e) { System.out.println("Loi UDP discovery: " + e.getMessage()); }
    }
    static void chayDichVu() {
        try (ServerSocket server = new ServerSocket(TCP_PORT)) { while (true) try (Socket s = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) { String q = in.readLine(); out.println("PING".equals(q) ? "OK PONG" : "OK " + q); } }
        catch (IOException e) { System.out.println("Loi TCP service: " + e.getMessage()); }
    }
}
