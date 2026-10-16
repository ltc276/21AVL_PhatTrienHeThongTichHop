// Bai 1: kiem tra host va phan tich URI theo tung thanh phan.
import java.net.*;

public class Main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Cach dung: java Main <hostname> <URI>");
            System.out.println("Vi du: java Main example.com https://example.com:443/a?x=1#top");
            return;
        }
        try {
            System.out.println("=== DIA CHI HOST ===");
            for (InetAddress ip : InetAddress.getAllByName(args[0])) {
                String loai = ip instanceof Inet4Address ? "IPv4" : ip instanceof Inet6Address ? "IPv6" : "Khong ro";
                System.out.println("IP: " + ip.getHostAddress());
                System.out.println("Loai: " + loai);
                System.out.println("Loopback: " + ip.isLoopbackAddress());
                System.out.println("Site local: " + ip.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Khong phan giai duoc hostname: " + args[0]);
        }
        try {
            URI uri = new URI(args[1]);
            System.out.println("=== URI ===");
            System.out.println("Scheme: " + uri.getScheme());
            System.out.println("Host: " + uri.getHost());
            System.out.println("Port: " + uri.getPort());
            System.out.println("Path: " + uri.getPath());
            System.out.println("Query: " + uri.getQuery());
            System.out.println("Fragment: " + uri.getFragment());
        } catch (URISyntaxException e) {
            System.out.println("URI khong hop le: " + e.getMessage());
        }
    }
}
