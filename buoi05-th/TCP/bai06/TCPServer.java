import java.io.*;
import java.net.*;

public class TCPServer {

    static final int PORT = 5002;

    public static void main(String[] args) throws Exception {

        ServerSocket server = new ServerSocket(PORT);

        System.out.println("TCP Benchmark Server dang chay...");

        while (true) {

            Socket socket = server.accept();

            DataInputStream in =
                    new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            try {

                while (true) {

                    int length;

                    try {
                        length = in.readInt();
                    } catch (EOFException e) {
                        break;
                    }

                    byte[] data = new byte[length];

                    in.readFully(data);

                    out.writeInt(data.length);
                    out.write(data);
                    out.flush();
                }

            } finally {

                socket.close();
            }
        }
    }
}