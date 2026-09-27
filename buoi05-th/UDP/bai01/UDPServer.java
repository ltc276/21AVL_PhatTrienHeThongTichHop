import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws Exception {

        DatagramSocket server = new DatagramSocket(5001);

        System.out.println("UDP Server dang chay...");

        // Nhan hostname
        byte[] data1 = new byte[1024];

        DatagramPacket packet1 =
                new DatagramPacket(data1, data1.length);

        server.receive(packet1);

        String hostname = new String(
                packet1.getData(),
                0,
                packet1.getLength());

        // Nhan URI
        byte[] data2 = new byte[2048];

        DatagramPacket packet2 =
                new DatagramPacket(data2, data2.length);

        server.receive(packet2);

        String uriString = new String(
                packet2.getData(),
                0,
                packet2.getLength());

        String result = "";

        result += "===== HOST =====\n";
        result += "Hostname: " + hostname + "\n";

        // Xu ly hostname
        try {
            InetAddress[] list =
                    InetAddress.getAllByName(hostname);

            for (InetAddress ip : list) {

                result += "IP: "
                        + ip.getHostAddress() + "\n";

                if (ip instanceof Inet4Address) {
                    result += "Loai: IPv4\n";
                } else {
                    result += "Loai: IPv6\n";
                }

                result += "Loopback: "
                        + ip.isLoopbackAddress() + "\n";

                result += "Site local: "
                        + ip.isSiteLocalAddress() + "\n";
            }

        } catch (UnknownHostException e) {
            result +=
                "Loi: Khong phan giai duoc hostname\n";
        }

        // Xu ly URI
        result += "\n===== URI =====\n";

        try {
            URI uri = new URI(uriString);

            result += "Scheme: "
                    + uri.getScheme() + "\n";

            result += "Host: "
                    + uri.getHost() + "\n";

            result += "Port: "
                    + uri.getPort() + "\n";

            result += "Path: "
                    + uri.getPath() + "\n";

            result += "Query: "
                    + uri.getQuery() + "\n";

            result += "Fragment: "
                    + uri.getFragment() + "\n";

        } catch (URISyntaxException e) {
            result += "Loi: URI khong hop le\n";
        }

        // Gui ket qua ve Client
        byte[] resultData = result.getBytes();

        DatagramPacket resultPacket =
                new DatagramPacket(
                        resultData,
                        resultData.length,
                        packet1.getAddress(),
                        packet1.getPort());

        server.send(resultPacket);

        server.close();
    }
}
