package org.exam;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class SquareArrayClient {
  public static void main(String[] args) {
    try (Socket socket = new Socket("localhost", 12345)) {
      ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
      ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

      Scanner scanner = new Scanner(System.in);
      System.out.println("Enter numbers");
      String[] input = scanner.nextLine().split(" ");
      int[] array = Arrays.stream(input).mapToInt(Integer::parseInt).toArray();

      out.writeObject(array);

      int[] result = (int[]) in.readObject();
      System.out.println("squared array: " + Arrays.toString(result));
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }
}
