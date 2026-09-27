import java.io.*;
import java.net.*;

public class TCPClient {

    static final String HOST = "localhost";
    static final int PORT = 5002;

    static final int MESSAGE_SIZE = 1024;
    static final int MESSAGE_COUNT = 1000;
    static final int TEST_COUNT = 5;
    static final int TIMEOUT = 2000;

    public static void main(String[] args) throws Exception {

        byte[] message = new byte[MESSAGE_SIZE];

        for (int i = 0; i < message.length; i++) {
            message[i] = 'A';
        }

        long totalAllRuns = 0;
        int totalResponses = 0;

        System.out.println("TCP BENCHMARK");
        System.out.println("Message size: " + MESSAGE_SIZE + " bytes");
        System.out.println("Messages: " + MESSAGE_COUNT);
        System.out.println("Timeout: " + TIMEOUT + " ms");
        System.out.println();

        for (int run = 1; run <= TEST_COUNT; run++) {

            Socket socket =
                    new Socket(HOST, PORT);

            socket.setSoTimeout(TIMEOUT);

            DataOutputStream out =
                    new DataOutputStream(socket.getOutputStream());

            DataInputStream in =
                    new DataInputStream(socket.getInputStream());

            int responses = 0;

            long start = System.nanoTime();

            try {

                for (int i = 0; i < MESSAGE_COUNT; i++) {

                    out.writeInt(message.length);
                    out.write(message);
                    out.flush();

                    try {

                        int length = in.readInt();

                        byte[] result =
                                new byte[length];

                        in.readFully(result);

                        responses++;

                    } catch (SocketTimeoutException e) {

                        break;
                    }
                }

            } finally {

                socket.close();
            }

            long end = System.nanoTime();

            long timeMs =
                    (end - start) / 1_000_000;

            totalAllRuns += timeMs;
            totalResponses += responses;

            System.out.println(
                    "Lan " + run +
                    ": " + timeMs + " ms" +
                    ", phan hoi = " + responses
            );
        }

        double averageTime =
                (double) totalAllRuns / TEST_COUNT;

        double averageResponses =
                (double) totalResponses / TEST_COUNT;

        System.out.println();
        System.out.println(
                "Thoi gian trung binh: "
                        + averageTime + " ms"
        );

        System.out.println(
                "Phan hoi trung binh: "
                        + averageResponses
        );
    }
}