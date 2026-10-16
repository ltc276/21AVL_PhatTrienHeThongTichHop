// Bai 8 UDP: gui file theo chunk stop-and-wait; moi chunk co so thu tu va thu gui lai khi timeout.
import java.net.*; import java.nio.*; import java.nio.charset.StandardCharsets; import java.nio.file.*; import java.io.*; import java.security.*;
public class Client {
    static final int CHUNK = 1000;
    public static void main(String[] args) {
        if (args.length < 1) { System.out.println("Cach dung: java Client <duong_dan_file> [host]"); return; }
        Path file = Path.of(args[0]); String host = args.length > 1 ? args[1] : "localhost";
        try {
            if (!Files.isRegularFile(file)) { System.out.println("File khong ton tai"); return; }
            String name = file.getFileName().toString(); long size = Files.size(file); String hash = sha256(file); InetAddress ip = InetAddress.getByName(host);
            try (DatagramSocket socket = new DatagramSocket(); InputStream in = Files.newInputStream(file)) {
                socket.setSoTimeout(1000); send(socket, ip, "META|" + name + "|" + size + "|" + hash); String ready = receive(socket);
                if (!ready.equals("READY")) { System.out.println("Server: " + ready); return; }
                int seq = 0; byte[] buf = new byte[CHUNK];
                if (size == 0) { System.out.println("Server: " + receive(socket)); return; }
                while (true) {
                    int n = in.read(buf); if (n == -1) break; byte[] packet = new byte[n + 4]; ByteBuffer.wrap(packet).putInt(seq).put(buf, 0, n); boolean acked = false;
                    for (int tries = 1; tries <= 5 && !acked; tries++) {
                        socket.send(new DatagramPacket(packet, packet.length, ip, 5208));
                        try {
                            String reply = receive(socket);
                            if (reply.startsWith("ACK|" + seq)) acked = true;
                            else if (reply.startsWith("DONE|" + seq + "|")) { System.out.println("Server: " + reply.substring(reply.lastIndexOf('|') + 1)); return; }
                            else if (reply.startsWith("ERR")) { System.out.println("Server: " + reply); return; }
                        } catch (SocketTimeoutException e) { System.out.println("Thu gui lai chunk " + seq + " lan " + tries); }
                    }
                    if (!acked) { System.out.println("Khong nhan duoc ACK cho chunk " + seq); return; }
                    seq++;
                }
                // Client gui xong du lieu; doi thong bao ket qua. Server gui DONE sau khi nhan du chunk cuoi.
                try { System.out.println("Server: " + receive(socket)); }
                catch (SocketTimeoutException e) { System.out.println("Khong nhan duoc ket qua cuoi; kiem tra file tren server."); }
            }
        } catch (Exception e) { System.out.println("Loi UDP file client: " + e.getMessage()); }
    }
    static void send(DatagramSocket s, InetAddress ip, String msg) throws Exception { byte[] b=msg.getBytes(StandardCharsets.UTF_8); s.send(new DatagramPacket(b,b.length,ip,5208)); }
    static String receive(DatagramSocket s) throws Exception { byte[] b=new byte[2048]; DatagramPacket p=new DatagramPacket(b,b.length); s.receive(p); return new String(p.getData(),p.getOffset(),p.getLength(),StandardCharsets.UTF_8); }
    static String sha256(Path path) throws Exception { MessageDigest md=MessageDigest.getInstance("SHA-256"); try(InputStream in=Files.newInputStream(path)){byte[] b=new byte[4096];int n;while((n=in.read(b))!=-1)md.update(b,0,n);} StringBuilder s=new StringBuilder();for(byte x:md.digest())s.append(String.format("%02x",x));return s.toString(); }
}
