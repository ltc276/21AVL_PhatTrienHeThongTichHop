import java.net.*;
import java.io.*;

public class TCPClient {
    public static void main(String[] args) throws Exception {

        
        if (args.length != 2) {
            System.out.println(
                "Cach dung: java TCPClient <hostname> <URI>"
            );
            return;
        }

        String hostname = args[0];
        String uri = args[1];

        Socket socket = new Socket("localhost", 5000);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));

        PrintWriter out = new PrintWriter(
                socket.getOutputStream(), true);

       
        out.println(hostname);
        out.println(uri);

        
        String line;

        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }

        socket.close();
    }
}
