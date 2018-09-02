package a_lambda.z_zone;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class UniversalSetterExample {

    public static class Person{
        private String firstname;
        private String middlename;
        private String lastname;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getMiddlename() {
            return middlename;
        }

        public void setMiddlename(String middlename) {
            this.middlename = middlename;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public void setViaReflection(String fieldName, String fieldValue) throws NoSuchFieldException, IllegalAccessException {
            this.getClass().getDeclaredField(fieldName).set(this, fieldValue);
        }

        static final Map<String, BiConsumer<Person, String>> setters = new HashMap<>();
        static {
            setters.put("firstname", Person::setFirstname);
            setters.put("lastname", Person::setLastname);
            setters.put("middlename", Person::setMiddlename);
        }

        public void setViaLambdas(String fieldName, String fieldValue) {
            setters.get(fieldName).accept(this, fieldValue);
        }
    }

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

        Person p = new Person();
        p.setFirstname("John");
        p.setViaReflection("middlename", "Maria");
        p.setViaLambdas("lastname", "Galt");

    }
}
