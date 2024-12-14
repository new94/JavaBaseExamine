package org.exam;

import javax.lang.model.type.ArrayType;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;


public class SquareArrayServer {

  public static void main(String[] args) {
    try (ServerSocket serverSocket = new ServerSocket(12345)) {
      System.out.println("Server is running...");
      while(true) {
        Socket clientSocket = serverSocket.accept();
        System.out.println("Client connected");
        new Thread(() -> handleClient(clientSocket)).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static void handleClient(Socket socket) {
    try (
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
    ) {
      int[] inputArray = (int[]) in.readObject();
      System.out.println("received array: " + Arrays.toString(inputArray));

      int[] squaredArray = transformToSquares(inputArray);
      System.out.println("squared array: " + Arrays.toString(squaredArray));

      out.writeObject(squaredArray);
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private static int[] transformToSquares(int[] array) {
    int n = array.length;
    int[] result = new int[n];
    int left = 0, right = n - 1, pos = 0;

    while (left <= right) {
      if(Math.abs(array[left]) > Math.abs(array[right])) {
        result[pos++] = array[left] * array[left];
        left++;
      } else {
        result[pos++] = array[right] * array[right];
        right--;
      }
    }
    return result;
  }
}
