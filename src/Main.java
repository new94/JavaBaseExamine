import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        String inputFile = "input.txt";
        ExecutorService executor = Executors.newFixedThreadPool(4);
        BlockingQueue<Map<String, Integer>> resultsQueue = new LinkedBlockingQueue<>();
        long startTime = System.currentTimeMillis();

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        int batchSize = 10;
        List<List<String>> batches = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += batchSize) {
            batches.add(lines.subList(i, Math.min(i + batchSize, lines.size())));
        }

        for (List<String> batch : batches) {
            executor.submit(new  LetterFrequencyTask(batch, resultsQueue));
        }

        Map<String, Integer> aggregatedResults = new HashMap<>();
        for (int i = 0; i < batches.size(); i++) {
            try {
                Map<String, Integer> batchResult = resultsQueue.take();
                batchResult.forEach((key, value) -> aggregatedResults.merge(key, value, Integer::sum));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        aggregatedResults.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .limit(10)
                .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));

        long endTime = System.currentTimeMillis();
        System.out.println("Execution time: " + (endTime - startTime) + " ms");

        executor.shutdown();
    }
}
