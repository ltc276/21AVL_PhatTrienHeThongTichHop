// Bai 6 UDP: gui 1000 datagram moi lan, lap 5 lan, dem phan hoi va do thoi gian.
import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost"; int tong = 0;
        try (DatagramSocket socket = new DatagramSocket()) {
            socket.setSoTimeout(100); InetAddress ip = InetAddress.getByName(host);
            for (int lan = 1; lan <= 5; lan++) { long start = System.nanoTime(); int dem = 0;
                for (int i = 0; i < 1000; i++) { String msg = String.format("PING|%02d|%04d", lan, i); byte[] b = msg.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(b, b.length, ip, 5206));
                    long deadline = System.currentTimeMillis() + 100; boolean found = false;
                    while (!found && System.currentTimeMillis() < deadline) { try { byte[] buf = new byte[2048]; DatagramPacket p = new DatagramPacket(buf, buf.length); socket.receive(p); String r = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); if (r.equals("ACK|" + msg)) { dem++; found = true; } } catch (SocketTimeoutException e) { break; } }
                }
                long ms = (System.nanoTime() - start) / 1_000_000; tong += dem; System.out.println("Lan " + lan + ": " + dem + "/1000 phan hoi, " + ms + " ms");
            }
            System.out.println("Tong phan hoi: " + tong + "/5000. Can lap lai nhieu lan va ghi ro moi truong.");
        } catch (Exception e) { System.out.println("Loi UDP client: " + e.getMessage()); }
    }
}
