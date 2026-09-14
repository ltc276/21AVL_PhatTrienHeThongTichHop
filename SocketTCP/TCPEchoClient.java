import java.io.*;
import java.net.Socket;

public class TCPEchoClient {
    public final static String serverIP = "127.0.0.1";
    public final static int serverPort = 5000;

    public static void main(String[] args)
            throws InterruptedException, IOException {

        Socket s = null;

        try {
            s = new Socket(serverIP, serverPort);
            System.out.println("Client da duoc tao");

            InputStream is = s.getInputStream();
            OutputStream os = s.getOutputStream();

            for (int i = '0'; i <= '9'; i++) {
                os.write(i);

                int ch = is.read();
                System.out.println((char) ch);

                Thread.sleep(2000);
            }

        } catch (IOException ie) {
            System.out.println("Error: Can NOT create socket");
        } finally {
            if (s != null) {
                s.close();
            }
        }
    }
}