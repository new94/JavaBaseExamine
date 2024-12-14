import java.io.*;
import java.net.*;

public class Server {
    private static final int PORT = 12345;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Сервер запущен и ожидает подключений на порту " + PORT + "...");

            while (true) {
                try (Socket clientSocket = serverSocket.accept()) {
                    System.out.println("Клиент подключен: " + clientSocket.getInetAddress());

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream()));
                    BufferedWriter out = new BufferedWriter(
                            new OutputStreamWriter(clientSocket.getOutputStream()));

                    String request;
                    while ((request = in.readLine()) != null) {
                        if (request.equalsIgnoreCase("exit")) {
                            System.out.println("Получена команда exit. Закрытие соединения с клиентом.");
                            break;
                        }

                        String fileName = request;
                        String keyword = in.readLine();

                        System.out.println("Получен запрос: файл = " + fileName + ", ключевое слово = " + keyword);

                        File file = new File(fileName);
                        if (!file.exists()) {
                            out.write("Файл " + fileName + " не найден.\n");
                            out.flush();
                            continue;
                        }

                        BufferedReader fileReader = new BufferedReader(new FileReader(file));
                        String line;
                        StringBuilder result = new StringBuilder();
                        boolean keywordFound = false;

                        while ((line = fileReader.readLine()) != null) {
                            if (line.contains(keyword)) {
                                result.append(line).append("\n");
                                keywordFound = true;
                            }
                        }
                        fileReader.close();

                        if (keywordFound) {
                            out.write(result.toString());
                        } else {
                            out.write("Ключевое слово \"" + keyword + "\" не найдено в файле " + fileName + ".\n");
                        }
                        out.flush();
                    }

                    System.out.println("Соединение с клиентом закрыто.");
                } catch (IOException e) {

                    System.err.println("Ошибка при обработке клиента: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Не удалось запустить сервер: " + e.getMessage());
        }
    }
}
