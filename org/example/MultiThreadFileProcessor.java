package org.example;


import java.io.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


public class MultiThreadFileProcessor {
    public static void main(String[] args) {
        String inputFile1 = "/Users/a.lisnyak/Downloads/JavaBaseExamine/org/example/input.txt";
        String inputFile2 = "/Users/a.lisnyak/Downloads/JavaBaseExamine/org/example/input2.txt";
        String outputFile = "/Users/a.lisnyak/Downloads/JavaBaseExamine/org/example/output.txt";



        BlockingQueue<String> queue = new LinkedBlockingQueue<>();



        Thread readerThread1 = new Thread(new FileReaderTask(inputFile1, queue));
        Thread readerThread2 = new Thread(new FileReaderTask(inputFile2, queue));



        Thread writerThread = new Thread(new FileWriterTask(outputFile, queue));



        readerThread1.start();
        readerThread2.start();
        writerThread.start();


        try {

            readerThread1.join();
            readerThread2.join();



            queue.put("EOF");



            writerThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}



class FileReaderTask implements Runnable {
    private final String fileName;
    private final BlockingQueue<String> queue;


    public FileReaderTask(String fileName, BlockingQueue<String> queue) {
        this.fileName = fileName;
        this.queue = queue;
    }


    @Override
    public void run() {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                queue.put(line);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}




class FileWriterTask implements Runnable {
    private final String fileName;
    private final BlockingQueue<String> queue;


    public FileWriterTask(String fileName, BlockingQueue<String> queue) {
        this.fileName = fileName;
        this.queue = queue;
    }


    @Override
    public void run() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            String line;
            while (!(line = queue.take()).equals("EOF")) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
