// Bai 4 UDP: moi datagram chua mot lenh CALC va server tra ket qua.
import java.net.*; import java.nio.charset.StandardCharsets;
public class Server {
    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(5204)) {
            System.out.println("UDP may tinh dang nghe cong 5204");
            while (true) { byte[] buf = new byte[2048]; DatagramPacket p = new DatagramPacket(buf, buf.length); socket.receive(p);
                String req = new String(p.getData(), p.getOffset(), p.getLength(), StandardCharsets.UTF_8); String kq = tinh(req);
                byte[] data = kq.getBytes(StandardCharsets.UTF_8); socket.send(new DatagramPacket(data, data.length, p.getAddress(), p.getPort())); }
        } catch (Exception e) { System.out.println("Loi UDP server: " + e.getMessage()); }
    }

static String tinh(String request) {
    String[] p = request.trim().split("\\s+");
    if (p.length != 4 || !p[0].equalsIgnoreCase("CALC")) return "ERR INVALID_FORMAT";
    String op = p[1];
    if (!op.equals("+") && !op.equals("-") && !op.equals("*") && !op.equals("/")) return "ERR UNSUPPORTED_OPERATOR";
    double a, b;
    try { a = Double.parseDouble(p[2]); b = Double.parseDouble(p[3]); }
    catch (NumberFormatException e) { return "ERR INVALID_NUMBER"; }
    if (op.equals("/") && b == 0) return "ERR DIVIDE_BY_ZERO";
    double kq;
    switch (op) { case "+": kq = a + b; break; case "-": kq = a - b; break; case "*": kq = a * b; break; default: kq = a / b; }
    java.text.DecimalFormat df = new java.text.DecimalFormat("0.##########", java.text.DecimalFormatSymbols.getInstance(java.util.Locale.US));
    return "OK " + df.format(kq);
}

}
