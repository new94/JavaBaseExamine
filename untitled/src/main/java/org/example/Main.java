package org.example;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final String FILE_PATH = "/Users/oduvanchik/javaLabs/JavaBaseExamine/file.json";

    static class Person {
        private String name;
        private int age;
        private String gender;
        private String number;

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "ФИО='" + name + '\'' +
                    ", возраст=" + age +
                    ", пол='" + gender + '\'' +
                    ", номер_телефона='" + number + '\'' +
                    '}';
        }
    }

    public static void main() {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> people;

        try {
            people = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<Person>>() {
            });
        } catch (IOException e) {
            System.err.println("Ошибка: " + e.getMessage());
            return;
        }

        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<List<Person>> task = () -> people.stream()
                .filter(person -> person.getAge() > 18 && startsWithA(person.getName()))
                .collect(Collectors.toList());

        Future<List<Person>> futureResult = executor.submit(task);

        try {
            List<Person> filteredPeople = futureResult.get();
            System.out.println("Люди старше 18 лет, чьи фамилия и имя начинаются на букву 'A':");
            for (Person person : filteredPeople) {
                System.out.println(person);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Ошибка при выполнении задачи: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private static boolean startsWithA(String fullName) {
        if (fullName == null || fullName.isEmpty()) {
            return false;
        }
        String[] parts = fullName.trim().split("\\s+");
        if (parts.length < 2) {
            return false;
        }
        String surname = parts[0];
        String name = parts[1];
        return surname.toUpperCase().startsWith("A") && name.toUpperCase().startsWith("A");
    }
}