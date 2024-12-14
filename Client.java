import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
  private static final String SERVER_ADDRESS = "localhost";
  private static final int SERVER_PORT = 8080;

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    while (true) {
      try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
           PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
           BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

        System.out.println("Enter numbers separated by commas (or 'exit' to quit):");
        String input = scanner.nextLine();

        if ("exit".equalsIgnoreCase(input.trim())) {
          break;
        }

        // Send numbers to server
        out.println(input);
        System.out.println("Sent numbers to server");

        // Receive and display the result
        String result = in.readLine();
        System.out.println("Response: " + result);

      } catch (IOException e) {
        System.out.println("Error connecting to server: " + e.getMessage());
        break;
      }
    }
    scanner.close();
  }
}
