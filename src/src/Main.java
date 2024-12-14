import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args) {
        System.out.println("Current working directory: " + System.getProperty("user.dir"));

        BlockingQueue<String> queue = new LinkedBlockingQueue<>();

        String firstInput = "input1.txt";
        String secondInput = "input2.txt";
        String outputFile = "output.txt";

        Thread writer = new Thread(new QueueWriterThread(queue, outputFile));

        try {
            // Запускаем поток записи
            writer.start();

            // Читаем первый файл
            new FileReaderThread(firstInput, queue).run();

            // Читаем второй файл
            new FileReaderThread(secondInput, queue).run();

            // Гарантированно добавляем маркер завершения
            queue.put("EOF");

            // Ожидаем завершения потока записи
            writer.join();

            System.out.println("Data successfully written to " + outputFile);
        } catch (Exception e) {
            System.err.println("An error occurred during execution:");
            e.printStackTrace();
        }
    }
}
