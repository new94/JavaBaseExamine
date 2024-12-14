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
            writer.start();

            new FileReaderThread(firstInput, queue).run();
            new FileReaderThread(secondInput, queue).run();

            queue.put("EOF");

            writer.join();

            System.out.println("Data successfully written to " + outputFile);
        } catch (Exception e) {
            System.err.println("An error occurred during execution:");
            e.printStackTrace();
        }
    }
}
