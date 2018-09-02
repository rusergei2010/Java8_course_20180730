package b_streams.songs;

import java.util.LinkedList;
import java.util.List;
import java.util.zip.ZipFile;

public class Task4 {
    public static void main(String[] args) {
        Person me = new Person();
        Person you = new Person();

        List<Person> persons = new LinkedList<>();

        //---------------------------------

        persons.stream()
                .filter(p -> !(p.getShoulderMark() != null
                        && p.getShoulderMark().getStripes().size() == 2
                        && p.getShoulderMark().getStars().size() == 3))
                .map(p -> Letter.builder()
                        .setTo(p)
                        .setText(Utils.loremIpsum())
                        .build())
                .forEach(l -> send(l));

        //---------------------------------


    }

    private static void send(Letter l) {

    }


    private static class ThreadOfEverythingAbout extends Thread {
        public ThreadOfEverythingAbout(Person person) {
            super();
        }
    }

    static class Person {
        private ShoulderMark shoulderMark;

        public ShoulderMark getShoulderMark() {
            return shoulderMark;
        }

        public void setShoulderMark(ShoulderMark shoulderMark) {
            this.shoulderMark = shoulderMark;
        }
    }

    private static class ShoulderMark {
        private List<Stripe> lines;
        private ZipFile stars;

        public List<Stripe> getStripes() {
            return lines;
        }

        public void setLines(List<Stripe> lines) {
            this.lines = lines;
        }

        public ZipFile getStars() {
            return stars;
        }

        public void setStars(ZipFile stars) {
            this.stars = stars;
        }

        private class Stripe {
        }
    }

    private static class Letter {

        private static class Builder {
            private Person to;
            private String text;

            Letter build() {
                return new Letter();
            }

            public Builder setTo(Person to) {
                this.to = to;
                return this;
            }

            public Builder setText(String text) {
                this.text = text;
                return this;
            }
        }

        public static Builder builder() {
            return new Builder();
        }
    }

    private static class Utils {

        public static String loremIpsum() {
            return "";
        }
    }
}
