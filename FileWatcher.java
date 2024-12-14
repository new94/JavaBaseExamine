/*
Реализуйте программу на Java, которая запускает отдельный
 поток на чтение файла, раз в заданное время проверяет
  изменения в файле и записывает в отдельный файл лог
   изменений этого файла с датой последнего чтения
 */

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileWatcher {

    private final Path filePath;
    private final Path logFilePath;
    private final long checkIntervalMillis;
    private long lastModifiedTime;

    private ScheduledExecutorService scheduler;

    public FileWatcher(Path filePath, Path logFilePath, long checkIntervalMillis) {
        this.filePath = filePath;
        this.logFilePath = logFilePath;
        this.checkIntervalMillis = checkIntervalMillis;
        this.lastModifiedTime = 0;
    }

    public void startWatching() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::checkFileForChanges, 0, checkIntervalMillis, TimeUnit.MILLISECONDS);

        Scanner scanner = new Scanner(System.in);
        new Thread(() -> {
            System.out.println("Введите 'exit' для завершения программы.");
            Boolean foundExit = false;
            while (!foundExit) {
                if (scanner.nextLine().equalsIgnoreCase("exit")) {
                    scanner.close();
                    shutdownAndExit();
                }
            }
        }).start();
    }

    private synchronized void shutdownAndExit() {
        scheduler.shutdown();
        System.out.println("Программа завершена.");
        System.exit(0);
    }

    private synchronized void checkFileForChanges() {
        try {
            long currentLastModifiedTime = Files.getLastModifiedTime(filePath).toMillis();

            if (currentLastModifiedTime > lastModifiedTime) {
                lastModifiedTime = currentLastModifiedTime;
                logChanges(currentLastModifiedTime);
            }

        } catch (IOException e) {
            logError("Ошибка при проверке файла: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void logChanges(long timestamp) {
        try (BufferedWriter writer = Files.newBufferedWriter(logFilePath, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            String formattedDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Файл изменен: " + formattedDateTime + ", последнее чтение: " + timestamp + "\n");
        } catch (IOException e) {
            logError("Ошибка при записи в лог-файл: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private void logError(String message) {
        System.err.println(message);
    }

    public static void main(String[] args) {
         if (args.length < 3) {
            System.err.println("Использование: java FileWatcher <путь_к_файлу> <путь_к_лог_файлу> <интервал_проверки_в_миллисекундах>");
            System.exit(1);
        }

        Path filePath = Paths.get(args[0]);
        Path logFilePath = Paths.get(args[1]);
        long checkIntervalMillis = Long.parseLong(args[2]);

        FileWatcher watcher = new FileWatcher(filePath, logFilePath, checkIntervalMillis);
        watcher.startWatching();
    }
}
