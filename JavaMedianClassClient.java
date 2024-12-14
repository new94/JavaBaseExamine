import java.io.*;
import java.net.Socket;
import java.util.List;

public class JavaMedianClassClient {

    private static Socket clientSocket;
    private static BufferedReader reader;

    private static BufferedReader in;
    private static BufferedWriter out;

    public static void main(String[] args) {
        try {
            try {
                clientSocket = new Socket("localhost", 4004);

                reader = new BufferedReader(new InputStreamReader(System.in));

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                System.out.println("Enter the numbers:");

                String command;

                while ((command = reader.readLine()) != null) {
                    out.write(command + "\n");
                    out.flush();

                    String response = in.readLine();
                    System.out.println(response);
                }


            } finally {
            clientSocket.close();
            in.close();
            out.close();
        }
    } catch (IOException e) {
        System.err.println(e);
    }

    }
}
