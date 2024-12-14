import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class PersonFilter {

    static class Person {
        String fullName;
        int age;
        String gender;
        String phoneNumber;

        public String getFullName() {
            return fullName;
        }

        public int getAge() {
            return age;
        }

        public String getGender() {
            return gender;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }
    }

    public static class PersonFilterTask implements Callable<List<Person>> {
        private List<Person> people;

        public PersonFilterTask(List<Person> people) {
            this.people = people;
        }

        @Override
        public List<Person> call() throws Exception {
            return people.stream()
                    .filter(person -> person.getAge() > 18 && person.getFullName().startsWith("A"))
                    .toList();
        }
    }

    public static List<Person> readPeopleFromJson(String filename) throws IOException {
        Gson gson = new Gson();
        FileReader reader = new FileReader(filename);
        return gson.fromJson(reader, new TypeToken<List<Person>>() {}.getType());
    }

    public static void main(String[] args) {
        try {
            List<Person> people = readPeopleFromJson("people.json");

            ExecutorService executorService = Executors.newSingleThreadExecutor();

            PersonFilterTask task = new PersonFilterTask(people);

            Future<List<Person>> future = executorService.submit(task);

            List<Person> result = future.get();

            System.out.println("Filtered people:");
            for (Person person : result) {
                System.out.println(person.getFullName() + ", " + person.getAge() + " years old");
            }

            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
