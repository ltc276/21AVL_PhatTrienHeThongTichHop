// Bai vi du 4.1: nhan hostname va in cac dia chi IP cung thong tin co ban.
import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.UnknownHostException;

public class HostInspector {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Cach dung: java HostInspector <hostname>");
            return;
        }

        try {
            InetAddress[] ds = InetAddress.getAllByName(args[0]);
            System.out.println("Host: " + args[0]);
            for (InetAddress ip : ds) {
                String loai = "Khong ro";
                if (ip instanceof Inet4Address) loai = "IPv4";
                if (ip instanceof Inet6Address) loai = "IPv6";
                System.out.println("IP: " + ip.getHostAddress());
                System.out.println("Loai: " + loai);
                System.out.println("Loopback: " + ip.isLoopbackAddress());
                System.out.println("Site local: " + ip.isSiteLocalAddress());
            }
        } catch (UnknownHostException e) {
            System.out.println("Khong phan giai duoc host: " + args[0]);
        }
    }
}
