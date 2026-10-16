// Bai 3 UDP: moi datagram la mot lenh ngay gio; UDP khong can lenh QUIT.
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
public class Server {
    public static void main(String[] args) {
        DateTimeFormatter ngay = DateTimeFormatter.ofPattern("dd MM yyyy");
        DateTimeFormatter gio = DateTimeFormatter.ofPattern("HH mm ss");
        try (DatagramSocket socket = new DatagramSocket(5203)) {
            System.out.println("UDP dich vu ngay gio dang nghe cong 5203");
            while (true) {
                byte[] buf = new byte[1024]; DatagramPacket p = new DatagramPacket(buf, buf.length); socket.receive(p);
                String cmd = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8).trim().toUpperCase();
                LocalDateTime now = LocalDateTime.now(); String kq;
                switch (cmd) {
                    case "DATE": kq = "OK " + now.format(ngay); break;
                    case "TIME": kq = "OK " + now.format(gio); break;
                    case "DATETIME": kq = "OK " + now.format(ngay) + " " + now.format(gio); break;
                    case "QUIT": kq = "OK BYE"; break;
                    default: kq = "ERR UNKNOWN_COMMAND";
                }
                byte[] data = kq.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(data, data.length, p.getAddress(), p.getPort()));
            }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }
}
