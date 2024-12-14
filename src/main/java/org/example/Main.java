package org.example;


import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Random;
import java.util.random.RandomGenerator;

public class Main {
  public static void main(String[] args) {
    Queue<String> queue = new ArrayDeque<>();
    String outputFilename = "output";

    MessageProducer producer = new MessageProducer(queue);
    MessageConsumer consumer = new MessageConsumer(queue, outputFilename);

    producer.start();
    consumer.start();
  }
}

class MessageProducer extends Thread {

  private final Queue<String> queue;
  private final Random random;

  public MessageProducer(Queue<String> queue) {
    this.queue = queue;
    random = Random.from(RandomGenerator.getDefault());
  }

  @Override
  public void run() {
    while (true) {
      synchronized (queue) {
        long waitTime = Math.abs(random.nextLong()) % 4000 + 500;
        try {
          queue.wait(waitTime);
        } catch (InterruptedException e) {
          System.out.println(Thread.currentThread().getName() +
              " (producer) was interrupted");
        }

        String message = new StringBuilder()
            .append(Thread.currentThread().getName())
            .append(" Time: ")
            .append(LocalDateTime.now())
            .toString();

        queue.add(message);
      }
    }
  }
}

class MessageConsumer extends Thread {

  private final Queue<String> queue;
  private final String outputFilename;
  private final Random random;

  public MessageConsumer(Queue<String> queue, String outputFilename) {
    this.queue = queue;
    this.outputFilename = outputFilename;
    random = Random.from(RandomGenerator.getDefault());
  }

  @Override
  public void run() {
    try (FileWriter fileWriter = new FileWriter(outputFilename)) {
      while (true) {
        synchronized (queue) {
          long waitTime = Math.abs(random.nextLong()) % 4000;
          try {
            queue.wait(waitTime);
          } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() +
                " (consumer) was interrupted");
          }

          if (!queue.isEmpty()) {
            String message = queue.poll();
            fileWriter.write(message);
            fileWriter.write(System.lineSeparator());
            fileWriter.flush();
          }
        }
      }
    } catch (IOException e) {
      for (StackTraceElement trace : e.getStackTrace()) {
        System.out.println(trace);
      }
    }
  }
}
