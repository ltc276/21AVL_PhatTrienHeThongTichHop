// Bai 6 TCP: gui 1000 thong diep moi lan, lap 5 lan va thong ke thoi gian/phuc hoi.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost"; int soPhanHoi = 0;
        try (Socket s = new Socket(host, 5206); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
            s.setSoTimeout(3000);
            for (int lan = 1; lan <= 5; lan++) { long start = System.nanoTime(); int dem = 0;
                for (int i = 0; i < 1000; i++) { String msg = String.format("PING|%02d|%04d", lan, i); out.println(msg); String reply = in.readLine(); if (("ACK|" + msg).equals(reply)) dem++; }
                long ms = (System.nanoTime() - start) / 1_000_000; soPhanHoi += dem; System.out.println("Lan " + lan + ": " + dem + "/1000 phan hoi, " + ms + " ms"); }
            System.out.println("Tong phan hoi: " + soPhanHoi + "/5000");
        } catch (Exception e) { System.out.println("Loi client: " + e.getMessage()); }
    }
}
