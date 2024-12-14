import java.io.*;
import java.util.*;
import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        String filePath = "input.txt";

        String fileContent = readFile(filePath);
        if (fileContent == null) {
            System.out.println("Не удалось прочитать файл.");
            return;
        }
        ExecutorService executor = Executors.newFixedThreadPool(2);;


        Future<Map<Character, Integer>> letterCountFuture = executor.submit(() -> countLetters(fileContent));
        Future<Map<String, Integer>> wordCountFuture = executor.submit(() -> countWords(fileContent));

        try {
            Map<Character, Integer> letterCounts = letterCountFuture.get();
            Map<String, Integer> wordCounts = wordCountFuture.get();

            System.out.println("Частота букв:");
            letterCounts.forEach((letter, count) -> System.out.println(letter + ": " + count));

            System.out.println("\nЧастота слов:");
            wordCounts.forEach((word, count) -> System.out.println(word + ": " + count));
        } catch (InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private static String readFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append(" ");
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
            return null;
        }
        return content.toString().trim();
    }

    private static Map<Character, Integer> countLetters(String text) {
        Map<Character, Integer> letterCounts = new HashMap<>();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                letterCounts.put(c, letterCounts.getOrDefault(c, 0) + 1);
            }
        }
        return letterCounts;
    }

    private static Map<String, Integer> countWords(String text) {
        Map<String, Integer> wordCounts = new HashMap<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
        }
        return wordCounts;
    }
}
