import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
            System.out.println("Подключено к серверу " + SERVER_ADDRESS + " на порту " + SERVER_PORT);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));
            BufferedWriter out = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream()));
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Введите имя файла (или 'exit' для завершения): ");
                String fileName = scanner.nextLine();

                if (fileName.equalsIgnoreCase("exit")) {
                    out.write("exit\n");
                    out.flush();
                    System.out.println("Завершение работы клиента.");
                    break;
                }

                System.out.println("Введите ключевое слово: ");
                String keyword = scanner.nextLine();

                out.write(fileName + "\n");
                out.write(keyword + "\n");
                out.flush();

                StringBuilder response = new StringBuilder();
                String line;

                socket.setSoTimeout(2000);
                try {
                    while ((line = in.readLine()) != null) {
                        response.append(line).append("\n");
                    }
                } catch (SocketTimeoutException e) {
                }

                System.out.println("Ответ от сервера:");
                System.out.println(response.toString());
            }

            scanner.close();
        } catch (IOException e) {
            System.err.println("Ошибка подключения к серверу: " + e.getMessage());
        }
    }
}
