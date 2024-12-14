import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class QueueWriterThread implements Runnable {
    private final BlockingQueue<String> queue;
    private final String outputFilePath;

    public QueueWriterThread(BlockingQueue<String> queue, String outputFilePath) {
        this.queue = queue;
        this.outputFilePath = outputFilePath;
    }

    @Override
    public void run() {
        try {
            if (!Files.exists(Paths.get(outputFilePath))) {
                Files.createFile(Paths.get(outputFilePath));
            }

            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFilePath))) {
                while (true) {
                    String line = queue.take();
                    if ("EOF".equals(line)) {
                        break;
                    }
                    writer.write(line);
                    writer.newLine();
                }
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error writing to file: " + outputFilePath);
            e.printStackTrace();
        }
    }
}
