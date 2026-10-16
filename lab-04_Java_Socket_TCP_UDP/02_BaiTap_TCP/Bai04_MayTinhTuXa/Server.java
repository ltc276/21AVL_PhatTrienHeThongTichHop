// Bai 4 TCP: server tinh cong, tru, nhan, chia va tra ma loi neu dau vao sai.
import java.io.*; import java.net.*; import java.nio.charset.StandardCharsets;
public class Server {
    public static void main(String[] args) {
        try (ServerSocket server = new ServerSocket(5204)) {
            System.out.println("TCP may tinh dang nghe cong 5204");
            while (true) try (Socket s = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true)) {
                String request; while ((request = in.readLine()) != null) { if (request.equalsIgnoreCase("QUIT")) { out.println("OK BYE"); break; } out.println(tinh(request)); }
            }
        } catch (IOException e) { System.out.println("Loi server: " + e.getMessage()); }
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
