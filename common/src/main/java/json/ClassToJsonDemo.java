package json;

import com.google.gson.Gson;

public class ClassToJsonDemo {

    class Person {
        String firstName;
        String lastName;
        int age;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }
    }

    public static void main(String[] args) {
        Person spock = new ClassToJsonDemo().new Person("Leonard", "Nimoy", 81);
        Gson gson = new Gson();
        System.out.println(gson.toJson(spock));
    }
}

