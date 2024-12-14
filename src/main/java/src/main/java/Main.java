package src.main.java;

import lombok.Getter;
import lombok.Setter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Main {
  public static void main(String[] args) throws src.main.java.ru.mai.lessons.FilenameShouldNotBeEmptyException {
    String jsonStr;
    try {
      jsonStr = jsonReader.loadConfig("D:\\IntelliJ IDEA\\DACS\\JavaBaseExamine\\src\\main\\file.json");
    } catch (src.main.java.ru.mai.lessons.FilenameShouldNotBeEmptyException e) {
      e.printStackTrace();
      return;
    }

    System.out.printf(jsonStr);

    List<Person> people = new ArrayList<>();
    try {
      jsonParse(jsonStr, people);
    } catch (ParseException e) {
      e.printStackTrace();
      return;
    }

    CompletableFuture<List<Person>> future = CompletableFuture.supplyAsync(() -> people.stream()
            .filter(person -> person.getAge() > 18)
            .filter(person -> person.getName().startsWith("A"))
            .collect(Collectors.toList()));


    try {
      List<Person> result = future.get();
      for (Person person : result) {
      if (person.getAge() > 18 && person.getName().toCharArray()[0] == 'А') {
        System.out.printf(person.getName());
        System.out.println();
      }
    }
    } catch (Exception e) {
      e.printStackTrace();
    }

//    for (Person person : people) {
//      if (person.getAge() > 18 && person.getName().toCharArray()[0] == 'А') {
//        System.out.printf(person.getName());
//        System.out.println();
//      }
//    }
  }

  private static void jsonParse(String jsonStr, List<Person> peoplesArr) throws ParseException {
    JSONParser parser = new JSONParser();
    JSONObject config = (JSONObject) parser.parse(jsonStr);
    JSONArray brackets = (JSONArray) config.get("people");


    for (Object bracketsPair : brackets) {
      String name = ((JSONObject) bracketsPair).get("name").toString();
      int age = Integer.parseInt(((JSONObject) bracketsPair).get("age").toString());
      String gender = ((JSONObject) bracketsPair).get("gender").toString();
      String phone = ((JSONObject) bracketsPair).get("phone").toString();

      peoplesArr.add(new Person(name, age, gender, phone));
    }
  }

  public static class Person {
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private int age;
    private String gender;
    private String phone;

    public Person(String name, int age, String gender, String phone) {
      this.name = name;
      this.age = age;
      this.gender = gender;
      this.phone = phone;
    }
  }
}