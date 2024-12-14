package org.example;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

class JsonProcessor {

    public static void main(String[] args) {
        //тестовый файл в resources, путь к нему
        String jsonFilePath = "C:\\Users\\TorchPochmak\\Desktop\\people.txt";

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Person> people = objectMapper.readValue(new File(jsonFilePath), new TypeReference<List<Person>>() {});

            Callable<List<Person>> task = () -> people.stream()
                    .filter(p -> p.getAge() > 18)
                    .filter(p -> p.getFullName().matches("^[Aa].*\\s[Aa].*")) //регулярка
                    .collect(Collectors.toList());

            ExecutorService executor = Executors.newSingleThreadExecutor();

            Future<List<Person>> future = executor.submit(task);

            List<Person> result = future.get();
            result.forEach(System.out::println);

            executor.shutdown();
        } catch (IOException | InterruptedException | ExecutionException e) {
            System.out.println(e.getMessage());
        }
    }
}