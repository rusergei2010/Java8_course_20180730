package b_streams.songs;

import java.awt.*;
import java.util.List;

public class Task3 {
    public static void main(String[] args) {


        FlowersFactory flowersFactory = new FlowersFactory();


        //---------------------------------

        Flowers flowers = flowersFactory.defaultFlowers(new Color(255, 255, 255));
        flowers.getThorns().forEach(Thorn::blunt);

        //---------------------------------


    }

    private static class FlowersFactory {
        public Flowers defaultFlowers(Color color) {
            return new Flowers();
        }

    }

    public static class Thorn {
        public void blunt() {

        }
    }
    private static class Flowers {

        private List<Thorn> thorns;

        List<Thorn> getThorns() {
            return thorns;
        }
        public void setThorns(List<Thorn> thorns) {
            this.thorns = thorns;
        }

    }
}
