package b_streams.songs;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Task6 {
    public static void main(String[] args) {
        Random random = new Random();

        List<Location> possibleLocations = new ArrayList<>();
        Person me = new Person();

        //---------------------------------

        Trip trip = Trip.builder()
                .type(Trip.Type.HIKING)
                .startTime(LocalTime.of(random.nextInt(4), random.nextInt(60)))
                .speedInKmH(random.nextDouble() * 4)
                .targetLocation(possibleLocations.stream()
                        .filter(l -> l.getTerrain().getType() == Terrain.Type.FIELD)
                        .findFirst().get()
                )
                .participant(me)
                .participant(me.getHouseHold().getDomesticAnimals().stream()
                        .filter(a -> a.getSex() == Sex.MALE)
                        .filter(a -> a.getShoes() != null
                                && a.getShoes().getMaterial() == Material.IRON)
                        .findFirst().get()
                ).build();

        //---------------------------------

        System.out.println(trip);

    }

    private static class Trip {
        public static Builder builder() {
            return null;
        }


        public enum Type {
            HIKING
        }

        public static class Builder extends Trip {
            public Builder type(Type hiking) {

                return new Builder();
            }

            public Builder startTime(LocalTime of) {
                return new Builder();
            }

            public Builder speedInKmH(double v) {
                return new Builder();
            }

            public Builder targetLocation(Location location) {

                return new Builder();
            }

            public Builder participant(Animal a) {
                return new Builder();
            }

            public Trip build() {
                return new Trip();
            }

        }
    }


    private static class Location {
        private Terrain terrain;

        public Terrain getTerrain() {
            return terrain;
        }


        private Terrain.Type type;

        public Terrain.Type getType() {
            return type;
        }

        public void setType(Terrain.Type type) {
            this.type = type;
        }

    }

    private static class Terrain {
        private Type type;

        public Type getType() {
            return type;
        }

        private static enum Type {
            FIELD
        }
    }

    private static class Person extends Animal {
        private HouseHold houseHold;

        public HouseHold getHouseHold() {
            return houseHold;
        }

        public void setHouseHold(HouseHold houseHold) {
            this.houseHold = houseHold;
        }
    }

    private static class Animal {
        private Shoes shoes;
        private Sex sex;

        public Shoes getShoes() {
            return shoes;
        }

        public void setShoes(Shoes shoes) {
            this.shoes = shoes;
        }

        public Sex getSex() {
            return sex;
        }

        public void setSex(Sex sex) {
            this.sex = sex;
        }
    }

    private static class HouseHold {
        private List<Animal> domesticAnimals;

        public List<Animal> getDomesticAnimals() {
            return domesticAnimals;
        }
    }

    private static enum Material {
        IRON
    }

    private static class Shoes {
        private Material material;

        public Material getMaterial() {
            return material;
        }
    }

    private static enum Sex {
        MALE, FEMALE
    }
}
