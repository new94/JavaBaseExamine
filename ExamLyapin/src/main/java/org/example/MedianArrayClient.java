package org.example;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MedianArrayClient {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8000;

    public static void main(String[] args) {
        int[] arr1 = {-12, -10, -6, -5 , -3, 3, 4, 6, 10, 12, 15};
        int[] arr2 = {2, 3, 5, 8, 10, 12, 14, 16, 18, 20};
        int[] arr3 = {2, 4, 5, 6};

        try (
                Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())
        ) {
            System.out.println("Отправлено на сервер...");
            outputStream.writeObject(arr1);
            double median1 = inputStream.readDouble();
            System.out.println("Медиана 1: " + median1);

            System.out.println("Отправлено на сервер...");
            outputStream.writeObject(arr2);
            double median2 = inputStream.readDouble();
            System.out.println("Медиана 2: " + median2);

            System.out.println("Отправлено на сервер...");
            outputStream.writeObject(arr3);
            double median3 = inputStream.readDouble();
            System.out.println("Медиана 3: " + median3);

            System.out.println("Медианы: " + median1 + ", " + median2 + ", " + median3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
