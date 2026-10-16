// Bai 9 UDP: receiver tham gia multicast group voi NetworkInterface duoc chi dinh.
import java.net.*; import java.nio.charset.StandardCharsets; import java.util.Enumeration;
public class Server {
    static final String GROUP = "239.255.0.1"; static final int PORT = 5209;
    public static void main(String[] args) {
        NetworkInterface ni = timInterface();
        if (ni == null) { System.out.println("Khong tim thay NetworkInterface ho tro multicast"); return; }
        InetAddress group;
        try { group = InetAddress.getByName(GROUP); }
        catch (Exception e) { System.out.println("Loi dia chi group"); return; }
        try (MulticastSocket socket = new MulticastSocket(PORT)) {
            InetSocketAddress groupAddress = new InetSocketAddress(group, PORT);
            socket.joinGroup(groupAddress, ni);
            System.out.println("Dang nhan multicast tai " + GROUP + ":" + PORT + " qua " + ni.getName());
            try {
                while (true) { byte[] b = new byte[4096]; DatagramPacket p = new DatagramPacket(b, b.length); socket.receive(p); String msg = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); System.out.println("Thong bao: " + msg); if (msg.equalsIgnoreCase("QUIT")) break; }
            } finally { socket.leaveGroup(groupAddress, ni); }
        } catch (Exception e) { System.out.println("Loi multicast: " + e.getMessage() + ". Kiem tra Wi-Fi, VPN va firewall."); }
    }
    static NetworkInterface timInterface() {
        try { Enumeration<NetworkInterface> ds = NetworkInterface.getNetworkInterfaces(); while (ds.hasMoreElements()) { NetworkInterface ni = ds.nextElement(); try { if (ni.isUp() && ni.supportsMulticast() && !ni.isLoopback()) return ni; } catch (SocketException e) { } } }
        catch (SocketException e) { }
        return null;
    }
}
