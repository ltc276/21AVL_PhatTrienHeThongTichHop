// Bai 10 UDP: phat hien server bang UDP, sau do noi TCP vao dia chi nguon cua phan hoi.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket ds = new DatagramSocket()) {
            ds.setSoTimeout(3000); InetAddress server = InetAddress.getByName(host); byte[] b = "DISCOVER_SERVICE".getBytes(StandardCharsets.UTF_8); ds.send(new DatagramPacket(b,b.length,server,5210));
            byte[] buf = new byte[1024]; DatagramPacket p = new DatagramPacket(buf,buf.length); ds.receive(p); String reply = new String(p.getData(),p.getOffset(),p.getLength(),StandardCharsets.UTF_8); System.out.println("Discovery: " + reply);
            String[] fields = reply.split("\\s+"); if (fields.length != 4 || !fields[0].equals("SERVICE")) return; int port = Integer.parseInt(fields[2]); String serviceHost = p.getAddress().getHostAddress();
            try (Socket s = new Socket(serviceHost,port); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(),StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(),StandardCharsets.UTF_8),true)) { out.println("PING"); System.out.println("Service: " + in.readLine()); }
        } catch (Exception e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
