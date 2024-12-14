package org.example;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class MedianArrayServer {
    private static final int PORT = 8000;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер на порту " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Клиент подключен: " + clientSocket.getInetAddress());
                handleClient(clientSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());
                DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream())
        ) {
            while (true) {
                try {
                    int[] inputArray = (int[]) inputStream.readObject(); // Чтение массива
                    System.out.println("Массив перед обработкой: " + Arrays.toString(inputArray));

                    double median = getMedian(inputArray);
                    System.out.println("Расчитанная медиана: " + median);

                    outputStream.writeDouble(median);
                    outputStream.flush();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Получена ошибка: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static double getMedian(int[] numbers) {
        Arrays.sort(numbers);
        final int size = numbers.length;
        return (size % 2 == 0) ?
                (numbers[size / 2 - 1] + numbers[size / 2]) / 2.0
                : numbers[size / 2];
    }
}
