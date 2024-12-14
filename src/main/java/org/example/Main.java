package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class Main {
    public static void main(String[] args){
        Task();
    }

    private static <Character> List<java.lang.Character> Task() {
        String inputFilename = "inputFile.txt";
        Path inputFile = parseInputFileName(inputFilename);

        List<String> lines = new ArrayList<>();
        List<Character> reccurrentChars = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(inputFile)) {
            String line;
            Integer numberLines = 0;
            while ((line = reader.readLine()) != null) {
                numberLines++;
                if (numberLines % 10 == 0 && numberLines != 0) {
                    reccurrentChars.add((Character) AnalyzeMostLetter(line));
                }
                lines.add(line);
            }
            return (List<java.lang.Character>) reccurrentChars;
        } catch (IOException e) {
            throw new RuntimeException("Error while reading file", e);
        }
    }

    private static Path parseInputFileName(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new RuntimeException("Filename is empty");
        }

        Path path = getPath(filename);

        if (!Files.exists(path)) {
            throw new RuntimeException("File not found " + path);
        }
        if (!Files.isRegularFile(path)) {
            throw new RuntimeException("Not a regular file " + path);
        }
        return path;
    }

    private static Path getPath(String filename) {
        if (filename == null || filename.isEmpty()) {
            throw new IllegalArgumentException("File path cannot be null or empty!");
        }
        Path path = Path.of(filename);
        return path.toAbsolutePath();
    }

    private static Character AnalyzeMostLetter(String line) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        try {
            AtomicReference<Character> mostLetter = new AtomicReference<>();
            executorService.submit(() -> {
                mostLetter.set(proccessLine(line));
            });
            return mostLetter.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
            try {
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    executorService.shutdownNow();
                }
            } catch (InterruptedException e) {
                executorService.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    private static Character proccessLine(String line) {
        if (line == null || line.isEmpty()) {
            return null;
        }
        Map<Character, Integer> CharactersMap = new HashMap<>();
        for(int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (CharactersMap.containsKey(c)) {
                CharactersMap.put(Character.valueOf(c), CharactersMap.get(c) + 1);
            } else {
                CharactersMap.put(Character.valueOf(c), 1);
            }
        }
        Character mostLetter = null;
        Integer maxCount = 0;
        for(Map.Entry<Character, Integer> entry : CharactersMap.entrySet()) {
            Character c = entry.getKey();
            Integer count = entry.getValue();
            if (count > maxCount) {
                maxCount = count;
                mostLetter = c;
            }
        }
        return mostLetter;
    }
}