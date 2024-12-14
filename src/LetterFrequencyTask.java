import java.util.*;
import java.util.concurrent.BlockingQueue;

public class  LetterFrequencyTask implements Runnable {
    private final List<String> lines;
    private final BlockingQueue<Map<String, Integer>> resultsQueue;

    public LetterFrequencyTask(List<String> lines, BlockingQueue<Map<String, Integer>> resultsQueue) {
        this.lines = lines;
        this.resultsQueue = resultsQueue;
    }

    @Override
    public void run() {
        Map<String, Integer> frequencyMap = new HashMap<>();
        for (String line : lines) {
            line = line.toLowerCase().replaceAll("[^a-z ]", "");

            String[] words = line.split(" ");

            for (String word : words) {
                if (word.length() < 3) continue;

                for (int i = 0; i <= word.length() - 3; i++) {
                    String triplet = word.substring(i, i + 3);
                    frequencyMap.merge(triplet, 1, Integer::sum);
                }
            }
        }

        try {
            resultsQueue.put(frequencyMap);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
