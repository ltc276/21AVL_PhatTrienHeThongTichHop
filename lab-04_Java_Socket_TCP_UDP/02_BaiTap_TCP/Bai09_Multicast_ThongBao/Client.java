// Bai 9 TCP: client ket noi va lang nghe thong bao server broadcast.
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        try (Socket s = new Socket(host, 5209); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8))) {
            System.out.println("Dang cho thong bao tu server...");
            String line;
            while ((line = in.readLine()) != null) {
                System.out.println(line);
                if (line.endsWith("QUIT")) break;
            }
        } catch (IOException e) {
            System.out.println("Loi ket noi: " + e.getMessage());
        }
    }
}