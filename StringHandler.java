import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*

Реализуйте программу на Java,
в которой необходимо сформировать коллекцию с помощью Stream API на основе входных данных.
На вход подается строка с любым набором символов.
Необходимо с помощью Stream API получить из этой строки коллекцию с типом String,
элементами которой являются сумма всех четных чисел из этой строки (в виде строки),
конкатенация всех символов нижнего регистра, и отдельно стоящие символы не буквы и не цифры.
Пример: На вход подается строка "aV_1,,.-fg.D42!r" на выходе коллекция ["6","afgr","_", ",",",",".","-",".","!"].

 */

public class StringHandler {

    public List<String> process(String stringToProcess) {
        String evenNumbersSum = Stream.of(stringToProcess.split(""))
                .filter(c -> c.matches("\\d"))
                .map(Integer::parseInt)
                .filter(num -> num % 2 == 0)
                .mapToInt(Integer::intValue)
                .sum() + "";

        String allLowerCaseConcat = Stream.of(stringToProcess.split(""))
                .filter(c -> c.matches("[a-z]"))
                .collect(Collectors.joining());

        List<String> notLettersNotDigits = Stream.of(stringToProcess.split(""))
                .filter(c -> !c.matches("[a-zA-Z\\d]"))
                .toList();

        List<String> result = new ArrayList<>();
        result.add(evenNumbersSum);
        result.add(allLowerCaseConcat);
        result.addAll(notLettersNotDigits);

        return result;
    }

}
