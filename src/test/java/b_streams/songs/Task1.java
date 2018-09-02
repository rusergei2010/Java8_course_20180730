package b_streams.songs;

import java.util.stream.Stream;

public class Task1 {
    public static void main(String[] args) {
        Person me = new Person();
        Person you = new Person();

        //---------------------------------

        Stream.of(
                new ThreadOfEverythingAbout(me),
                new ThreadOfEverythingAbout(you)
        ).forEach(Thread::start);

        //---------------------------------
    }


    private static class ThreadOfEverythingAbout extends Thread{
        public ThreadOfEverythingAbout(Person person) {
            super();
        }
    }


    private static class Person {
    }
}
