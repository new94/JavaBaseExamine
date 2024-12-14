import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Server {
  private static final int PORT = 8080;

  private static List<Double> processLine(String numbers) {
    if (numbers == null) {
      return null;
    }

    String[] numberStrings = numbers.split(",");
    List<Double> numberList = new ArrayList<>();

    for (String num : numberStrings) {
      double parsedVal;
      try {
        parsedVal = Double.parseDouble(num.trim());
      } catch (NumberFormatException e) {
        return null;
      }
      numberList.add(parsedVal);
    }

    return numberList;
  }

  private static void serverIteration(ServerSocket serverSocket) {
    try (Socket clientSocket = serverSocket.accept();
         BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
         PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

      System.out.println("Client connected: " + clientSocket.getInetAddress());

      String numbers = in.readLine();
      System.out.println("Received numbers: " + numbers);
      List<Double> numberList = processLine(numbers);
      if (numberList != null) {
        double median = calculateMedian(numberList);
        System.out.println("Calculated median: " + median);
        out.println(median);
      } else {
        out.println("Invalid input");
      }
    } catch (IOException e) {
      System.out.println("Error handling client: " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(PORT)) {
      System.out.println("Server started on port " + PORT);

      while (true) {
        serverIteration(serverSocket);
      }
    } catch (IOException e) {
      System.out.println("Server error: " + e.getMessage());
    }
  }

  private static double calculateMedian(List<Double> numbers) {
    Collections.sort(numbers);
    int size = numbers.size();

    if (size % 2 == 0) {
      return (numbers.get(size / 2 - 1) + numbers.get(size / 2)) / 2;
    } else {
      return numbers.get(size / 2);
    }
  }
}
