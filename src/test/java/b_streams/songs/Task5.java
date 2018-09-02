package b_streams.songs;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Task5 {
    public static void main(String[] args) {


        List<Sea> seas = Arrays.asList(new Sea());
        Here here = new Here();
        List<Location> allPossibleDestinations = Collections.emptyList();
        Location currentLocation = new Location();
        Location defaultDestination = new Location();



        //---------------------------------

        Girl girl = here.getGirl(Speed.MAX);

        Distance minDistance = seas.stream()
                .limit(100)
                .map(Sea::getWidth)
                .reduce(Distance.ZERO, Distance::add);

        Location destination = allPossibleDestinations.stream()
                .filter(d -> minDistance.compareTo(Distance.between(d, currentLocation)) < 0)
                .findAny()
                .orElse(defaultDestination);

        destination.getPlaces()
                .forEach(p -> kiss(girl));

        //---------------------------------

    }

    private static void kiss(Girl girl) {

    }

    private static Girl take(Object max) {
        return new Girl();
    }

    private static class Speed {
        public static final Object MAX = null;
    }

    private static class Girl {
    }

    private static class Sea {
        public static Distance getWidth(Sea sea) {
            return null;
        }
    }

    private static class Here {
        public Girl getGirl(Object max) {
            return null;
        }
    }

    private static class DestinationRepository {
        public List<Destination> findAllByNearestSea(Sea sea) {
            return null;
        }
    }

    private static class Destination extends Location {


    }

    private static class Place {
    }

    private static class Distance implements Comparable<Distance> {
        public static final Distance ZERO = null;

        public Distance add(Distance b) {
            return null;
        }

        public static Distance between(Location d, Location currentLocation) {
            return new Distance();
        }

        @Override
        public int compareTo(Distance o) {
            return 0;
        }
    }

    private static class Location {
        private  List<Place> places;

        public List<Place> getPlaces() {
            return places;
        }

        public void setPlaces(List<Place> places) {
            this.places = places;
        }
    }
}
