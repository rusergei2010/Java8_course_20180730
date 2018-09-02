package a_lambda.part1;

import org.junit.jupiter.api.Test;

import a_lambda.data.Person;

// Variety of lambdas
// Capturing context

public class Lambdas04 {

    private void run(Runnable r) {
        r.run();
    }

    @Test
    public void capturingAnonymous() {
        Person person = new Person("John", "Galt", 33);

        run(new Runnable() {
            @Override
            public void run() {
                person.print();
            }
        });

        //person = new Person("a", "a", 44);
    }

    @Test
    public void capturingLambda() {
        Person person = new Person("John", "Galt", 33);

        // statement lambda
        run(() -> {
            person.print();
        });
        // expression lambda
        run(() -> person.print());
        // method reference
        run(person::print);
    }

    private Person privatePerson = null;

    @Test
    public void capturingMystery() {
        privatePerson = new Person("John", "Galt", 33);
        privatePerson = new Person("John", "Galt", 34);

        run(() -> privatePerson.print());
        run(privatePerson::print);

        privatePerson = new Person("Mysterious", "Stranger", 777);
    }


    private Runnable runLater(Runnable r) {
        return () -> {
            System.out.println("before run");
            r.run();
        };
    }

    @Test
    public void capturingMystery2() {
        privatePerson = new Person("John", "Galt", 33);

        final Runnable r1 = runLater(() -> privatePerson.print());
        final Runnable r2 = runLater(privatePerson::print);

        privatePerson = new Person("Mysterious", "Stranger", 777);

        r1.run();
        r2.run();

    }

}
