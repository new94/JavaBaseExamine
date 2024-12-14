import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    // Входная строка
    String input = "aV_1,,.-fg.D42!r";

    // Формируем коллекцию через Stream API
    List<String> result = Stream.of(input)
        .map(str -> new Object() {
          final String sumOfEvenNumbers = String.valueOf(
              str.chars()
                  .filter(Character::isDigit)
                  .map(c -> c - '0').filter(n -> n % 2 == 0).sum()
          );

          final String lowerCaseChars = str.chars()
              .filter(Character::isLowerCase)
              .mapToObj(c -> String.valueOf((char) c))
              .collect(Collectors.joining());
          final List<String> nonAlphanumericChars = str.chars()
              .filter(c -> !Character.isLetterOrDigit(c))
              .mapToObj(c -> String.valueOf((char) c))
              .collect(Collectors.toList());
        })
        .flatMap(obj -> Stream.concat(
            Stream.of(obj.sumOfEvenNumbers, obj.lowerCaseChars),
            obj.nonAlphanumericChars.stream()
        ))
        .collect(Collectors.toList());

    System.out.println(result);
  }
}