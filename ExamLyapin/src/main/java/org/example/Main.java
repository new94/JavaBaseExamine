package org.example;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        HashSet<String> arr = new HashSet<>();
        arr.add("wow");
        arr.add("meow");
        arr.add("12332");
        arr.add("wow");

        arr.forEach(item -> System.out.println(item));
        Thread a = new Thread(
                () -> {
                    // инструкция
                }
        );
        a.start();

        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ExecutorService ex = Executors.newCachedThreadPool();
    }
}