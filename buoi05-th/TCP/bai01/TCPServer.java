import java.net.*;//thu vien mang: ServerSocket, Socket, InetAddress,URI,..
import java.io.*;// thu vien doc ghi

public class TCPServer {
    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);//tao sv qua cong 5000
        System.out.println("TCP Server dang chay...");

        Socket socket = server.accept();//kiem tra ket noi voi client
        System.out.println("Client da ket noi!");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));//lay du lieu tu client gui sv

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);//lay du lieu tu sv chuyen ve client

        // Nhan hostname va URI rieng
        String hostname = in.readLine();//nhan hostname
        String uriString = in.readLine();//nhan URI

        out.println("===== HOST =====");
        out.println("Hostname: " + hostname);

        // Xu ly hostname
        try {
            InetAddress[] list = InetAddress.getAllByName(hostname);//tim tat ca ip tuong ung voi hostname

            for (InetAddress ip : list) { // duyet tung ip cua list 
                out.println("IP: " + ip.getHostAddress());//in ip gui cho client

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

            out.println("Scheme: " + uri.getScheme());//lay giao thuc
            out.println("Host: " + uri.getHost());//lay ten may chu
            out.println("Port: " + uri.getPort());//lay cong
            out.println("Path: " + uri.getPath());//duong dan
            out.println("Query: " + uri.getQuery());//lay phan truy van
            out.println("Fragment: " + uri.getFragment());

        } catch (URISyntaxException e) {
            out.println("Loi: URI khong hop le");
        }

        socket.close();
        server.close();
    }
}
