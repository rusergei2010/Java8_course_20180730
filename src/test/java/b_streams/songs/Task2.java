package b_streams.songs;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

import javafx.scene.control.Button;

public class Task2 {
    public static void main(String[] args) {

        Dream dream = new Dream();
        Person you = new Person();

        //---------------------------------

        Button theButton = new Button();
        theButton.setOnAction(
                e -> new Result(() -> you.getDream().fulfill())
        );
        assertFalse(you.isGlad());
        assertNull(you.getDream());

        //---------------------------------

    }

    private static class Result {
        public Result(Runnable o) {

        }
    }


    private static class Dream {
        public void fulfill() {

        }

    }

    private static class Person {
        private Dream dream;
        private boolean glad;

        public Dream getDream() {
            return dream;
        }

        public void setDream(Dream dream) {
            this.dream = dream;
        }

        public boolean isGlad() {
            return glad;
        }

        public void setGlad(boolean glad) {
            this.glad = glad;
        }
    }
}
