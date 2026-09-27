import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class TCPServer {

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(5000);

        System.out.println("TCP Server dang chay...");

        Socket socket = server.accept();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream(),
                        StandardCharsets.UTF_8));

        PrintWriter out = new PrintWriter(
                new OutputStreamWriter(
                        socket.getOutputStream(),
                        StandardCharsets.UTF_8),
                true);

        String data;

        while ((data = in.readLine()) != null) {

            // Neu Client gui QUIT thi thoat
            if (data.equals("QUIT")) {
                break;
            }

            String result;

            // Kiem tra dung 1 chu so
            if (data.equals("0")) {
                result = "khong";
            } else if (data.equals("1")) {
                result = "mot";
            } else if (data.equals("2")) {
                result = "hai";
            } else if (data.equals("3")) {
                result = "ba";
            } else if (data.equals("4")) {
                result = "bon";
            } else if (data.equals("5")) {
                result = "nam";
            } else if (data.equals("6")) {
                result = "sau";
            } else if (data.equals("7")) {
                result = "bay";
            } else if (data.equals("8")) {
                result = "tam";
            } else if (data.equals("9")) {
                result = "chin";
            } else {
                result = "ERR INVALID_DIGIT";
            }

            out.println(result);
        }

        socket.close();
        server.close();

        System.out.println("TCP Server ket thuc.");
    }
}
