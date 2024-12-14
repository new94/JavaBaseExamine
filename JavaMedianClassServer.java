import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;



public class JavaMedianClassServer {
    private static Socket clientSocket;
    private static ServerSocket server;
    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                server = new ServerSocket(4004);

                clientSocket = server.accept();

                try {


                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                    String word;
                    while ((word = in.readLine()) != null) {

                        String[] numbers = word.split(" ");

                        double average = 0.0;
                        int count = 0;

                        for (String number : numbers) {
                            int numberInt = Integer.parseInt(number);
                            average += numberInt;
                            count++;
                        }

                        average = average / count;

                        out.write("Median: " + average + "\n");

                        out.flush();
                    }

                } finally {
                    clientSocket.close();

                    in.close();
                    out.close();
                }
            } finally {
                server.close();
            }
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
