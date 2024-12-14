import java.util.List;

public class Main {

    public static void main(String[] args) {
        StringHandler stringHandler = new StringHandler();

        String input = "aV_1,,.-fg.D42!r";

        List<String> expectedResult = List.of("6", "afgr", "_", ",", ",", ".", "-", ".", "!");
        List<String> actualResult = stringHandler.process(input);

        System.out.println("Actual result: " + actualResult);
        System.out.println("Expected result: " + expectedResult);

        if (actualResult.equals(expectedResult)) {
            System.out.println("passed");
        } else {
            System.out.println("not passed");
        }
    }

}
